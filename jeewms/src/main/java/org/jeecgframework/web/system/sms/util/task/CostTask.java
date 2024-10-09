package org.jeecgframework.web.system.sms.util.task;

import com.zzjee.ba.entity.BaCostEntity;
import com.zzjee.md.entity.MdCusEntity;
import com.zzjee.wm.entity.WmCusCostHEntity;
import com.zzjee.wm.entity.WmCusCostIEntity;
import com.zzjee.wm.entity.WmDayCostConfEntity;
import com.zzjee.wm.entity.WmDayCostEntity;
import com.zzjee.wm.service.WmCusCostHServiceI;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.sms.service.TSSmsServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:SmsSendTask
 * @Description: 根据计费配置，生成费用详情
 * @date 2014-11-13 下午5:06:34
 */
@Service("costTask")
public class CostTask {
    @Autowired
    private SystemService systemService;
    @Autowired
    private WmCusCostHServiceI wmCusCostHService;
    public void run() {
        long start = System.currentTimeMillis();
        String run = ResourceUtil.getConfigByName("timerun");
        if (!run.equals("run")) {
            return;
        }
        String datestr = DateUtils.date2Str(DateUtils.date_sdf);
        org.jeecgframework.core.util.LogUtil
                .info("===================計費定时任务开始===================");
        WmDayCostConfEntity t = new WmDayCostConfEntity();

        this.costcountv2(datestr, "N", t);//每天自动计算
        org.jeecgframework.core.util.LogUtil
                .info("===================計費定时任务结束===================");
        long end = System.currentTimeMillis();
        long times = end - start;
        org.jeecgframework.core.util.LogUtil.info("总耗时" + times + "毫秒");
    }

    public void costcountv2(String datestr, String chongsuan, WmDayCostConfEntity t){
        String tsql = "select COST_SF  from wm_day_cost_conf   where to_days(cost_date) = to_days(?)";
        if (chongsuan.equals("N")) {//非重算
            List<Map<String, Object>> resultconf = systemService.findForJdbc(tsql, datestr);
            if (resultconf.size() > 0) {
                return;
            } else {
                Date costdate = DateUtils.str2Date(datestr, DateUtils.date_sdf);
                t.setCostDate(costdate);
                t.setCostSf("Y");
                t.setCreateBy("system");
                t.setCreateDate(costdate);
                systemService.save(t);
            }
        }
        tsql = "call update_wm_his_stock('" + datestr + "')";
        try {
            systemService.executeSql(tsql);
        } catch (Exception e) {
            // TODO: handle exception
        }
        org.jeecgframework.core.util.LogUtil
                .info("===================1库存更新成功===================");

        //先删除
        tsql = "delete  from wm_day_cost   where cost_sta ='已生成' and  to_days(cost_data) = to_days(?)";
        systemService.executeSql(tsql, t.getCostDate());
        org.jeecgframework.core.util.LogUtil
                .info("===================V2数据删除成功===================");
        CriteriaQuery cq = new CriteriaQuery(WmCusCostHEntity.class);
        cq.add();
        List<WmCusCostHEntity> list=this.wmCusCostHService.getListByCriteriaQuery(cq, false);
        Double cost_jg = 0.0000;
        Double cost_sl = 0.0000;
        Double cost_bhs = 0.0000;
        Double cost_hs = 0.0000;
        Double dayCostYj = 0.0000;
        Double dayCostBhs = 0.0000;
        Double dayCostSe = 0.0000;
        Double dayCostHsj = 0.0000;
        Double costSlo = 0.0000;
        String ori = null;
        String costSl = null;
        String countunit = null;
        if(list!=null&&list.size()>0){
            for(WmCusCostHEntity entity:list){
                try{
                     Object id0 = entity.getId();
                    String hql0 = "from WmCusCostIEntity where 1 = 1 AND cUS_COST_ID = ? ";
                    List<WmCusCostIEntity> wmCusCostIEntityList = systemService.findHql(hql0,id0);
                    String cusCode = entity.getCusCode();
                    String cusName = "";
                    try{
                        MdCusEntity mdcus1 = systemService.findUniqueByProperty(MdCusEntity.class, "keHuBianMa", cusCode);
                        cusName = mdcus1.getZhongWenQch();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    for (WmCusCostIEntity wmCusCostIEntity : wmCusCostIEntityList) {
                        //执行计算 SQL 返回order_id goods_id  goods_count count_unit
                        String dataSql = wmCusCostIEntity.getDataSql();
                        if (StringUtil.isEmpty(dataSql)){
                            continue;
                        }
                        dataSql = StringUtils.replace(dataSql, "{cusCode}", cusCode);
                        dataSql = StringUtils.replace(dataSql, "{date}", datestr);
                        List<Map<String, Object>> resulthq = systemService
                                .findForJdbc(dataSql);
                        //计算费用
                        for (int i = 0; i < resulthq.size(); i++) {
                            try{
                                ori = null;
                                costSl = null;
                                cost_jg = 0.0000;
                                cost_sl = 0.0000;
                                cost_bhs = 0.0000;
                                cost_hs = 0.0000;
                                dayCostYj = 0.0000;
                                dayCostBhs = 0.0000;
                                dayCostSe = 0.0000;
                                dayCostHsj = 0.0000;
                                cost_jg = Double.parseDouble(wmCusCostIEntity.getCostJg());
                                cost_sl = Double.parseDouble(wmCusCostIEntity.getCostSl());
                                cost_bhs = Double.parseDouble(wmCusCostIEntity.getCostBhs());
                                cost_hs = Double.parseDouble(wmCusCostIEntity.getCostHs());
                                ori = resulthq.get(i).get("order_id").toString() + "/" + resulthq.get(i).get("goods_id").toString();
                                countunit = resulthq.get(i).get("count_unit").toString();
                                costSlo = Double.parseDouble(resulthq.get(i).get("goods_count").toString());
                                costSl = costSlo.toString();
                                WmDayCostEntity WmDayCost = new WmDayCostEntity();
                                WmDayCost.setCostJs("N");
                                WmDayCost.setCreateBy("system");
                                WmDayCost.setCreateDate(DateUtils.getDate());
                                WmDayCost.setCostCode(wmCusCostIEntity.getCostCode());
                                try{
                                    BaCostEntity bacost = systemService.findUniqueByProperty(BaCostEntity.class, "costCode", wmCusCostIEntity.getCostCode());
                                    WmDayCost.setCostName(bacost.getCostName());
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                WmDayCost.setCostData(t.getCostDate());
                                WmDayCost.setCostOri(ori);
                                WmDayCost.setCostSl(costSl);
                                WmDayCost.setCostSta("已生成");
                                WmDayCost.setCusCode(cusCode);
                                WmDayCost.setCusName(cusName);
                                WmDayCost.setCostUnit(countunit);
                                dayCostYj = cost_jg * costSlo;
                                dayCostBhs = cost_bhs * costSlo;
                                dayCostHsj = cost_hs * costSlo;
                                dayCostSe = dayCostHsj - dayCostBhs;
                                DecimalFormat df = new DecimalFormat("#.00");
                                WmDayCost.setDayCostYj(df.format(dayCostYj));
                                WmDayCost.setDayCostBhs(df.format(dayCostBhs));
                                WmDayCost.setDayCostSe(df.format(dayCostSe));
                                WmDayCost.setDayCostHsj(df.format(dayCostHsj));
                                systemService.save(WmDayCost);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }catch(Exception e){
                }
            }
        }
    }

}
