package com.zzjee.wmutil;

import com.zzjee.bireport.entity.RpPeriodInOutEntity;
import com.zzjee.conf.entity.FxjOtherLoginEntity;
import com.zzjee.md.entity.MdBinEntity;
import com.zzjee.md.entity.MdGoodsEntity;
import com.zzjee.md.entity.MvGoodsEntity;
import com.zzjee.sys.entity.SysParaEntity;
import com.zzjee.wm.entity.WvStockEntity;
import org.antlr.stringtemplate.language.Cat;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.*;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.sms.service.TSSmsSqlServiceI;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.StringUtil;

import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;

import java.io.*;
import java.util.*;

/**
 * User: caoez
 * Date: 13-7-26
 * Time: 下午2:07
 */


public class wmUtil {

    public static List removeDuplicate(List list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    public synchronized static void genrp(String datafrom, String datato, String username) {
        SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);

        String tsql = "call update_rp_period_in_out(" + "'" + datafrom + "'," + "'" + datato + "'," + "'" + username + "'" + ")";
        try {
            systemService.executeSql(tsql);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static boolean checkys(String goodsid, String scrq) {
        boolean isaccept = true;
        String pz = "A";
        try {
            //			  1,获取允收配置。
            pz = ResourceUtil.getConfigByName("wms.yskz");
        } catch (Exception e) {

        }
        if ("A".equals(pz)) {
            return isaccept;
        } else {
            //		      2,获取物料。
            SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
            List<MdGoodsEntity> mdGoodsEntityList = systemService.findByProperty(MdGoodsEntity.class, "shpBianMa", goodsid);
            String bzhiqi = mdGoodsEntityList.get(0).getBzhiQi();
            String zhlKgm = mdGoodsEntityList.get(0).getZhlKgm();
//		      3,计算结果。   保质期减去允收天数  大于等于当前日期减去生产日期
            Calendar now = DateUtils.getCalendar();
            int ygrq = 0;
            int ysrq = 0;
            try {
                ysrq = Integer.parseInt(bzhiqi) - Integer.parseInt(zhlKgm);
            } catch (Exception e) {

            }
            try {
                Calendar scrqc = DateUtils.parseCalendar(scrq, "yyyy-MM-dd");
                ygrq = DateUtils.dateDiff('d', now, scrqc);
            } catch (Exception e) {

            }
            if (ygrq > ysrq) {
                isaccept = false;
            }
            return isaccept;
        }

    }


    public synchronized static void genrp2(String datafrom, String datato, String username) {
        SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);

        String tsql = "i";
        try {
            List<RpPeriodInOutEntity> forJdbc = systemService.findHql(tsql);
            forJdbc.forEach(System.out::println);
        } catch (Exception e) {
        }
    }

    public synchronized static String getNextNoticeid(String orderType) {
        String noticeid = null;
        SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);

        Map<String, Object> countMap = systemService
                .findOneForJdbc("SELECT cast(right(ifnull((notice_id),0),4)+1  as SIGNED) as count FROM wm_im_notice_h  t where  TO_DAYS(t.create_date) = TO_DAYS(NOW()) order by create_date desc, RIGHT(IFNULL((notice_id), 0), 4)  desc limit 1");
        int newcount = 1;

        try {
            newcount = ((Long) countMap.get("count")).intValue();
        } catch (Exception e) {
            newcount = 1;
        }

        if (StringUtil.isEmpty(orderType)) {
            orderType = "01";
        }
        if (orderType.equals("03")) {
            noticeid = "TH"
                    + DateUtils.date2Str(new Date(), DateUtils.yyyyMMdd)
                    + "-"
                    + StringUtil.leftPad(
                    newcount, 4,
                    '0');
        } else if (orderType.equals("01")) {
            noticeid = "RK"
                    + DateUtils.date2Str(new Date(), DateUtils.yyyyMMdd)
                    + "-"
                    + StringUtil.leftPad(
                    newcount, 4,
                    '0');
        } else if (orderType.equals("04")) {
            noticeid = "YK"
                    + DateUtils.date2Str(new Date(), DateUtils.yyyyMMdd)
                    + "-"
                    + StringUtil.leftPad(
                    newcount, 4,
                    '0');
        } else if (orderType.equals("09")) {
            noticeid = "QTRK"
                    + DateUtils.date2Str(new Date(), DateUtils.yyyyMMdd)
                    + "-"
                    + StringUtil.leftPad(
                    newcount, 4,
                    '0');
        } else {
            noticeid = "QT"
                    + DateUtils.date2Str(new Date(), DateUtils.yyyyMMdd)
                    + "-"
                    + StringUtil.leftPad(
                    newcount, 4,
                    '0');
        }


        return noticeid;
    }

