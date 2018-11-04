package org.jeecgframework.web.system.sms.util.task;

import java.text.DecimalFormat;
import java.util.*;

import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.apache.poi.hdf.extractor.NewOleFile;
import org.jeecgframework.core.util.DBTypeUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.sms.entity.TSSmsEntity;
import org.jeecgframework.web.system.sms.service.TSSmsServiceI;
import org.jsoup.helper.DataUtil;

import com.zzjee.md.entity.MdBinEntity;
import com.zzjee.md.entity.MdCusEntity;
import com.zzjee.md.entity.MdGoodsEntity;
import com.zzjee.md.entity.MvGoodsEntity;
import com.zzjee.wm.entity.WmDayCostConfEntity;
import com.zzjee.wm.entity.WmDayCostEntity;
import com.zzjee.wm.entity.WmImNoticeIEntity;
import com.zzjee.wm.entity.WmInQmIEntity;
import com.zzjee.wm.entity.WmOmNoticeIEntity;
import com.zzjee.wm.entity.WmOmQmIEntity;
import com.zzjee.wm.entity.WmToDownGoodsEntity;
import com.zzjee.wm.entity.WmToMoveGoodsEntity;
import com.zzjee.wm.entity.WmToUpGoodsEntity;

/**
 * 
 * @ClassName:SmsSendTask 所有信息的发送定时任务类
 * @Description: TODO
 * @date 2014-11-13 下午5:06:34
 * 
 */
@Service("costTask")
public class CostTask {

	@Autowired
	private TSSmsServiceI tSSmsService;
	@Autowired
	private SystemService systemService;

	/* @Scheduled(cron="0 0 01 * * ?") */
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