    public synchronized static String getNextomNoticeIdtms(String orderType) {
        SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
        Map<String, Object> countMap = systemService
                .findOneForJdbc("SELECT cast(right(ifnull((om_notice_id),0),4)+1 as SIGNED) as count FROM tms_om_notice_h  t where  TO_DAYS(t.create_date) = TO_DAYS(NOW()) order by create_date desc, right(ifnull((om_notice_id),0),4) desc limit 1");
        String noticeid = null;
        int newcount = 1;

        try {
            newcount = ((Long) countMap.get("count")).intValue();
        } catch (Exception e) {
        }
        if (StringUtil.isEmpty(orderType)) {
            orderType = "11";
        }
        if (orderType.equals("19")) {
            noticeid = "QTCK"
                    + DateUtils.date2Str(new Date(), DateUtils.yyyyMMdd)
                    + "-"
                    + StringUtil.leftPad(
                    newcount, 4,
                    '0');
        } else if (orderType.equals("11")) {
            noticeid = "CK"
                    + DateUtils.date2Str(new Date(), DateUtils.yyyyMMdd)
                    + "-"
                    + StringUtil.leftPad(
                    newcount, 4,
                    '0');
        } else {
            noticeid = "QT"
                    + DateUtils.date2Str(new Date(), DateUtils.yyyyMMdd)
                    + "-"
                    + StringUtil.leftPad(
                    newcount, 4,
                    '0');
        }
        return noticeid;
    }

    public synchronized static String getNextomNoticeId(String orderType) {
        SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
        Map<String, Object> countMap = systemService
                .findOneForJdbc("SELECT cast(right(ifnull((om_notice_id),0),4)+1 as SIGNED) as count FROM wm_om_notice_h  t where  TO_DAYS(t.create_date) = TO_DAYS(NOW()) order by create_date desc, right(ifnull((om_notice_id),0),4) desc limit 1");
        String noticeid = null;
        int newcount = 1;

        try {
            newcount = ((Long) countMap.get("count")).intValue();
        } catch (Exception e) {
        }
        if (StringUtil.isEmpty(orderType)) {
            orderType = "11";
        }
        if (orderType.equals("19")) {
            noticeid = "QTCK"
                    + DateUtils.date2Str(new Date(), DateUtils.yyyyMMdd)
                    + "-"
                    + StringUtil.leftPad(
                    newcount, 4,
                    '0');
        } else if (orderType.equals("11")) {
            noticeid = "CK"
                    + DateUtils.date2Str(new Date(), DateUtils.yyyyMMdd)
                    + "-"
                    + StringUtil.leftPad(
                    newcount, 4,
                    '0');
        } else {
            noticeid = "QT"
                    + DateUtils.date2Str(new Date(), DateUtils.yyyyMMdd)
                    + "-"
                    + StringUtil.leftPad(
                    newcount, 4,
                    '0');
        }
        return noticeid;
    }

    public static String getSysPar(String parType, String username) {
        String parvalue = null;
        try {
            SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
            if (StringUtil.isEmpty(username)) {
                String hql = " from SysParaEntity where parType = ?";
                SysParaEntity syspar = (SysParaEntity) systemService.findHql(hql, parType).get(0);
                parvalue = syspar.getParValue();
            } else {
                String hql = " from SysParaEntity where parType = ? and parUsername = ?";
                SysParaEntity syspar = (SysParaEntity) systemService.findHql(hql, parType, username).get(0);
                parvalue = syspar.getParValue();
            }
        } catch (Exception e) {

        }

        return parvalue;
    }