		this.costcount(datestr,"N",t);//每天自动计算
		org.jeecgframework.core.util.LogUtil
				.info("===================計費定时任务结束===================");
		long end = System.currentTimeMillis();
		long times = end - start;
		org.jeecgframework.core.util.LogUtil.info("总耗时" + times + "毫秒");
	}
	public  void costcount(String datestr,String chongsuan,WmDayCostConfEntity t ){
//		WmDayCostConfEntity t = new WmDayCostConfEntity();
		String 	tsql = "select COST_SF  from wm_day_cost_conf   where to_days(cost_date) = to_days(?)";
		if(chongsuan.equals("N")){//非重算
			List<Map<String, Object>> resultconf = systemService.findForJdbc(tsql, datestr);
			if(resultconf.size()>0){
				return;
			}else{
				Date costdate = DateUtils.str2Date(datestr,DateUtils.date_sdf);
				t.setCostDate(costdate);
				t.setCostSf("Y");
				t.setCreateBy("system");
				t.setCreateDate(costdate);
				systemService.save(t);
			}
		}

		 tsql = "call update_wm_his_stock('"+datestr+"')";
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
				.info("===================2数据删除成功===================");
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
		String cuscode = null;
		String cfwenceng = null;
		String beizhu = null;
		String costcode = null;
		String countunit = null;

		tsql = "select  mg.cf_wen_ceng,wi.tin_id,mg.jf_shp_lei,   wi.cus_code,    wi.im_notice_id,    wi.goods_id,    wi.rec_deg,(sum(wi.qm_ok_quat)/ mg.chl_shl) as qmcount,(mg.zhl_kg  ) as zhl_kg,    (sum(wi.qm_ok_quat) * mg.zhl_kg   ) as grcount "
				+" from    wm_in_qm_i wi,    mv_goods mg where  wi.goods_id = mg.goods_code and wi.qm_ok_quat > 0  and to_days(wi.create_date) = to_days(?) "
				+" group by wi.im_notice_id ,wi.cus_code,wi.tin_id,  wi.goods_id , wi.rec_deg";
		List<Map<String, Object>> resulthq = systemService
				.findForJdbc(tsql, t.getCostDate());
		//计算卸货费
		for (int i = 0; i < resulthq.size(); i++) {
			if( resulthq.get(i).get("im_notice_id").toString().startsWith("QT")){
				continue;
			}
			ori = null;
			costSl = null;
			cuscode = null;
			cfwenceng = null;
			beizhu = null;
			cost_jg = 0.0000;
			cost_sl = 0.0000;
			cost_bhs = 0.0000;
			cost_hs = 0.0000;
			dayCostYj = 0.0000;
			dayCostBhs = 0.0000;
			dayCostSe = 0.0000;
			dayCostHsj = 0.0000;

//			String jf_shp_lei  = resulthq.get(i).get("jf_shp_lei").toString();
			String jf_shp_lei  = null;
			try{
				jf_shp_lei = resulthq.get(i).get("jf_shp_lei").toString();
			}catch (Exception e){

			}
			if("10".equals(jf_shp_lei)){
				costcode = "501";// 卸货费/计费吨
				//更改为向上取整
//					costSlo = Math.ceil(Double.parseDouble(resulthq.get(i).get("grcount").toString())/1000);
				costSlo = Double.parseDouble(resulthq.get(i).get("grcount").toString())/1000;
				countunit = "吨";
			}
			if("20".equals(jf_shp_lei)){
				costcode = "502";// 卸货费/整托
				costSlo = 1.00;
				countunit = "托";
			}
			if("30".equals(jf_shp_lei)){
				costcode = "503";// 卸货费/轻抛5KG以下
				costSlo = Double.parseDouble(resulthq.get(i).get("qmcount").toString());
				countunit = "箱";
			}
			if("40".equals(jf_shp_lei)){
				costcode = "504";// 卸货费/轻抛5KG以上
				costSlo = Double.parseDouble(resulthq.get(i).get("qmcount").toString());
				countunit = "箱";
			}
			costSl = costSlo.toString();


			cuscode  = resulthq.get(i).get("cus_code").toString();
			//计算操作费和越库费用
			tsql = " select  cost_code,  cost_jg,  cost_sl,  cost_zk,  cost_bhs ,  cost_hs   "
					+ "from wm_cus_cost_h wch , wm_cus_cost_i wci where wch.id = wci.cus_cost_id and wci.cost_code = ? and wch.cus_code = ? and "
					+ "(to_days(wch.begin_date) <= to_days(?)  and to_days(wch.end_date) >= to_days(?)) limit 1";


			List<Map<String, Object>> resultjg = systemService
					.findForJdbc(tsql,costcode,cuscode, t.getCostDate(), t.getCostDate());
			if(resultjg.size()>0){


				cost_jg = Double.parseDouble((String) resultjg.get(0).get("cost_jg"));
				cost_sl  = Double.parseDouble((String) resultjg.get(0).get("cost_sl"));
				cost_bhs = Double.parseDouble((String) resultjg.get(0).get("cost_bhs"));
				cost_hs = Double.parseDouble((String) resultjg.get(0).get("cost_hs"));
			}else{
				beizhu = "费用合同无此费率";
				cost_bhs  = 0.00  ;
				dayCostBhs  = 0.00 ;
				cost_hs  = 0.00  ;
			}
			ori = resulthq.get(i).get("im_notice_id").toString()+"/"+resulthq.get(i).get("goods_id").toString();

			WmDayCostEntity WmDayCost = new WmDayCostEntity();
			WmDayCost.setCreateBy("system");
			WmDayCost.setCreateDate(DateUtils.getDate());
			WmDayCost.setCostCode(costcode);
			WmDayCost.setCostData(t.getCostDate());
			WmDayCost.setCostOri(ori);
			WmDayCost.setCostSl(costSl);
			WmDayCost.setCostSta("已生成");
			WmDayCost.setCusCode(cuscode);
			WmDayCost.setCostUnit(countunit);
			WmDayCost.setBeizhu(beizhu);
			dayCostYj  = cost_jg *  costSlo;
			dayCostBhs  = cost_bhs *  costSlo;
			dayCostHsj  = cost_hs *  costSlo;
			dayCostSe  = dayCostHsj -  dayCostBhs ;
			DecimalFormat df=new DecimalFormat("#.00");
//				String st=df.format(d);
			WmDayCost.setDayCostYj(df.format(dayCostYj));
			WmDayCost.setDayCostBhs(df.format(dayCostBhs));
			WmDayCost.setDayCostSe(df.format(dayCostSe));
			WmDayCost.setDayCostHsj(df.format(dayCostHsj));
			systemService.save(WmDayCost);
		}//计算卸货费/吨

		org.jeecgframework.core.util.LogUtil
				.info("===================3卸货费成功===================");

		tsql = "select  mg.cf_wen_ceng,wi.tin_id,mg.jf_shp_lei,   wi.cus_code,    wi.im_notice_id,    wi.goods_id,    wi.rec_deg,(sum(wi.qm_ok_quat)/ mg.chl_shl)  as qmcount,(mg.zhl_kg  ) as zhl_kg,    (sum(wi.qm_ok_quat) * mg.zhl_kg   ) as grcount "
				+" from    wm_in_qm_i wi,    mv_goods mg where  wi.goods_id = mg.goods_code and wi.qm_ok_quat > 0  and to_days(wi.create_date) = to_days(?) "
				+" group by wi.im_notice_id ,wi.cus_code,wi.tin_id";
		resulthq = systemService
				.findForJdbc(tsql, t.getCostDate());
		//计算过磅费
		for (int i = 0; i < resulthq.size(); i++) {
			if( resulthq.get(i).get("im_notice_id").toString().startsWith("YK")||resulthq.get(i).get("im_notice_id").toString().startsWith("QT")){
				continue;
			}
			ori = null;
			costSl = null;
			cuscode = null;
			cfwenceng = null;
			beizhu = null;
			cost_jg = 0.0000;
			cost_sl = 0.0000;
			cost_bhs = 0.0000;
			cost_hs = 0.0000;
			dayCostYj = 0.0000;
			dayCostBhs = 0.0000;
			dayCostSe = 0.0000;
			dayCostHsj = 0.0000;
			costcode = "70";// 过磅费/
			costSlo = 1.00;
			countunit = "托";

			costSl = costSlo.toString();


			cuscode  = resulthq.get(i).get("cus_code").toString();

			tsql = " select  cost_code  ,  cost_jg  ,  cost_sl  ,  cost_zk  ,  cost_bhs ,  cost_hs   "
					+ "from wm_cus_cost_h wch , wm_cus_cost_i wci where wch.id = wci.cus_cost_id and wci.cost_code = ? and wch.cus_code = ? and "
					+ "(to_days(wch.begin_date) <= to_days(?)  and to_days(wch.end_date) >= to_days(?)) limit 1";


			List<Map<String, Object>> resultjg = systemService
					.findForJdbc(tsql,costcode,cuscode, t.getCostDate(), t.getCostDate());
			if(resultjg.size()>0){


				cost_jg = Double.parseDouble((String) resultjg.get(0).get("cost_jg"));
				cost_sl  = Double.parseDouble((String) resultjg.get(0).get("cost_sl"));
				cost_bhs = Double.parseDouble((String) resultjg.get(0).get("cost_bhs"));
				cost_hs = Double.parseDouble((String) resultjg.get(0).get("cost_hs"));
			}else{
				beizhu = "费用合同无此费率";
				cost_bhs  = 0.00  ;
				dayCostBhs  = 0.00 ;
				cost_hs  = 0.00  ;
			}
			ori = resulthq.get(i).get("im_notice_id").toString()+"/"+resulthq.get(i).get("goods_id").toString();

			WmDayCostEntity WmDayCost = new WmDayCostEntity();
			WmDayCost.setCreateBy("system");
			WmDayCost.setCreateDate(DateUtils.getDate());
			WmDayCost.setCostCode(costcode);
			WmDayCost.setCostData(t.getCostDate());
			WmDayCost.setCostOri(ori);
			WmDayCost.setCostSl(costSl);
			WmDayCost.setCostSta("已生成");
			WmDayCost.setCusCode(cuscode);
			WmDayCost.setCostUnit(countunit);
			WmDayCost.setBeizhu(beizhu);
			dayCostYj  = cost_jg *  costSlo;
			dayCostBhs  = cost_bhs *  costSlo;
			dayCostHsj  = cost_hs *  costSlo;
			dayCostSe  = dayCostHsj -  dayCostBhs ;
			DecimalFormat df=new DecimalFormat("#.00");
//					String st=df.format(d);
			WmDayCost.setDayCostYj(df.format(dayCostYj));
			WmDayCost.setDayCostBhs(df.format(dayCostBhs));
			WmDayCost.setDayCostSe(df.format(dayCostSe));
			WmDayCost.setDayCostHsj(df.format(dayCostHsj));
			systemService.save(WmDayCost);
		}//计算过磅费/托

		org.jeecgframework.core.util.LogUtil
				.info("===================4过磅费成功===================");

		tsql = 	"select   wi.cus_code, wi.tin_id,   wi.im_notice_id,    wi.goods_id,"
				+" ((select count(*)  from wm_im_notice_i where im_notice_id = wi.im_notice_id) )  as  goodscount,"
				+" (sum(wi.qm_ok_quat) / mg.chl_shl) as qmcount "
				+" from    wm_in_qm_i wi,    mv_goods mg "
				+" where    wi.goods_id = mg.goods_code  and wi.qm_ok_quat > 0"
				+"        and to_days(wi.create_date) = to_days(?)"
				+" group by wi.im_notice_id , wi.tin_id,wi.goods_id,wi.cus_code  having goodscount > 2 ";
		resulthq = systemService
				.findForJdbc(tsql, t.getCostDate());
		//计算进货分拣
		Double goodscount = 0.00 ;
		for (int i = 0; i < resulthq.size(); i++) {
			if( resulthq.get(i).get("im_notice_id").toString().startsWith("YK")||resulthq.get(i).get("im_notice_id").toString().startsWith("QT")){
				continue;
			}
			cost_jg = 0.0000;
			cost_sl = 0.0000;
			cost_bhs = 0.0000;
			cost_hs = 0.0000;
			dayCostYj = 0.0000;
			dayCostBhs = 0.0000;
			dayCostSe = 0.0000;
			dayCostHsj = 0.0000;
			ori = null;
			costSl = null;
			cuscode = null;
			cfwenceng = null;
			beizhu = null;

			goodscount = Double.parseDouble(resulthq.get(i).get("goodscount").toString());

			if(goodscount>10){
				costcode = "602";// 10以上
				costSlo = Double.parseDouble(resulthq.get(i).get("qmcount").toString());
				countunit = "箱";
			}else{
				costcode = "601";// 10以上
				costSlo = Double.parseDouble(resulthq.get(i).get("qmcount").toString());
				countunit = "箱";
			}

			costSl = costSlo.toString();

			cuscode  = resulthq.get(i).get("cus_code").toString();

			tsql = " select  cost_code  ,  cost_jg  ,  cost_sl  ,  cost_zk  ,  cost_bhs ,  cost_hs   "
					+ "from wm_cus_cost_h wch , wm_cus_cost_i wci where wch.id = wci.cus_cost_id and wci.cost_code = ? and wch.cus_code = ? and "
					+ "(to_days(wch.begin_date) <= to_days(?)  and to_days(wch.end_date) >= to_days(?)) limit 1";


			List<Map<String, Object>> resultjg = systemService
					.findForJdbc(tsql,costcode,cuscode, t.getCostDate(), t.getCostDate());
			if(resultjg.size()>0){


				cost_jg = Double.parseDouble((String) resultjg.get(0).get("cost_jg"));
				cost_sl  = Double.parseDouble((String) resultjg.get(0).get("cost_sl"));
				cost_bhs = Double.parseDouble((String) resultjg.get(0).get("cost_bhs"));
				cost_hs = Double.parseDouble((String) resultjg.get(0).get("cost_hs"));
			}else{
				beizhu = "费用合同无此费率";
				cost_bhs  = 0.00  ;
				dayCostBhs  = 0.00 ;
				cost_hs  = 0.00  ;
			}
			ori = resulthq.get(i).get("im_notice_id").toString()+"/"+resulthq.get(i).get("goods_id").toString();

			WmDayCostEntity WmDayCost = new WmDayCostEntity();
			WmDayCost.setCreateBy("system");
			WmDayCost.setCreateDate(DateUtils.getDate());
			WmDayCost.setCostCode(costcode);
			WmDayCost.setCostData(t.getCostDate());
			WmDayCost.setCostOri(ori);
			WmDayCost.setCostSl(costSl);
			WmDayCost.setCostSta("已生成");
			WmDayCost.setCusCode(cuscode);
			WmDayCost.setCostUnit(countunit);
			WmDayCost.setBeizhu(beizhu);
			dayCostYj  = cost_jg *  costSlo;
			dayCostBhs  = cost_bhs *  costSlo;
			dayCostHsj  = cost_hs *  costSlo;
			dayCostSe  = dayCostHsj -  dayCostBhs ;
			DecimalFormat df=new DecimalFormat("#.00");
//						String st=df.format(d);
			WmDayCost.setDayCostYj(df.format(dayCostYj));
			WmDayCost.setDayCostBhs(df.format(dayCostBhs));
			WmDayCost.setDayCostSe(df.format(dayCostSe));
			WmDayCost.setDayCostHsj(df.format(dayCostHsj));
			systemService.save(WmDayCost);
		}//计算进货分拣费

		org.jeecgframework.core.util.LogUtil
				.info("===================5进货分拣成功===================");
		tsql = 						"select     wi.cus_code,    wi.order_id,    wi.goods_id,(select count(*)  from wm_om_notice_i where om_notice_id = wi.order_id) as  goodscount,"
				+" (sum(wi.base_goodscount) / mg.chl_shl) as qmcount "
				+" from    wm_to_down_goods wi , mv_goods mg "
				+" where  wi.goods_id = mg.goods_code  and   wi.bin_id_to <> 'ZY'  "
				+"       and to_days(wi.create_date) = to_days(?) "
				+" group by wi.order_id , wi.cus_code ,   wi.goods_id  having goodscount > 2";
		resulthq = systemService
				.findForJdbc(tsql, t.getCostDate());
		//计算出货分拣
		goodscount = 0.00 ;
		for (int i = 0; i < resulthq.size(); i++) {
			if(resulthq.get(i).get("order_id").toString().startsWith("QT")){
				continue;
			}
			cost_jg = 0.0000;
			cost_sl = 0.0000;
			cost_bhs = 0.0000;
			cost_hs = 0.0000;
			dayCostYj = 0.0000;
			dayCostBhs = 0.0000;
			dayCostSe = 0.0000;
			dayCostHsj = 0.0000;
			ori = null;
			costSl = null;
			cuscode = null;
			cfwenceng = null;
			beizhu = null;
			goodscount = Double.parseDouble(resulthq.get(i).get("goodscount").toString());

			if(goodscount>10){
				costcode = "6021";// 10以上
				costSlo = Double.parseDouble(resulthq.get(i).get("qmcount").toString());
				countunit = "箱";
			}else{
				costcode = "6011";// 10以上
				costSlo = Double.parseDouble(resulthq.get(i).get("qmcount").toString());
				countunit = "箱";
			}

			costSl = costSlo.toString();

			cuscode  = resulthq.get(i).get("cus_code").toString();

			tsql = " select  cost_code  ,  cost_jg  ,  cost_sl  ,  cost_zk  ,  cost_bhs ,  cost_hs   "
					+ "from wm_cus_cost_h wch , wm_cus_cost_i wci where wch.id = wci.cus_cost_id and wci.cost_code = ? and wch.cus_code = ? and "
					+ "(to_days(wch.begin_date) <= to_days(?)  and to_days(wch.end_date) >= to_days(?)) limit 1";


			List<Map<String, Object>> resultjg = systemService
					.findForJdbc(tsql,costcode,cuscode, t.getCostDate(), t.getCostDate());
			if(resultjg.size()>0){


				cost_jg = Double.parseDouble((String) resultjg.get(0).get("cost_jg"));
				cost_sl  = Double.parseDouble((String) resultjg.get(0).get("cost_sl"));
				cost_bhs = Double.parseDouble((String) resultjg.get(0).get("cost_bhs"));
				cost_hs = Double.parseDouble((String) resultjg.get(0).get("cost_hs"));
			}else{
				beizhu = "费用合同无此费率";
				cost_bhs  = 0.00  ;
				dayCostBhs  = 0.00 ;
				cost_hs  = 0.00  ;
			}
			ori = resulthq.get(i).get("order_id").toString()+"/"+resulthq.get(i).get("goods_id").toString();

			WmDayCostEntity WmDayCost = new WmDayCostEntity();
			WmDayCost.setCreateBy("system");
			WmDayCost.setCreateDate(DateUtils.getDate());
			WmDayCost.setCostCode(costcode);
			WmDayCost.setCostData(t.getCostDate());
			WmDayCost.setCostOri(ori);
			WmDayCost.setCostSl(costSl);
			WmDayCost.setCostSta("已生成");
			WmDayCost.setCusCode(cuscode);
			WmDayCost.setCostUnit(countunit);
			WmDayCost.setBeizhu(beizhu);
			dayCostYj  = cost_jg *  costSlo;
			dayCostBhs  = cost_bhs *  costSlo;
			dayCostHsj  = cost_hs *  costSlo;
			dayCostSe  = dayCostHsj -  dayCostBhs ;
			DecimalFormat df=new DecimalFormat("#.00");
//								String st=df.format(d);
			WmDayCost.setDayCostYj(df.format(dayCostYj));
			WmDayCost.setDayCostBhs(df.format(dayCostBhs));
			WmDayCost.setDayCostSe(df.format(dayCostSe));
			WmDayCost.setDayCostHsj(df.format(dayCostHsj));
			systemService.save(WmDayCost);
		}//计算出货分拣费

		org.jeecgframework.core.util.LogUtil
				.info("===================6出货分拣成功===================");

		tsql = 		"select  wi.bin_id_from, mg.jf_shp_lei, wi.cus_code, wi.order_id, wi.goods_id, (sum(wi.base_goodscount)/ mg.chl_shl) as qmcount,"
				+"  (sum(wi.base_goodscount) * mg.zhl_kg_zx / mg.chl_shl) as grcount ,cast((mg.mp_dan_ceng * mg.mp_ceng_gao * mg.chl_shl) as signed) as hiti"
				+" from wm_to_down_goods wi,  mv_goods mg  "
				+" where  wi.goods_id = mg.goods_code   "
				+"        and to_days(wi.create_date) = to_days(?)   and wi.bin_id_to <> 'ZY' "
				+" group by wi.order_id , wi.cus_code , wi.bin_id_from , wi.goods_id " ;
		resulthq = systemService
				.findForJdbc(tsql, t.getCostDate());
		//计算出货费
		for (int i = 0; i < resulthq.size(); i++) {
			if(resulthq.get(i).get("order_id").toString().startsWith("QT")){
				continue;
			}
			cost_jg = 0.0000;
			cost_sl = 0.0000;
			cost_bhs = 0.0000;
			cost_hs = 0.0000;
			dayCostYj = 0.0000;
			dayCostBhs = 0.0000;
			dayCostSe = 0.0000;
			dayCostHsj = 0.0000;
			ori = null;
			costSl = null;
			cuscode = null;
			cfwenceng = null;
			beizhu = null;
			String jf_shp_lei  = null;
			try{
				jf_shp_lei = resulthq.get(i).get("jf_shp_lei").toString();
			}catch (Exception e){

			}

			if("10".equals(jf_shp_lei)){
//					  costcode = "5011";// 卸货费/计费吨
//					  costSlo = Double.parseDouble(resulthq.get(i).get("grcount").toString())/1000;
//					  countunit = "吨";
				MdCusEntity mdc = systemService.findUniqueByProperty(MdCusEntity.class, "keHuBianMa", resulthq.get(i).get("cus_code").toString());
				if(mdc.getKeHuZhuangTai().equals("20")){
					costcode = "5011";// 卸货费/吨
					costSlo = Double.parseDouble(resulthq.get(i).get("grcount").toString())/1000;
					countunit = "吨";
				}else{

					if(  Double.parseDouble(resulthq.get(i).get("hiti").toString()) ==  Double.parseDouble(resulthq.get(i).get("qmcount").toString())){
						costcode = "5011";// 卸货费/吨
						costSlo = Double.parseDouble(resulthq.get(i).get("grcount").toString())/1000;
						countunit = "吨";
					}else{
						costcode = "5031";// 卸货费/轻抛5KG以下
						if((Double.parseDouble(resulthq.get(i).get("hiti").toString()) / 2) <=  Double.parseDouble(resulthq.get(i).get("qmcount").toString())){
							costSlo = (Double.parseDouble(resulthq.get(i).get("hiti").toString())) - Double.parseDouble(resulthq.get(i).get("qmcount").toString()) ;
						}else{
							costSlo = Double.parseDouble(resulthq.get(i).get("qmcount").toString());
						}

						countunit = "箱";
					}

				}
			}
			if("20".equals(jf_shp_lei)){
				costcode = "5021";// 卸货费/整托
				costSlo = 1.00;
				countunit = "托";
			}
			if("30".equals(jf_shp_lei)){
				costcode = "5031";// 卸货费/轻抛5KG以下
				costSlo = Double.parseDouble(resulthq.get(i).get("qmcount").toString());
				countunit = "箱";
			}
			if("40".equals(jf_shp_lei)){
				costcode = "5041";// 卸货费/轻抛5KG以上
				costSlo = Double.parseDouble(resulthq.get(i).get("qmcount").toString());
				countunit = "箱";
			}

			costSl = costSlo.toString();

			cuscode  = resulthq.get(i).get("cus_code").toString();

			tsql = " select  cost_code  ,  cost_jg,  cost_sl  ,  cost_zk  ,  cost_bhs ,  cost_hs   "
					+ "from wm_cus_cost_h wch , wm_cus_cost_i wci where wch.id = wci.cus_cost_id and wci.cost_code = ? and wch.cus_code = ? and "
					+ "(to_days(wch.begin_date) <= to_days(?)  and to_days(wch.end_date) >= to_days(?)) limit 1";


			List<Map<String, Object>> resultjg = systemService
					.findForJdbc(tsql,costcode,cuscode, t.getCostDate(), t.getCostDate());
			if(resultjg.size()>0){


				cost_jg = Double.parseDouble((String) resultjg.get(0).get("cost_jg"));
				cost_sl  = Double.parseDouble((String) resultjg.get(0).get("cost_sl"));
				cost_bhs = Double.parseDouble((String) resultjg.get(0).get("cost_bhs"));
				cost_hs = Double.parseDouble((String) resultjg.get(0).get("cost_hs"));
			}else{
				beizhu = "费用合同无此费率";
				cost_bhs  = 0.00  ;
				dayCostBhs  = 0.00 ;
				cost_hs  = 0.00  ;
			}
			ori = resulthq.get(i).get("order_id").toString()+"/"+resulthq.get(i).get("goods_id").toString();

			WmDayCostEntity WmDayCost = new WmDayCostEntity();
			WmDayCost.setCreateBy("system");
			WmDayCost.setCreateDate(DateUtils.getDate());
			WmDayCost.setCostCode(costcode);
			WmDayCost.setCostData(t.getCostDate());
			WmDayCost.setCostOri(ori);
			WmDayCost.setCostSl(costSl);
			WmDayCost.setCostSta("已生成");
			WmDayCost.setCusCode(cuscode);
			WmDayCost.setCostUnit(countunit);
			WmDayCost.setBeizhu(beizhu);
			dayCostYj  = cost_jg *  costSlo;
			dayCostBhs  = cost_bhs *  costSlo;
			dayCostHsj  = cost_hs *  costSlo;
			dayCostSe  = dayCostHsj -  dayCostBhs ;
			DecimalFormat df=new DecimalFormat("#.00");
//				String st=df.format(d);
			WmDayCost.setDayCostYj(df.format(dayCostYj));
			WmDayCost.setDayCostBhs(df.format(dayCostBhs));
			WmDayCost.setDayCostSe(df.format(dayCostSe));
			WmDayCost.setDayCostHsj(df.format(dayCostHsj));
			systemService.save(WmDayCost);
		}//计算装车费
		org.jeecgframework.core.util.LogUtil
				.info("===================7出装车费成功===================");

		//复冻处置费
		tsql = "select  mg.cf_wen_ceng,     wi.cus_code,    wi.im_notice_id,    wi.goods_id,ceil(mg.jizhun_wendu) as jizhun_wendu,  ceil(wi.rec_deg) as rec_deg,sum(wi.qm_ok_quat) as qmcount,(mg.zhl_kg) as zhl_kg,    (sum(wi.qm_ok_quat) * mg.zhl_kg   ) as grcount "
				+" from    wm_in_qm_i wi,    mv_goods mg where  wi.goods_id = mg.goods_code and wi.qm_ok_quat > 0  and to_days(wi.create_date) = to_days(?) "
				+" group by wi.im_notice_id ,wi.cus_code,   wi.goods_id , wi.rec_deg";
		resulthq = systemService
				.findForJdbc(tsql, t.getCostDate());
		costcode = "30";
		for (int i = 0; i < resulthq.size(); i++) {
			if( resulthq.get(i).get("im_notice_id").toString().startsWith("YK")||resulthq.get(i).get("im_notice_id").toString().startsWith("QT")){
				continue;
			}
			cost_jg = 0.0000;
			cost_sl = 0.0000;
			cost_bhs = 0.0000;
			cost_hs = 0.0000;
			dayCostYj = 0.0000;
			dayCostBhs = 0.0000;
			dayCostSe = 0.0000;
			dayCostHsj = 0.0000;
			ori = null;
			costSl = null;
			cuscode = null;
			cfwenceng = null;
			beizhu = null;
			cuscode  = resulthq.get(i).get("cus_code").toString();
			cfwenceng = resulthq.get(i).get("cf_wen_ceng").toString();
			//
			tsql = " select  cost_code  ,  cost_jg  ,  cost_sl  ,  cost_zk  ,  cost_bhs ,  cost_hs   "
					+ "from wm_cus_cost_h wch , wm_cus_cost_i wci where wch.id = wci.cus_cost_id and wci.cost_code = ? and wch.cus_code = ? and "
					+ "(to_days(wch.begin_date) <= to_days(?)  and to_days(wch.end_date) >= to_days(?)) limit 1";
			Double recdeg = 0.00;

			try{
				recdeg = Double.parseDouble(resulthq.get(i).get("rec_deg").toString()) - Double.parseDouble(resulthq.get(i).get("jizhun_wendu").toString());

			}catch (Exception e){
				recdeg = 0 - Double.parseDouble(resulthq.get(i).get("jizhun_wendu").toString());

			}
			if(recdeg>25){
				costcode = "308";//复冻处置费/吨 温差大于25
			}else if(recdeg>=13&&recdeg<=25){
				costcode = "306";//复冻处置费/吨  温差 13 25
			}else if(recdeg>=10&&recdeg<13){
				costcode = "304";//复冻处置费/吨  温差10 13
			}else{
				continue;
			}


			List<Map<String, Object>> resultjg = systemService
					.findForJdbc(tsql,costcode,cuscode, t.getCostDate(), t.getCostDate());
			if(resultjg.size()>0){
				cost_jg = Double.parseDouble((String) resultjg.get(0).get("cost_jg"));
				cost_sl  = Double.parseDouble((String) resultjg.get(0).get("cost_sl"));
				cost_bhs = Double.parseDouble((String) resultjg.get(0).get("cost_bhs"));
				cost_hs = Double.parseDouble((String) resultjg.get(0).get("cost_hs"));
			}else{
				beizhu = "费用合同无此费率";
				cost_bhs  = 0.00  ;
				dayCostBhs  = 0.00 ;
				cost_hs  = 0.00  ;
			}
			ori = resulthq.get(i).get("im_notice_id").toString()+"/"+resulthq.get(i).get("goods_id").toString();
			costSl = resulthq.get(i).get("grcount").toString();
			WmDayCostEntity WmDayCost = new WmDayCostEntity();
			WmDayCost.setCreateBy("system");
			WmDayCost.setCreateDate(DateUtils.getDate());
			WmDayCost.setCostCode(costcode);
			WmDayCost.setCostData(t.getCostDate());
			WmDayCost.setCostOri(ori);
			WmDayCost.setCostSl(costSl);
			WmDayCost.setCostSta("已生成");
			WmDayCost.setCusCode(cuscode);
			WmDayCost.setCostUnit("KG");
			WmDayCost.setBeizhu(beizhu);
			dayCostYj  = cost_jg *  Double.parseDouble(resulthq.get(i).get("grcount").toString()) / 1000;
			dayCostBhs  = cost_bhs *  Double.parseDouble(resulthq.get(i).get("grcount").toString())  / 1000;
			dayCostHsj  =  cost_hs *  Double.parseDouble(resulthq.get(i).get("grcount").toString()) / 1000;
			dayCostSe  = dayCostHsj -  dayCostBhs ;
			DecimalFormat df=new DecimalFormat("#.00");
//				String st=df.format(d);
			WmDayCost.setDayCostYj(df.format(dayCostYj));
			WmDayCost.setDayCostBhs(df.format(dayCostBhs));
			WmDayCost.setDayCostSe(df.format(dayCostSe));
			WmDayCost.setDayCostHsj(df.format(dayCostHsj));
			systemService.save(WmDayCost);
		}//复冻处置费/吨
		org.jeecgframework.core.util.LogUtil
				.info("===================8复冻处置费成功===================");
		tsql = "select  mg.cf_wen_ceng,   wi.cus_code,    wi.im_notice_id,    wi.tin_id,    wi.rec_deg,sum(wi.qm_ok_quat) as qmcount,(mg.zhl_kg  ) as zhl_kg,    (sum(wi.qm_ok_quat) * mg.zhl_kg   ) as grcount "
				+" from    wm_in_qm_i wi,    mv_goods mg where  wi.goods_id = mg.goods_code and wi.qm_ok_quat > 0  and to_days(wi.create_date) = to_days(?) "
				+" group by wi.cus_code , wi.im_notice_id, wi.tin_id ";
		resulthq = systemService
				.findForJdbc(tsql, t.getCostDate());
		//服务费
		for (int i = 0; i < resulthq.size(); i++) {
			if( resulthq.get(i).get("im_notice_id").toString().startsWith("YK")||resulthq.get(i).get("im_notice_id").toString().startsWith("QT")){
				continue;
			}
			cost_jg = 0.0000;
			cost_sl = 0.0000;
			cost_bhs = 0.0000;
			cost_hs = 0.0000;
			dayCostYj = 0.0000;
			dayCostBhs = 0.0000;
			dayCostSe = 0.0000;
			dayCostHsj = 0.0000;
			ori = null;
			costSl = null;
			cuscode = null;
			cfwenceng = null;
			beizhu = null;
			cuscode  = resulthq.get(i).get("cus_code").toString();

			tsql = " select  cost_code  ,  cost_jg  ,  cost_sl  ,  cost_zk  ,  cost_bhs ,  cost_hs   "
					+ "from wm_cus_cost_h wch , wm_cus_cost_i wci where wch.id = wci.cus_cost_id and wci.cost_code = ? and wch.cus_code = ? and "
					+ "(to_days(wch.begin_date) <= to_days(?)  and to_days(wch.end_date) >= to_days(?)) limit 1";

			costcode = "80";
			List<Map<String, Object>> resultjg = systemService
					.findForJdbc(tsql,costcode,cuscode, t.getCostDate(), t.getCostDate());
			if(resultjg.size()>0){
				cost_jg = Double.parseDouble((String) resultjg.get(0).get("cost_jg"));
				cost_sl  = Double.parseDouble((String) resultjg.get(0).get("cost_sl"));
				cost_bhs = Double.parseDouble((String) resultjg.get(0).get("cost_bhs"));
				cost_hs = Double.parseDouble((String) resultjg.get(0).get("cost_hs"));
			}else{
				beizhu = "费用合同无此费率";
				cost_bhs  = 0.00  ;
				dayCostBhs  = 0.00 ;
				cost_hs  = 0.00  ;
			}
			ori = resulthq.get(i).get("im_notice_id").toString()+"/"+ resulthq.get(i).get("tin_id").toString();
			costSl = "1";
			WmDayCostEntity WmDayCost = new WmDayCostEntity();
			WmDayCost.setCreateBy("system");
			WmDayCost.setCreateDate(DateUtils.getDate());
			WmDayCost.setCostCode(costcode);
			WmDayCost.setCostData(t.getCostDate());
			WmDayCost.setCostOri(ori);
			WmDayCost.setCostSl(costSl);
			WmDayCost.setCostSta("已生成");
			WmDayCost.setCusCode(cuscode);
			WmDayCost.setCostUnit("托");
			WmDayCost.setBeizhu(beizhu);
			dayCostYj  = cost_jg  ;
			dayCostBhs  = cost_bhs ;
			dayCostHsj  = cost_hs  ;
			dayCostSe  = dayCostHsj -  dayCostBhs ;
			DecimalFormat df=new DecimalFormat("#.00");
//					String st=df.format(d);
			WmDayCost.setDayCostYj(df.format(dayCostYj));
			WmDayCost.setDayCostBhs(df.format(dayCostBhs));
			WmDayCost.setDayCostSe(df.format(dayCostSe));
			WmDayCost.setDayCostHsj(df.format(dayCostHsj));
			systemService.save(WmDayCost);
		}//计算服务费

		org.jeecgframework.core.util.LogUtil
				.info("===================9服务费成功===================");
//		SELECT  mdp.cus_code, mdp.ku_wei_bian_ma, mdp.bin_id, mdp.goods_id,sum(base_goodscount) as count
//		FROM  mv_down_and_up mdp where	    (mdp.leixing = 'S' and to_days(mdp.create_date) <= to_days('2017-09-16'))
//	        or (mdp.leixing = 'H' and to_days(mdp.create_date) < to_days('2017-09-16'))
//	group by mdp.cus_code, mdp.ku_wei_bian_ma , mdp.bin_id , mdp.goods_id
//	having sum(base_goodscount)  > 0 order by cus_code,  create_date;
		if(ResourceUtil.getConfigByName("ckjf").equals("1")){
			tsql = 		"SELECT  mdp.cus_code, mdp.ku_wei_bian_ma, mdp.bin_id, mdp.goods_id,mdp.jf_shp_lei, ((select zhl_kg from mv_goods where goods_code = mdp.goods_id) / (select chl_shl from mv_goods where goods_code = mdp.goods_id) / 1000) as zhl_kgm, " +
					"(sum(base_goodscount) * (select zhl_kg from mv_goods where goods_code = mdp.goods_id) / (select chl_shl from mv_goods where goods_code = mdp.goods_id) /1000) as count," +
					" (sum(base_goodscount) / (select chl_shl from mv_goods where goods_code = mdp.goods_id) ) as xcount"
					+" FROM  mv_down_and_up mdp where  (mdp.leixing = 'S' and to_days(mdp.create_date) <= to_days(?))"
					+" or (mdp.order_id <> 'ZY' and mdp.leixing = 'H' and to_days(mdp.create_date) < to_days(?))"
					+" or (mdp.order_id = 'ZY' and mdp.leixing = 'H' and to_days(mdp.create_date) <= to_days(?))"
//				+" group by mdp.cus_code,mdp.ku_wei_bian_ma, mdp.bin_id, mdp.goods_id, mdp.jf_shp_lei "

					+" group by mdp.cus_code,   mdp.goods_id, mdp.jf_shp_lei "
					+" having sum(base_goodscount)  > 0 order by mdp.cus_code, mdp.goods_id, mdp.create_date";
		}else{
			tsql = 		"SELECT  mdp.cus_code, mdp.ku_wei_bian_ma, mdp.bin_id, mdp.goods_id,mdp.jf_shp_lei, ((select zhl_kg from mv_goods where goods_code = mdp.goods_id) / (select chl_shl from mv_goods where goods_code = mdp.goods_id) / 1000) as zhl_kgm, " +
					"(sum(base_goodscount) * (select zhl_kg from mv_goods where goods_code = mdp.goods_id) / (select chl_shl from mv_goods where goods_code = mdp.goods_id) /1000) as count," +
					" (sum(base_goodscount) / (select chl_shl from mv_goods where goods_code = mdp.goods_id) ) as xcount"
					+" FROM  mv_down_and_up mdp where  (mdp.leixing = 'S' and to_days(mdp.create_date) <= to_days(?))"
					+" or (mdp.order_id <> 'ZY' and mdp.leixing = 'H' and to_days(mdp.create_date) < to_days(?))"
					+" or (mdp.order_id = 'ZY' and mdp.leixing = 'H' and to_days(mdp.create_date) <= to_days(?))"
				+" group by mdp.cus_code,mdp.ku_wei_bian_ma, mdp.bin_id, mdp.goods_id, mdp.jf_shp_lei "

//					+" group by mdp.cus_code,   mdp.goods_id, mdp.jf_shp_lei "
					+" having sum(base_goodscount)  > 0 order by mdp.cus_code, mdp.goods_id, mdp.create_date";
		}

		resulthq = systemService
				.findForJdbc(tsql, t.getCostDate(), t.getCostDate(), t.getCostDate());
		HashMap<String,Double>  cusmap = new  HashMap<String,Double>();
		HashMap<String,Double>  goodsmap = new  HashMap<String,Double>();
		HashMap<String,Double>  cdwcusmap = new  HashMap<String,Double>();
		HashMap<String,Double>  cdwgoodsmap = new  HashMap<String,Double>();
		String  jfunit = null;
		//仓租
		for (int i = 0; i < resulthq.size(); i++) {
			cost_jg = 0.0000;
			cost_sl = 0.0000;
			cost_bhs = 0.0000;
			cost_hs = 0.0000;
			dayCostYj = 0.0000;
			dayCostBhs = 0.0000;
			dayCostSe = 0.0000;
			dayCostHsj = 0.0000;
			ori = null;
			costSl = null;
			cuscode = null;
			cfwenceng = null;
			beizhu = null;
			cuscode  = resulthq.get(i).get("cus_code").toString();
			//仓租

			if(StringUtil.isEmpty(cuscode)){
				continue;
			}
			//仓租
			MdBinEntity mdb = systemService.findUniqueByProperty(MdBinEntity.class, "kuWeiBianMa", resulthq.get(i).get("ku_wei_bian_ma").toString());
			if(mdb==null){
				continue;
			}
			if(mdb.getKuWeiShuXing().equals("超低温")){
				MdCusEntity mdc = systemService.findUniqueByProperty(MdCusEntity.class, "keHuBianMa", resulthq.get(i).get("cus_code").toString());
//				String jf_shp_lei  = resulthq.get(i).get("jf_shp_lei").toString();
				String jf_shp_lei  = null;
				try{
					jf_shp_lei = resulthq.get(i).get("jf_shp_lei").toString();
				}catch (Exception e){

				}
				costcode = "1031";
				if("10".equals(jf_shp_lei)){
					costSl = resulthq.get(i).get("count").toString();
					costcode = "1031";
					jfunit = "吨";
					if(mdc.getKeHuShuXing().equals("10")){
						if(cdwcusmap.containsKey(resulthq.get(i).get("cus_code").toString())){
							Double temp = 0.00;
							temp = cdwcusmap.get(resulthq.get(i).get("cus_code").toString()) +Double.parseDouble(resulthq.get(i).get("count").toString());
							cdwcusmap.replace(resulthq.get(i).get("cus_code").toString(), temp);
						}else{
							cdwcusmap.put(resulthq.get(i).get("cus_code").toString(), Double.parseDouble(resulthq.get(i).get("count").toString()));
						}
						//按客户合计


					}else{
						if(cdwgoodsmap.containsKey(resulthq.get(i).get("goods_id").toString())){
							Double temp = 0.00;
							temp = cdwgoodsmap.get(resulthq.get(i).get("goods_id").toString()) +Double.parseDouble(resulthq.get(i).get("count").toString());
							cdwgoodsmap.replace(resulthq.get(i).get("goods_id").toString(), temp);
						}else{
							cdwgoodsmap.put(resulthq.get(i).get("goods_id").toString(), Double.parseDouble(resulthq.get(i).get("count").toString()));
						}
						// 按商品合计

					}
					continue;
				}
				if ("20".equals(jf_shp_lei)) {
					costSl = "1";
					costcode = "1032";
					jfunit = "托";
				}
				if (jf_shp_lei.equals("30")) {
					costSl = resulthq.get(i).get("xcount").toString();
					costcode = "1033";
					jfunit = "箱";
				}
				if (jf_shp_lei.equals("40")) {
					costSl = resulthq.get(i).get("xcount").toString();
					costcode = "1034";
					jfunit = "箱";
				}
			}else {
				MdCusEntity mdc = systemService.findUniqueByProperty(MdCusEntity.class, "keHuBianMa", resulthq.get(i).get("cus_code").toString());
//				String jf_shp_lei  = resulthq.get(i).get("jf_shp_lei").toString();
				String jf_shp_lei  = null;
				try{
					jf_shp_lei = resulthq.get(i).get("jf_shp_lei").toString();
				}catch (Exception e){

				}
				costcode = "1011";

				if("10".equals(jf_shp_lei)){
					costSl = resulthq.get(i).get("count").toString();
					costcode = "1011";
					jfunit = "吨";
					if(mdc.getKeHuShuXing().equals("10")){
						if(cusmap.containsKey(resulthq.get(i).get("cus_code").toString())){
							Double temp = 0.00;
							temp = cusmap.get(resulthq.get(i).get("cus_code").toString()) +Double.parseDouble(resulthq.get(i).get("count").toString());
							cusmap.replace(resulthq.get(i).get("cus_code").toString(), temp);
						}else{
							cusmap.put(resulthq.get(i).get("cus_code").toString(), Double.parseDouble(resulthq.get(i).get("count").toString()));
						}
						//按客户合计


					}else{
						if(goodsmap.containsKey(resulthq.get(i).get("goods_id").toString())){
							Double temp = 0.00;
							temp = goodsmap.get(resulthq.get(i).get("goods_id").toString()) +Double.parseDouble(resulthq.get(i).get("count").toString());
							goodsmap.replace(resulthq.get(i).get("goods_id").toString(), temp);
						}else{
							goodsmap.put(resulthq.get(i).get("goods_id").toString(), Double.parseDouble(resulthq.get(i).get("count").toString()));
						}
						// 按商品合计

					}
					continue;

				}
				if ("20".equals(jf_shp_lei)) {
					costSl = "1";
					costcode = "1012";
					jfunit = "托";
				}
				if ("30".equals(jf_shp_lei)) {
					costSl = resulthq.get(i).get("xcount").toString();
					costcode = "1013";
					jfunit = "箱";
				}
				if ("40".equals(jf_shp_lei)) {
					costSl = resulthq.get(i).get("xcount").toString();
					costcode = "1014";
					jfunit = "箱";
				}
			}
			tsql = " select  cost_code  ,  cost_jg  ,  cost_sl  ,  cost_zk  ,  cost_bhs ,  cost_hs   "
					+ "from wm_cus_cost_h wch , wm_cus_cost_i wci where wch.id = wci.cus_cost_id and wci.cost_code = ? and wch.cus_code = ? and "
					+ "(to_days(wch.begin_date) <= to_days(?)  and to_days(wch.end_date) >= to_days(?)) limit 1";

			List<Map<String, Object>> resultjg = systemService
					.findForJdbc(tsql,costcode,cuscode, t.getCostDate(), t.getCostDate());
			if(resultjg.size()>0){
				if(ResourceUtil.getConfigByName("jifeitype").equals("1")){
					cost_jg = Double.parseDouble((String) resultjg.get(0).get("cost_jg")) * Math.ceil(Double.parseDouble(costSl));
					cost_sl  = Double.parseDouble((String) resultjg.get(0).get("cost_sl")) * Math.ceil(Double.parseDouble(costSl));
					cost_bhs = Double.parseDouble((String) resultjg.get(0).get("cost_bhs")) * Math.ceil(Double.parseDouble(costSl));
					cost_hs = Double.parseDouble((String) resultjg.get(0).get("cost_hs")) * Math.ceil(Double.parseDouble(costSl));

				}else{
					cost_jg = Double.parseDouble((String) resultjg.get(0).get("cost_jg")) *  Double.parseDouble(costSl);
					cost_sl  = Double.parseDouble((String) resultjg.get(0).get("cost_sl")) *  Double.parseDouble(costSl);
					cost_bhs = Double.parseDouble((String) resultjg.get(0).get("cost_bhs")) *  Double.parseDouble(costSl);
					cost_hs = Double.parseDouble((String) resultjg.get(0).get("cost_hs")) *  Double.parseDouble(costSl);

				}

				beizhu="";
			}else{
				beizhu = "费用合同无此费率";
				cost_bhs  = 0.00  ;
				dayCostBhs  = 0.00 ;
				cost_hs  = 0.00  ;
			}
			ori = resulthq.get(i).get("cus_code").toString()+"/"+ t.getCostDate();
//					costSl = "1";
			WmDayCostEntity WmDayCost = new WmDayCostEntity();
			WmDayCost.setCreateBy("system");
			WmDayCost.setCreateDate(DateUtils.getDate());
			WmDayCost.setCostCode(costcode);
			WmDayCost.setCostData(t.getCostDate());
			WmDayCost.setCostOri(ori);
			WmDayCost.setCostSl(costSl);
			WmDayCost.setCostSta("已生成");
			WmDayCost.setCusCode(cuscode);
			WmDayCost.setCostUnit(jfunit);
			WmDayCost.setBeizhu(beizhu);
			dayCostYj  = cost_jg  ;
			dayCostBhs  = cost_bhs ;
			dayCostHsj  = cost_hs  ;
			dayCostSe  = dayCostHsj -  dayCostBhs ;
			DecimalFormat df=new DecimalFormat("#.00");
//					String st=df.format(d);
			WmDayCost.setDayCostYj(df.format(dayCostYj));
			WmDayCost.setDayCostBhs(df.format(dayCostBhs));
			WmDayCost.setDayCostSe(df.format(dayCostSe));
			WmDayCost.setDayCostHsj(df.format(dayCostHsj));
			systemService.save(WmDayCost);
		}//计算仓租
		//低温合计吨数
		Iterator iter = cusmap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			tsql = " select  cost_code  ,  cost_jg  ,  cost_sl  ,  cost_zk  ,  cost_bhs ,  cost_hs   "
					+ "from wm_cus_cost_h wch , wm_cus_cost_i wci where wch.id = wci.cus_cost_id and wci.cost_code = ? and wch.cus_code = ? and "
					+ "(to_days(wch.begin_date) <= to_days(?)  and to_days(wch.end_date) >= to_days(?)) limit 1";
			cuscode = (String) key;
			costSl =  val.toString();
			costcode = "1011";
			jfunit = "吨";
			List<Map<String, Object>> resultjg = systemService
					.findForJdbc(tsql,costcode,cuscode, t.getCostDate(), t.getCostDate());
			if(resultjg.size()>0){

				if(ResourceUtil.getConfigByName("jifeitype").equals("1")){
					cost_jg = Double.parseDouble((String) resultjg.get(0).get("cost_jg")) * Math.ceil(Double.parseDouble(costSl));
					cost_sl  = Double.parseDouble((String) resultjg.get(0).get("cost_sl")) * Math.ceil(Double.parseDouble(costSl));
					cost_bhs = Double.parseDouble((String) resultjg.get(0).get("cost_bhs")) * Math.ceil(Double.parseDouble(costSl));
					cost_hs = Double.parseDouble((String) resultjg.get(0).get("cost_hs")) * Math.ceil(Double.parseDouble(costSl));

				}else{
					cost_jg = Double.parseDouble((String) resultjg.get(0).get("cost_jg")) *  Double.parseDouble(costSl);
					cost_sl  = Double.parseDouble((String) resultjg.get(0).get("cost_sl")) *  Double.parseDouble(costSl);
					cost_bhs = Double.parseDouble((String) resultjg.get(0).get("cost_bhs")) *  Double.parseDouble(costSl);
					cost_hs = Double.parseDouble((String) resultjg.get(0).get("cost_hs")) *  Double.parseDouble(costSl);

				}
				beizhu="";
			}else{
				beizhu = "费用合同无此费率";
				cost_bhs  = 0.00  ;
				dayCostBhs  = 0.00 ;
				cost_hs  = 0.00  ;
			}
			ori = cuscode+"/"+ t.getCostDate();
			WmDayCostEntity WmDayCost = new WmDayCostEntity();
			WmDayCost.setCreateBy("system");
			WmDayCost.setCreateDate(DateUtils.getDate());
			WmDayCost.setCostCode(costcode);
			WmDayCost.setCostData(t.getCostDate());
			WmDayCost.setCostOri(ori);
			WmDayCost.setCostSl(costSl);
			WmDayCost.setCostSta("已生成");
			WmDayCost.setCusCode(cuscode);
			WmDayCost.setCostUnit(jfunit);
			WmDayCost.setBeizhu(beizhu);
			dayCostYj  = cost_jg  ;
			dayCostBhs  = cost_bhs ;
			dayCostHsj  = cost_hs  ;
			dayCostSe  = dayCostHsj -  dayCostBhs ;
			DecimalFormat df=new DecimalFormat("#.00");
//					String st=df.format(d);
			WmDayCost.setDayCostYj(df.format(dayCostYj));
			WmDayCost.setDayCostBhs(df.format(dayCostBhs));
			WmDayCost.setDayCostSe(df.format(dayCostSe));
			WmDayCost.setDayCostHsj(df.format(dayCostHsj));
			systemService.save(WmDayCost);
		}
		//低温合计SKU
		Iterator itersku = goodsmap.entrySet().iterator();
		while (itersku.hasNext()) {
			Map.Entry entrysku = (Map.Entry) itersku.next();
			Object keysku = entrysku.getKey();
			Object valsku = entrysku.getValue();
			tsql = " select  cost_code  ,  cost_jg  ,  cost_sl  ,  cost_zk  ,  cost_bhs ,  cost_hs   "
					+ "from wm_cus_cost_h wch , wm_cus_cost_i wci where wch.id = wci.cus_cost_id and wci.cost_code = ? and wch.cus_code = ? and "
					+ "(to_days(wch.begin_date) <= to_days(?)  and to_days(wch.end_date) >= to_days(?)) limit 1";
			String goodscode = (String) keysku;
			MdGoodsEntity mdg = systemService.findUniqueByProperty(MdGoodsEntity.class, "shpBianMa", goodscode);
			costSl =  valsku.toString();
			cuscode = mdg.getSuoShuKeHu();
			costcode = "1011";
			jfunit = "吨";
			List<Map<String, Object>> resultjgsku = systemService
					.findForJdbc(tsql,costcode,cuscode, t.getCostDate(), t.getCostDate());
			if(resultjgsku.size()>0){

				if(ResourceUtil.getConfigByName("jifeitype").equals("1")){
					cost_jg = Double.parseDouble((String) resultjgsku.get(0).get("cost_jg")) * Math.ceil(Double.parseDouble(costSl));
					cost_sl  = Double.parseDouble((String) resultjgsku.get(0).get("cost_sl")) * Math.ceil(Double.parseDouble(costSl));
					cost_bhs = Double.parseDouble((String) resultjgsku.get(0).get("cost_bhs")) * Math.ceil(Double.parseDouble(costSl));
					cost_hs = Double.parseDouble((String) resultjgsku.get(0).get("cost_hs")) * Math.ceil(Double.parseDouble(costSl));

				}else{
					cost_jg = Double.parseDouble((String) resultjgsku.get(0).get("cost_jg")) * Double.parseDouble(costSl);
					cost_sl  = Double.parseDouble((String) resultjgsku.get(0).get("cost_sl")) * Double.parseDouble(costSl);
					cost_bhs = Double.parseDouble((String) resultjgsku.get(0).get("cost_bhs")) * Double.parseDouble(costSl);
					cost_hs = Double.parseDouble((String) resultjgsku.get(0).get("cost_hs")) * Double.parseDouble(costSl);

				}
				beizhu="";
			}else{
				beizhu = "费用合同无此费率";
				cost_bhs  = 0.00  ;
				dayCostBhs  = 0.00 ;
				cost_hs  = 0.00  ;
			}
			ori = cuscode+"/"+goodscode+"/"+ t.getCostDate();
			WmDayCostEntity WmDayCostsku = new WmDayCostEntity();
			WmDayCostsku.setCreateBy("system");
			WmDayCostsku.setCreateDate(DateUtils.getDate());
			WmDayCostsku.setCostCode(costcode);
			WmDayCostsku.setCostData(t.getCostDate());
			WmDayCostsku.setCostOri(ori);
			WmDayCostsku.setCostSl(costSl);
			WmDayCostsku.setCostSta("已生成");
			WmDayCostsku.setCusCode(cuscode);
			WmDayCostsku.setCostUnit(jfunit);
			WmDayCostsku.setBeizhu(beizhu);
			dayCostYj  = cost_jg  ;
			dayCostBhs  = cost_bhs ;
			dayCostHsj  = cost_hs  ;
			dayCostSe  = dayCostHsj -  dayCostBhs ;
			DecimalFormat dfsku =new DecimalFormat("#.00");
//						String st=df.format(d);
			WmDayCostsku.setDayCostYj(dfsku.format(dayCostYj));
			WmDayCostsku.setDayCostBhs(dfsku.format(dayCostBhs));
			WmDayCostsku.setDayCostSe(dfsku.format(dayCostSe));
			WmDayCostsku.setDayCostHsj(dfsku.format(dayCostHsj));
			systemService.save(WmDayCostsku);
		}
		//超低温合计吨数
		Iterator iterc = cdwcusmap.entrySet().iterator();
		while (iterc.hasNext()) {
			Map.Entry entry = (Map.Entry) iterc.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			tsql = " select  cost_code  ,  cost_jg  ,  cost_sl  ,  cost_zk  ,  cost_bhs ,  cost_hs   "
					+ "from wm_cus_cost_h wch , wm_cus_cost_i wci where wch.id = wci.cus_cost_id and wci.cost_code = ? and wch.cus_code = ? and "
					+ "(to_days(wch.begin_date) <= to_days(?)  and to_days(wch.end_date) >= to_days(?)) limit 1";
			cuscode = (String) key;
			costSl =  val.toString();
			costcode = "1031";
			jfunit = "吨";
			List<Map<String, Object>> resultjg = systemService
					.findForJdbc(tsql,costcode,cuscode, t.getCostDate(), t.getCostDate());
			if(resultjg.size()>0){
				if(ResourceUtil.getConfigByName("jifeitype").equals("1")){
					cost_jg = Double.parseDouble((String) resultjg.get(0).get("cost_jg")) * Math.ceil(Double.parseDouble(costSl));
					cost_sl  = Double.parseDouble((String) resultjg.get(0).get("cost_sl")) * Math.ceil(Double.parseDouble(costSl));
					cost_bhs = Double.parseDouble((String) resultjg.get(0).get("cost_bhs")) * Math.ceil(Double.parseDouble(costSl));
					cost_hs = Double.parseDouble((String) resultjg.get(0).get("cost_hs")) * Math.ceil(Double.parseDouble(costSl));

				}else{
					cost_jg = Double.parseDouble((String) resultjg.get(0).get("cost_jg")) *  Double.parseDouble(costSl);
					cost_sl  = Double.parseDouble((String) resultjg.get(0).get("cost_sl")) *  Double.parseDouble(costSl);
					cost_bhs = Double.parseDouble((String) resultjg.get(0).get("cost_bhs")) *  Double.parseDouble(costSl);
					cost_hs = Double.parseDouble((String) resultjg.get(0).get("cost_hs")) *  Double.parseDouble(costSl);

				}
				beizhu="";
			}else{
				beizhu = "费用合同无此费率";
				cost_bhs  = 0.00  ;
				dayCostBhs  = 0.00 ;
				cost_hs  = 0.00  ;
			}
			ori = cuscode+"/"+ t.getCostDate();
			WmDayCostEntity WmDayCost = new WmDayCostEntity();
			WmDayCost.setCreateBy("system");
			WmDayCost.setCreateDate(DateUtils.getDate());
			WmDayCost.setCostCode(costcode);
			WmDayCost.setCostData(t.getCostDate());
			WmDayCost.setCostOri(ori);
			WmDayCost.setCostSl(costSl);
			WmDayCost.setCostSta("已生成");
			WmDayCost.setCusCode(cuscode);
			WmDayCost.setCostUnit(jfunit);
			WmDayCost.setBeizhu(beizhu);
			dayCostYj  = cost_jg  ;
			dayCostBhs  = cost_bhs ;
			dayCostHsj  = cost_hs  ;
			dayCostSe  = dayCostHsj -  dayCostBhs ;
			DecimalFormat df=new DecimalFormat("#.00");
//						String st=df.format(d);
			WmDayCost.setDayCostYj(df.format(dayCostYj));
			WmDayCost.setDayCostBhs(df.format(dayCostBhs));
			WmDayCost.setDayCostSe(df.format(dayCostSe));
			WmDayCost.setDayCostHsj(df.format(dayCostHsj));
			systemService.save(WmDayCost);
		}
		//超低温合计SKU
		Iterator itercsku = cdwgoodsmap.entrySet().iterator();
		while (itercsku.hasNext()) {
			Map.Entry entrycsku = (Map.Entry) itercsku.next();
			Object keysku = entrycsku.getKey();
			Object valsku = entrycsku.getValue();
			tsql = " select  cost_code  ,  cost_jg  ,  cost_sl  ,  cost_zk  ,  cost_bhs ,  cost_hs   "
					+ "from wm_cus_cost_h wch , wm_cus_cost_i wci where wch.id = wci.cus_cost_id and wci.cost_code = ? and wch.cus_code = ? and "
					+ "(to_days(wch.begin_date) <= to_days(?)  and to_days(wch.end_date) >= to_days(?)) limit 1";
			String goodscode = (String) keysku;
			MdGoodsEntity mdg = systemService.findUniqueByProperty(MdGoodsEntity.class, "shpBianMa", goodscode);
			costSl = valsku.toString();
			cuscode = mdg.getSuoShuKeHu();
			costcode = "1031";
			jfunit = "吨";
			List<Map<String, Object>> resultjgsku = systemService
					.findForJdbc(tsql,costcode,cuscode, t.getCostDate(), t.getCostDate());
			if(resultjgsku.size()>0){
				if(ResourceUtil.getConfigByName("jifeitype").equals("1")){
					cost_jg = Double.parseDouble((String) resultjgsku.get(0).get("cost_jg")) * Math.ceil(Double.parseDouble(costSl));
					cost_sl  = Double.parseDouble((String) resultjgsku.get(0).get("cost_sl")) * Math.ceil(Double.parseDouble(costSl));
					cost_bhs = Double.parseDouble((String) resultjgsku.get(0).get("cost_bhs")) * Math.ceil(Double.parseDouble(costSl));
					cost_hs = Double.parseDouble((String) resultjgsku.get(0).get("cost_hs")) * Math.ceil(Double.parseDouble(costSl));

				}else{
					cost_jg = Double.parseDouble((String) resultjgsku.get(0).get("cost_jg")) * Double.parseDouble(costSl);
					cost_sl  = Double.parseDouble((String) resultjgsku.get(0).get("cost_sl")) * Double.parseDouble(costSl);
					cost_bhs = Double.parseDouble((String) resultjgsku.get(0).get("cost_bhs")) * Double.parseDouble(costSl);
					cost_hs = Double.parseDouble((String) resultjgsku.get(0).get("cost_hs")) * Double.parseDouble(costSl);

				}
				beizhu="";
			}else{
				beizhu = "费用合同无此费率";
				cost_bhs  = 0.00  ;
				dayCostBhs  = 0.00 ;
				cost_hs  = 0.00  ;

			}
			ori = cuscode+"/"+goodscode+"/"+ t.getCostDate();
			WmDayCostEntity WmDayCostsku = new WmDayCostEntity();
			WmDayCostsku.setCreateBy("system");
			WmDayCostsku.setCreateDate(DateUtils.getDate());
			WmDayCostsku.setCostCode(costcode);
			WmDayCostsku.setCostData(t.getCostDate());
			WmDayCostsku.setCostOri(ori);
			WmDayCostsku.setCostSl(costSl);
			WmDayCostsku.setCostSta("已生成");
			WmDayCostsku.setCusCode(cuscode);
			WmDayCostsku.setCostUnit(jfunit);
			WmDayCostsku.setBeizhu(beizhu);
			dayCostYj  = cost_jg  ;
			dayCostBhs  = cost_bhs ;
			dayCostHsj  = cost_hs  ;
			dayCostSe  = dayCostHsj -  dayCostBhs ;
			DecimalFormat dfsku =new DecimalFormat("#.00");
//							String st=df.format(d);
			WmDayCostsku.setDayCostYj(dfsku.format(dayCostYj));
			WmDayCostsku.setDayCostBhs(dfsku.format(dayCostBhs));
			WmDayCostsku.setDayCostSe(dfsku.format(dayCostSe));
			WmDayCostsku.setDayCostHsj(dfsku.format(dayCostHsj));
			systemService.save(WmDayCostsku);
		}

	}
}