    public static void saveSysPar(String parType, String username, String parvalue) {
        SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
        if (StringUtil.isEmpty(username)) {
            String hql = " from SysParaEntity where parType = ?";
            SysParaEntity syspar = (SysParaEntity) systemService.findHql(hql, parType).get(0);
            if (syspar == null) {
                syspar = new SysParaEntity();
                syspar.setParType(parType);
                syspar.setParValue(parType);
                systemService.save(syspar);
            } else {
                syspar.setParType(parType);
                syspar.setParValue(parType);
                systemService.updateEntitie(syspar);
            }
        } else {
            String hql = " from SysParaEntity where parType = ? and parUsername = ?";
            SysParaEntity syspar = (SysParaEntity) systemService.findHql(hql, parType, username).get(0);
            if (syspar == null) {
                syspar = new SysParaEntity();
                syspar.setParType(parType);
                syspar.setParUsername(username);
                syspar.setParValue(parType);
                systemService.save(syspar);
            } else {
                syspar.setParType(parType);
                syspar.setParUsername(username);
                syspar.setParValue(parType);
                systemService.updateEntitie(syspar);
            }
        }
    }

    public static String getCurrentDepartCode() {
        TSUser tsUser = ResourceUtil.getSessionUserName();
        if (tsUser != null) {
            return tsUser.getCurrentDepart().getOrgCode();
        } else {
            return null;
        }
    }


    //通过客户商品编码，或者WMS商品编码和单位找到WMS编码
    public static Map<String, String> getGoodsId(String cusCode, String cusgoodsid, String goodsUnit) {
        Map<String, String> resultmap = new HashMap<>();
        String goodsCode = null;
        SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
        String tsql = "select cus_code,chailing, goods_id,goods_code,shp_bian_makh,shl_dan_wei,baseunit" +
                " from  mv_goods where goods_id = ? or shp_bian_makh = ?  and cus_code = ? order by chailing desc";
        List<Map<String, Object>> result = systemService.findForJdbc(tsql, cusgoodsid, cusgoodsid, cusCode);
        if (result.size() > 0) {
            try {
                for (int i = 0; i < result.size(); i++) {
                    if (goodsUnit.equals(result.get(i).get("baseunit").toString())) {
                        goodsCode = result.get(i).get("goods_code").toString();
                        cusCode = result.get(i).get("cus_code").toString();
                        resultmap.put("goodsCode", goodsCode);
                        resultmap.put("cusCode", cusCode);
                        break;
                    }
                    if (goodsUnit.equals(result.get(i).get("shl_dan_wei").toString())) {
                        goodsCode = result.get(i).get("goods_id").toString();
                        cusCode = result.get(i).get("cus_code").toString();
                        resultmap.put("goodsCode", goodsCode);
                        resultmap.put("cusCode", cusCode);
                        break;
                    }

                }

            } catch (Exception e) {

            }
        }
        return resultmap;
    }

    public static String getCusCode() {
        SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
        String cusCode = "";
        String cusrole = "";
        try {
            cusrole = ResourceUtil.getConfigByName("cus.role");

        } catch (Exception e) {
            cusrole = "CUS";

        }
        try {
            TSUser user = ResourceUtil.getSessionUserName();
            String roles = "";
            if (user != null) {
                List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
                for (TSRoleUser ru : rUsers) {

                    TSRole role = ru.getTSRole();
                    roles += role.getRoleCode() + ",";
                    System.out.println("role.getRoleCode()========" + role.getRoleCode());
                }
                System.out.println("roles========" + roles);
                System.out.println("cusrole========" + cusrole);
                if (StringUtil.strPos(roles, cusrole)) {
                    cusCode = user.getUserName();
                }
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return cusCode;
    }


    public static String gettuopanma() {
        String tuopanma = "";
        if (ResourceUtil.getConfigByName("usetuopan").equals("no")) {
            try {
                if (StringUtil.isNotEmpty(ResourceUtil.getConfigByName("tuopanma"))) {
                    tuopanma = ResourceUtil.getConfigByName("tuopanma");
                } else {
                    tuopanma = DateUtils.getDataString(DateUtils.yyyymmddhhmmss);
                }
            } catch (Exception e) {

            }

        }
        return tuopanma;
    }

    /**
     * 删除指定文件夹下所有文件
     *
     * @param
     * @return
     */
    public static boolean checkstcok(String binid, String tinid, String goodsid, String prodate, String basecount) {
        boolean flag = false;
        try {
            String goods = null;
            if (!StringUtil.isEmpty(goodsid)) {
                if (goodsid.endsWith("l")) {
                    goods = goodsid.substring(0, goodsid.length() - 1);
                    System.out.print("11111111I" + goods);
                } else {
                    goods = goodsid;
                    System.out.print("22222" + goods);

                }

            }
            SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
            String tsql = "select ws.base_unit,ws.zhong_wen_qch, ws.ku_wei_bian_ma,ws.bin_id,ws.shp_ming_cheng,cast(sum(ws.base_goodscount) as signed) as goods_qua, mb.qu_huo_ci_xu, ws.goods_pro_data"
                    + "  from wv_stock ws, md_bin mb  where "
                    + "   ws.ku_wei_bian_ma = mb.ku_wei_bian_ma and mb.ting_yong <> 'Y' and (ws.kuctype = '库存' or ws.kuctype = '待下架')"
                    + "   and ws.ku_wei_bian_ma = ? "
                    + "   and ws.bin_id =  ? "
                    + "   and ws.goods_id =  ? "
                    + "   group by ws.ku_wei_bian_ma,ws.bin_id,ws.goods_id,mb.qu_huo_ci_xu, ws.goods_pro_data order by ws.goods_pro_data , ws.goods_qua ,mb.qu_huo_ci_xu,ws.create_date desc";
            List<Map<String, Object>> result = systemService.findForJdbc(tsql, binid, tinid, goods);
            if (result.size() > 0) {
                if (Double.parseDouble(result.get(0).get("goods_qua").toString()) >= Double.parseDouble(basecount)) {
                    flag = true;
                }
            }
        } catch (Exception e) {

        }

        return flag;
    }


    public static boolean checkishavestock(String checktype, String checkvalue) {
        boolean ishavestock = false;
        String hql = "from WvStockEntity where ";
        if ("bin".equals(checktype)) {
            hql = hql + " kuWeiBianMa = ?";
        }
        if ("cus".equals(checktype)) {
            hql = hql + " cusCode = ?";

        }
        if ("goods".equals(checktype)) {
            hql = hql + " goodsId = ?";

        }
        SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);

        List<WvStockEntity> list = systemService.findHql(hql, checkvalue);
        if (list != null && list.size() > 0 && list.get(0).getGoodsQua() > 0.01) {//判断库存不为0
            ishavestock = true;
        }
        return ishavestock;

    }


    public static String getstock(String goodsid) {
        String goodsqua = "0";
        try {
            String goods = null;
            if (!StringUtil.isEmpty(goodsid)) {
                if (goodsid.endsWith("l")) {
                    goods = goodsid.substring(0, goodsid.length() - 1);
                    System.out.print("11111111I" + goods);
                } else {
                    goods = goodsid;
                    System.out.print("22222" + goods);

                }

            }
            SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
            String tsql = " select cast(sum(ws.base_goodscount) as signed) as goods_qua"
                    + "  from wv_stock ws where "
                    + "     ws.goods_id =  ? "
                    + "   group by ws.goods_id";

            List<Map<String, Object>> result = systemService.findForJdbc(tsql, goods);
            if (result.size() > 0) {
                goodsqua = result.get(0).get("goods_qua").toString();
            }
        } catch (Exception e) {
        }
        return goodsqua;
    }


    public static boolean checkstcoka(String binid, String tinid, String goodsid, String prodate, String basecount) {
        boolean flag = false;
        try {
            String goods = null;
            if (!StringUtil.isEmpty(goodsid)) {
                if (goodsid.endsWith("l")) {
                    goods = goodsid.substring(0, goodsid.length() - 1);
                    System.out.print("11111111I" + goods);
                } else {
                    goods = goodsid;
                    System.out.print("22222" + goods);

                }

            }
            SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
            String tsql = "select ws.base_unit,ws.zhong_wen_qch, ws.ku_wei_bian_ma,ws.bin_id,ws.shp_ming_cheng,cast(sum(ws.base_goodscount) as signed) as goods_qua, mb.qu_huo_ci_xu, ws.goods_pro_data"
                    + "  from wv_stock ws, md_bin mb  where "
                    + "   ws.ku_wei_bian_ma = mb.ku_wei_bian_ma and mb.ting_yong <> 'Y' and (ws.kuctype = '库存' )"
                    + "   and ws.ku_wei_bian_ma = ? "
                    + "   and ws.bin_id =  ? "
                    + "   and ws.goods_id =  ? "
                    + "   group by ws.ku_wei_bian_ma,ws.bin_id,ws.goods_id,mb.qu_huo_ci_xu order by ws.goods_pro_data , ws.goods_qua ,mb.qu_huo_ci_xu,ws.create_date desc";

            List<Map<String, Object>> result = systemService.findForJdbc(tsql, binid, tinid, goods);
            if (result.size() > 0) {
                if (Double.parseDouble(result.get(0).get("goods_qua").toString()) >= Double.parseDouble(basecount)) {
                    flag = true;
                }

            }
        } catch (Exception e) {

        }
        return flag;
    }


    public static boolean checkbin(String binid) {
        boolean flag = false;
        SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
        try {
            MdBinEntity mdBinEntity = systemService.findUniqueByProperty(MdBinEntity.class, "kuWeiBianMa", binid);
            if (mdBinEntity != null) {
                if ("N".equals(mdBinEntity.getTingYong())) {
                    flag = true;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static String getscrp() {
        if ("no".equals(ResourceUtil.getConfigByName("scrqon"))) {
            if (StringUtil.isNotEmpty(ResourceUtil.getConfigByName("scrq"))) {
                return ResourceUtil.getConfigByName("scrq");
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static String getmdgoodsbytiaoma(String tiaoma) {
        if ("yes".equals(ResourceUtil.getConfigByName("sptmon"))) {
            try {
                SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);

                String ttr = "";

                List<MdGoodsEntity> t = systemService.findByProperty(MdGoodsEntity.class, "shpTiaoMa", tiaoma);
                for (MdGoodsEntity tt : t) {
                    if (StringUtil.isNotEmpty(ttr)) {
                        ttr = ttr + "," + tt.getShpBianMa();
                    } else {
                        ttr = tt.getShpBianMa();
                    }
                }
                return ttr;
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static boolean checkstcokk(String cuscode, String binid, String tinid, String goodsid, String prodate, String basecount) {
        boolean flag = false;
        try {
            String goods = null;
            if (!StringUtil.isEmpty(goodsid)) {
                if (goodsid.endsWith("l")) {
                    goods = goodsid.substring(0, goodsid.length() - 1);
                    System.out.print("11111111I" + goods);
                } else {
                    goods = goodsid;
                    System.out.print("22222" + goods);

                }

            }
            SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
            String tsql = "select ws.base_unit,ws.zhong_wen_qch, ws.ku_wei_bian_ma,ws.bin_id,ws.shp_ming_cheng,cast(sum(ws.base_goodscount) as signed) as goods_qua, mb.qu_huo_ci_xu, ws.goods_pro_data"
                    + "  from wv_stock ws, md_bin mb  where "
                    + "   ws.ku_wei_bian_ma = mb.ku_wei_bian_ma and mb.ting_yong <> 'Y' and (ws.kuctype = '库存' )"
                    + "   and ws.ku_wei_bian_ma = ? "
                    + "   and ws.bin_id =  ? "
                    + "   and ws.goods_id =  ? "
                    + "   and ws.cus_code = ?   "
                    + "   group by ws.ku_wei_bian_ma,ws.bin_id,ws.goods_id,mb.qu_huo_ci_xu, ws.goods_pro_data order by ws.goods_pro_data , ws.goods_qua ,mb.qu_huo_ci_xu,ws.create_date desc";

            List<Map<String, Object>> result = systemService.findForJdbc(tsql, binid, tinid, goods, cuscode);
            if (result.size() > 0) {
                if (Double.parseDouble(result.get(0).get("goods_qua").toString()) >= Double.parseDouble(basecount)) {
                    flag = true;
                }

            }
        } catch (Exception e) {

        }
        return flag;
    }

    public static TSUser getsysorgcode(String sysuser) {
        SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
        try {
            FxjOtherLoginEntity fxjOtherLoginEntity = systemService.findUniqueByProperty(FxjOtherLoginEntity.class, "otherid", sysuser);
            if (fxjOtherLoginEntity != null) {
                sysuser = fxjOtherLoginEntity.getUsername();
            }
        } catch (Exception e) {

        }
        TSUser task = systemService.findUniqueByProperty(TSUser.class, "userName", sysuser);
//		if(task==null){
//			sysuser=ResourceUtil.getConfigByName("mini.user");
//			task = systemService.findUniqueByProperty(TSUser.class,"userName",sysuser);
//		}
        if (task != null) {
            try {
                TSDepart tsDepart = systemService.get(TSDepart.class, task.getDepartid());
                if (tsDepart != null) {
                    tsDepart.setOrgCode(tsDepart.getOrgCode());
                    task.setCurrentDepart(tsDepart);
                }
            } catch (Exception e) {

            }
        }
        return task;
    }
}
