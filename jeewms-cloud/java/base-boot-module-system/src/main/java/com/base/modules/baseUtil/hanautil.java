package com.base.modules.baseUtil;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class hanautil {


    private String  sap_hana ;
    private String  sap_user  ;
    private String  sap_password ;

    public String getSap_hana() {
        return sap_hana;
    }

    public void setSap_hana(String sap_hana) {
        this.sap_hana = sap_hana;
    }

    public String getSap_user() {
        return sap_user;
    }

    public void setSap_user(String sap_user) {
        this.sap_user = sap_user;
    }

    public String getSap_password() {
        return sap_password;
    }

    public void setSap_password(String sap_password) {
        this.sap_password = sap_password;
    }

    public void setPara(String sap_hanain, String  sap_userin, String  sap_passwordin){

        this.setSap_hana(sap_hanain);
        this.setSap_user(sap_userin);
        this.setSap_password(sap_passwordin);

    }

    //获取供应商
    public   List<FxjoutputEntity>   getlifnrlist(String bukrs, String lifnr)  {
        List<FxjoutputEntity> listres = new ArrayList<>();

        String hanasql = "select   a.LIFNR ,a.NAME1,a.sortl,a.stras,a.telf1 " +
                "from ecc.lfa1 as a left join ecc.lfb1 as  b on a.lifnr = b.lifnr " +
                " where b.bukrs = '"+bukrs+"' and a.mandt = '800' and a.KTOKK = 'Z001' ";
        if(StringUtil.isNotEmpty(lifnr)){
            hanasql = hanasql +  "  and a.lifnr =  '"+lifnr+"'  ";;
        }
        return  this.getbilist(hanasql);

    }

    //根据防伪码获取sap物料号
    public  String getmatkl(String ebeln,String ebelp,String matnr) {
//        org.jeecgframework.core.util.LogUtil.info("===================获取MATNR开始===================");

        String result=null;

//                String sap_hana = ResourceUtil.getConfigByName("sap_hana");
//                String sap_user = ResourceUtil.getConfigByName("sap_user");
//                String sap_password = ResourceUtil.getConfigByName("sap_password");
                try {
                    Connection con = this.getConnection("com.sap.db.jdbc.Driver", sap_hana, sap_user, sap_password);
                    PreparedStatement pstmt = con.prepareStatement("select T.MTART from ecc.mara t where t.mandt = '800' and t.matnr = '" + matnr + "'");
                    ResultSet rs = pstmt.executeQuery();
                    result = this.processResult(rs);
                    this.closeConnection(con, pstmt);
//                    if(sapEkpoEntity!=null) {
//
//                        sapEkpoEntity.setMtart(result);
//                        systemService.updateEntitie(sapEkpoEntity);
//                    }
//            org.jeecgframework.core.util.LogUtil.info("===================获取MATNRjieshu ===================");
                } catch (Exception e) {
                }

        return result;
    }
    //根据防伪码获取sap物料号
    public  String getzzs(String ebeln,String ebelp,String matnr) {
//        org.jeecgframework.core.util.LogUtil.info("===================获取MATNR开始===================");
        String result=null;

                 try{
                     Connection con = this.getConnection("com.sap.db.jdbc.Driver", sap_hana, sap_user, sap_password);
                     PreparedStatement pstmt = con.prepareStatement("select T.ZZS from ecc.mara t where t.mandt = '800' and t.matnr = '"+matnr+"'");
                     ResultSet rs = pstmt.executeQuery();
                     result = this.processResult(rs);
                     this.closeConnection(con, pstmt);
            //            org.jeecgframework.core.util.LogUtil.info("===================获取MATNRjieshu ===================");
                 }catch (Exception e){

                 }


        return result;
    }

    //根据防伪码获取sap物料号
    public  String getdomtext(String domname,String domvalue) {
//        org.jeecgframework.core.util.LogUtil.info("===================获取domname开始===================");

        String result=null;
        try{
            Connection con = this.getConnection("com.sap.db.jdbc.Driver", sap_hana, sap_user, sap_password);
            PreparedStatement pstmt = con.prepareStatement("select T.DDTEXT from ecc.DD07t t where t.ddlanguage = 1 and t.domname = '"+domname+"'"+"and t.domvalue_L = '"+domvalue+"'");
            ResultSet rs = pstmt.executeQuery();
            result = this.processResult(rs);
            this.closeConnection(con, pstmt);
//            org.jeecgframework.core.util.LogUtil.info("===================获取MATNRjieshu ===================");
        }catch (Exception e){

        }
        return result;
    }

//    select DDTEXT from ecc.DD07t where domname like 'ZZPHD' and ddlanguage =1 and domvalue_L= 'A'
    //根据防伪码获取sap物料号
    public  String getlqua(String matnr) {
//        org.jeecgframework.core.util.LogUtil.info("===================获取MATNR开始===================");


        String result=null;
        try{
            Connection con = this.getConnection("com.sap.db.jdbc.Driver", sap_hana, sap_user, sap_password);
            PreparedStatement pstmt = con.prepareStatement("select T.LGPLA from ecc.lqua t where t.mandt = '800' and t.matnr = '"+matnr+"'");
            ResultSet rs = pstmt.executeQuery();
            result = this.processResult(rs);
            this.closeConnection(con, pstmt);
//            org.jeecgframework.core.util.LogUtil.info("===================获取MATNRjieshu ===================");
        }catch (Exception e){

        }
        return result;
    }    //根据防伪码获取sap物料号
    public  String getBLDAT(String mblnr) {
//        org.jeecgframework.core.util.LogUtil.info("===================获取MATNR开始===================");


        String result=null;
        try{
            Connection con = this.getConnection("com.sap.db.jdbc.Driver", sap_hana, sap_user, sap_password);
            PreparedStatement pstmt = con.prepareStatement("select T.BLDAT from ecc.mkpf t where t.mandt = '800' and t.mblnr = '"+mblnr+"'");
            ResultSet rs = pstmt.executeQuery();
            result = this.processResult(rs);
            this.closeConnection(con, pstmt);
//            org.jeecgframework.core.util.LogUtil.info("===================获取MATNRjieshu ===================");
        }catch (Exception e){

        }
        return result;
    }

    //根据防伪码获取sap物料号
    public  String getxblnr(String mblnr) {
//        org.jeecgframework.core.util.LogUtil.info("===================获取MATNR开始===================");


        String result=null;
        try{
            Connection con = this.getConnection("com.sap.db.jdbc.Driver", sap_hana, sap_user, sap_password);
            PreparedStatement pstmt = con.prepareStatement("select T.xblnr from ecc.mkpf t where t.mandt = '800' and t.mblnr = '"+mblnr+"'");
            ResultSet rs = pstmt.executeQuery();
            result = this.processResult(rs);
            this.closeConnection(con, pstmt);
//            org.jeecgframework.core.util.LogUtil.info("===================获取MATNRjieshu ===================");
        }catch (Exception e){

        }
        return result;
    }
    //根据防伪码获取sap物料号
    public  String getekpolgort(String ebeln,String ebelp) {

        String result=null;
        try{
            Connection con = this.getConnection("com.sap.db.jdbc.Driver", sap_hana, sap_user, sap_password);
            PreparedStatement pstmt = con.prepareStatement("select T.LGORT from ecc.ekpo t where t.mandt = '800' and t.ebeln = '"+ebeln+"'" +"and t.ebelp = '"+ebelp+"'");
            ResultSet rs = pstmt.executeQuery();
            result = this.processResult(rs);
            this.closeConnection(con, pstmt);
        }catch (Exception e){

        }
        return result;
    }
    //根据防伪码获取sap物料号
    public  String getwq(String ebeln,String ebelp) {

        String result=null;
        try{
            Connection con = this.getConnection("com.sap.db.jdbc.Driver", sap_hana, sap_user, sap_password);
            PreparedStatement pstmt = con.prepareStatement("select   ifnull(sum( (case   when shkzg = 'H'  then  (0 - abs(menge)) else abs(menge)  end   )  ),0) as menge  " +
                    "from  ecc.ekbe where mandt = '800' and BEWTP = 'E' and bwart in ('101','102','161','162','105','106')  and ebeln = '"+ebeln+"'"  + " and ebelp = '"+ebelp+"'");
            ResultSet rs = pstmt.executeQuery();
            result = this.processResult(rs);
            this.closeConnection(con, pstmt);
//            org.jeecgframework.core.util.LogUtil.info("===================获取MATNRjieshu ===================");
        }catch (Exception e){

        }


        return result;


    }


    public  String getkgl(String ebeln,String ebelp) {

        String result=null;
        try{
            Connection con = this.getConnection("com.sap.db.jdbc.Driver", sap_hana, sap_user, sap_password);
            PreparedStatement pstmt = con.prepareStatement("select   ifnull(sum( (case   when shkzg = 'H'  then  (0 - abs(menge)) else abs(menge)  end   )  ),0) as menge  " +
                    "from  ecc.mseg where mandt = '800' and XAUTO = 'X'  and bwart in ('541','542','543','544')  and ebeln = '"+ebeln+"'"  + " and ebelp = '"+ebelp+"'");
            ResultSet rs = pstmt.executeQuery();
            result = this.processResult(rs);
            this.closeConnection(con, pstmt);
//            org.jeecgframework.core.util.LogUtil.info("===================获取MATNRjieshu ===================");
        }catch (Exception e){

        }


        return result;


    }
    public  String getkgmatnr(String ebeln,String ebelp) {
//        org.jeecgframework.core.util.LogUtil.info("===================获取w开始===================");
        String result=null;

            try {
                Connection con = this.getConnection("com.sap.db.jdbc.Driver", sap_hana, sap_user, sap_password);
                PreparedStatement pstmt = con.prepareStatement("select    matnr  " +
                        "from  ecc.mseg where mandt = '800' and XAUTO = 'X'  and bwart in ('541','542')  and ebeln = '" + ebeln + "'" + " and ebelp = '" + ebelp + "'");
                ResultSet rs = pstmt.executeQuery();
                result = this.processResult(rs);
                this.closeConnection(con, pstmt);
//            org.jeecgframework.core.util.LogUtil.info("===================获取MATNRjieshu ===================");
            } catch (Exception e) {

            }

        return result;
    }
    //获取采购订单
    public   List<FxjoutputEntity>   getekpo(String lifnr, String ebeln)  {
        List<FxjoutputEntity> listres = new ArrayList<>();

        String hanasql = "     \n" +
                "  select    \n" +
                "      sap_ekko.zzph,\n" +
                "    sap_ekko.zwxdkc,\n" +
                "    sap_ekko.zsfbl,\n" +
                "     sap_ekko.ekorg, \n" +
                "    sap_ekko.bsart,\n" +
                "    sap_ekko.bedat,\n" +
                "    sap_ekko.lifnr,\n" +
                "    sap_lfa1.name1,\n" +
                "    sap_ekko.ebeln,\n" +
                "    sap_ekpo.ebelp,\n" +
                "    sap_ekpo.matnr,\n" +
                "    sap_EKPO.txz01,\n" +
                "    sap_ekpo.meins,\n" +
                "    sap_ekpo.idnlf,\n" +
                "    sap_ekpo.matkl,\n" +
                "    sap_ekpo.netpr, \n" +
                "    sap_ekpo.menge, \n" +
                "    sap_ekpo.mwskz,\n" +
                "\n" +
                "  (select sapeket.eindt from ecc.eket as sapeket where sapeket.ebeln  = sap_ekpo.ebeln and sapeket.ebelp  = sap_ekpo.ebelp  and sapeket.etenr = '0001') as eindt ," +
                " sap_ekko.ekgrp," +
                "(select   ifnull(sum( (case   when sapekbe.shkzg = 'H'  then  (0 - abs(sapekbe.menge)) else abs(sapekbe.menge)  end   )  ),0) as menge  \n" +
                "                     from  ecc.ekbe as sapekbe where sapekbe.mandt = '800' and sapekbe.BEWTP = 'E' and sapekbe.bwart in ('101','102','161','162','105','106')  and sapekbe.ebeln  = sap_ekpo.ebeln and sapekbe.ebelp = sap_ekpo.ebelp   ) as wq , " +
                "  (select sapp.PTEXT from ecc.ZEKPOTEXT as sapp where sapp.ebeln = sap_ekpo.ebeln and sapp.ebelp = sap_ekpo.ebelp ) as ptext  ," +
                "sap_ekpo.RETPO," +
                "sap_ekpo.ELIKZ," +
                "sap_ekpo.UEBTO," +
                "sap_ekpo.LOEKZ " +
                "from  ecc.ekko as  sap_ekko inner join ecc.ekpo as sap_ekpo on sap_ekko.ebeln = sap_ekpo.ebeln  inner join ecc.lfa1 as sap_lfa1 " +
                "on sap_lfa1.lifnr = sap_ekko.lifnr where sap_ekko.lifnr not in('0000002020','0000001070') and sap_ekpo.lgort not in ('R996','R997','R999') and  sap_ekpo.werks in( '2021','1000') and  sap_ekko.BSART not like 'Y%' and  sap_ekpo.mandt = '800' and   sap_ekko.BSART not like 'R%' and sap_ekpo.KNTTP not in ('A','F','K')  " ;
        if(StringUtil.isNotEmpty(lifnr)){
            hanasql = hanasql +
                "  and sap_ekko.lifnr =  '"+lifnr+"'  ";
        }
        if(StringUtil.isNotEmpty(ebeln)){
            if("all".equals(ebeln)){
                hanasql = hanasql + "  and   (sap_ekko.aedat >= ADD_DAYS(CURRENT_DATE ,-400)   or sap_ekpo.aedat >= ADD_DAYS(CURRENT_DATE, -7) or sap_ekko.ebeln in( select sap_ekbe.ebeln from ecc.ekbe as sap_ekbe where  sap_ekbe.cpudt >= ADD_DAYS(CURRENT_DATE, -7)))";

            }else{
                hanasql = hanasql +
                        "  and sap_ekko.ebeln =  '"+ebeln+"'  ";
            }

        }else{
            hanasql = hanasql + "  and   (sap_ekko.aedat >= ADD_DAYS(CURRENT_DATE ,-7)   or sap_ekpo.aedat >= ADD_DAYS(CURRENT_DATE, -7) or sap_ekko.ebeln in( select sap_ekbe.ebeln from ecc.ekbe as sap_ekbe where  sap_ekbe.cpudt >= ADD_DAYS(CURRENT_DATE, -7)))";
        }


        return  this.getbilist(hanasql);

    }

    //获取采购订单
    public   List<FxjoutputEntity>   getekposmall(String lifnr, String ebeln)  {
        List<FxjoutputEntity> listres = new ArrayList<>();

        String hanasql = "     \n" +
                "  select    \n" +
                "      sap_ekko.zzph,\n" +
                "    sap_ekko.zwxdkc,\n" +
                "    sap_ekko.zsfbl,\n" +
                "     sap_ekko.ekorg, \n" +
                "    sap_ekko.bsart,\n" +
                "    sap_ekko.bedat,\n" +
                "    sap_ekko.lifnr,\n" +
                "    sap_lfa1.name1,\n" +
                "    sap_ekko.ebeln,\n" +
                "    sap_ekpo.ebelp,\n" +
                "    sap_ekpo.matnr,\n" +
                "    sap_EKPO.txz01,\n" +
                "    sap_ekpo.meins,\n" +
                "    sap_ekpo.idnlf,\n" +
                "    sap_ekpo.matkl,\n" +
                "    sap_ekpo.netpr, \n" +
                "    sap_ekpo.menge, \n" +
                "    sap_ekpo.mwskz,\n" +
                "\n" +
                "  (select sapeket.eindt from ecc.eket as sapeket where sapeket.ebeln  = sap_ekpo.ebeln and sapeket.ebelp  = sap_ekpo.ebelp  and sapeket.etenr = '0001') as eindt ," +
                " sap_ekko.ekgrp," +
                "(select   ifnull(sum( (case   when sapekbe.shkzg = 'H'  then  (0 - abs(sapekbe.menge)) else abs(sapekbe.menge)  end   )  ),0) as menge  \n" +
                "                     from  ecc.ekbe as sapekbe where sapekbe.mandt = '800' and sapekbe.BEWTP = 'E' and sapekbe.bwart in ('101','102','161','162','105','106')  and sapekbe.ebeln  = sap_ekpo.ebeln and sapekbe.ebelp = sap_ekpo.ebelp   ) as wq , " +
                "  (select sapp.PTEXT from ecc.ZEKPOTEXT as sapp where sapp.ebeln = sap_ekpo.ebeln and sapp.ebelp = sap_ekpo.ebelp ) as ptext  ," +
                "sap_ekpo.RETPO," +
                "sap_ekpo.ELIKZ," +
                "sap_ekpo.UEBTO," +
                "sap_ekpo.LOEKZ " +
                "from  ecc.ekko as  sap_ekko inner join ecc.ekpo as sap_ekpo on sap_ekko.ebeln = sap_ekpo.ebeln  inner join ecc.lfa1 as sap_lfa1 " +
                "on sap_lfa1.lifnr = sap_ekko.lifnr where sap_ekko.lifnr not in('0000002020','0000001070') and sap_ekpo.lgort not in ('R996','R997','R999') and  sap_ekpo.werks in( '2021','1000') and  sap_ekko.BSART not like 'Y%' and  sap_ekpo.mandt = '800' and   sap_ekko.BSART not like 'R%' and sap_ekpo.KNTTP not in ('A','F','K')  " ;
        if(StringUtil.isNotEmpty(lifnr)){
            hanasql = hanasql +
                    "  and sap_ekko.lifnr =  '"+lifnr+"'  ";
        }
        if(StringUtil.isNotEmpty(ebeln)){
            if("all".equals(ebeln)){
                hanasql = hanasql + "  and   (sap_ekko.aedat >= ADD_DAYS(CURRENT_DATE ,-400)   or sap_ekpo.aedat >= ADD_DAYS(CURRENT_DATE, -2) or sap_ekko.ebeln in( select sap_ekbe.ebeln from ecc.ekbe as sap_ekbe where  sap_ekbe.cpudt >= ADD_DAYS(CURRENT_DATE, -2)))";

            }else{
                hanasql = hanasql +
                        "  and sap_ekko.ebeln =  '"+ebeln+"'  ";
            }

        }else{
            hanasql = hanasql + "  and   (sap_ekko.aedat >= ADD_DAYS(CURRENT_DATE ,-2)   or sap_ekpo.aedat >= ADD_DAYS(CURRENT_DATE, -2) or sap_ekko.ebeln in( select sap_ekbe.ebeln from ecc.ekbe as sap_ekbe where  sap_ekbe.cpudt >= ADD_DAYS(CURRENT_DATE, -2)))";
        }


        return  this.getbilist(hanasql);

    }
//    sap_ekpo.LOEKZ <> 'X' and sap_ekpo.ELIKZ <> 'X' and
    //获取收货信息
    public   List<FxjoutputEntity>   getekbe(String lifnr)  {
        List<FxjoutputEntity> listres = new ArrayList<>();


        String hanasql = "   select    \n" +
                "      sap_ekko.zzph,\n" +
                "    sap_ekko.zwxdkc,\n" +
                "    sap_ekko.zsfbl,\n" +
                "     sap_ekko.ekorg, \n" +
                "    sap_ekko.bsart,\n" +
                "    sap_ekko.bedat,\n" +
                "    sap_ekko.lifnr,\n" +
                "    sap_lfa1.name1,\n" +
                "    sap_ekko.ebeln,\n" +
                "    sap_ekpo.ebelp,\n" +
                "    sap_ekpo.matnr,\n" +
                "    sap_EKPO.txz01,\n" +
                "    sap_ekpo.meins,\n" +
                "    sap_ekpo.idnlf,\n" +
                "    sap_ekpo.matkl,\n" +
                "    sap_ekpo.netpr, \n" +
                "    sap_ekbe.menge, \n" +
                "    sap_ekpo.mwskz,\n" +
                "    sap_ekbe.gjahr,\n" +
                "    sap_ekbe.belnr,\n" +
                "    sap_ekbe.buzei,\n" +
                "    '' as bewtp,\n" +
                "    sap_ekbe.bwart,\n" +
                "    sap_ekbe.dmbtr,\n" +
                "    sap_ekbe.BUALT as wrbtr,\n" +
                "    sap_ekbe.waers,\n" +
                "    sap_ekbe.shkzg ,\n" +
                "\n" +
                "   (select sapeket.eindt from ecc.eket as sapeket where sapeket.ebeln  = sap_ekpo.ebeln and sapeket.ebelp  = sap_ekpo.ebelp  and sapeket.etenr = '0001') as eindt ,sap_ekbe.MENGE as WESBS from  ecc.ekko as  sap_ekko inner join ecc.ekpo as sap_ekpo on sap_ekko.ebeln = sap_ekpo.ebeln \n" +
                "  inner join ecc.lfa1 as sap_lfa1 on sap_lfa1.lifnr = sap_ekko.lifnr\n" +
                "   inner join ecc.mseg as  sap_ekbe  on sap_ekbe.ebeln = sap_ekpo.ebeln  and sap_ekbe.ebelp = sap_ekpo.ebelp \n" +
                "   where sap_ekko.lifnr not in('0000002020','0000001070') and sap_ekbe.xauto <> 'X' and sap_ekpo.lgort not in ('R996','R997','R999') and  sap_ekpo.werks in( '2021','1000')  and sap_ekko.BSART not like 'Y%' and   sap_ekko.BSART not like 'R%'   and  sap_ekpo.mandt = '800'  and sap_ekpo.KNTTP not in ('A','F','K') and  sap_ekbe.CPUDT_MKPF >= ADD_DAYS(CURRENT_DATE, -7) " ;
        if(StringUtil.isNotEmpty(lifnr)){
            hanasql = hanasql +
                    " and sap_ekko.lifnr =  '"+lifnr+"'  ";
        }

        return  this.getbilist(hanasql);

    }

    //根据防伪码获取sap物料号
    public  String  getBione(String hanasql)  {
//        org.jeecgframework.core.util.LogUtil.info("==================="+hanasql+"获取BI单个数据开始===================");


        String result=null;
        try{
            Connection con = this.getConnection("com.sap.db.jdbc.Driver", sap_hana, sap_user, sap_password);
            PreparedStatement pstmt = con.prepareStatement( hanasql);
            ResultSet rs = pstmt.executeQuery();
            result = this.processResult(rs);
            this.closeConnection(con, pstmt);
        }catch ( Exception e){

        }

//        org.jeecgframework.core.util.LogUtil.info("===================获取BI单个数据+"+result+" ===================");
        return result;
    }

    public List<FxjoutputEntity>   getbilist(String hanasql)  {

        List<FxjoutputEntity> listres = new ArrayList<>();

        try{
            Connection con = this.getConnection("com.sap.db.jdbc.Driver", sap_hana, sap_user, sap_password);
            PreparedStatement pstmt = con.prepareStatement(hanasql);
            ResultSet rs = pstmt.executeQuery();
            List<Map<String ,Map<String ,String>>> resultout = new ArrayList<>();

            resultout = this.processResulttoMap(rs);
            listres = this.processMaptoList(resultout);
            this.closeConnection(con, pstmt);
        }catch ( Exception e){

        }
        return listres;
    }





    private List<Map<String ,Map<String ,String>>> processResulttoMap(ResultSet rs) throws Exception {
         List<Map<String ,Map<String ,String>>> result = new ArrayList<>();
        if (rs.next()) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int colNum = rsmd.getColumnCount();
            do {
                Map<String ,Map<String ,String>> maprow = new HashMap<String ,Map<String ,String>>();
                Map<String ,String> map = new HashMap<String ,String>();
                for (int i = 1; i <= colNum; i++) {
                    map.put(Integer.toString(i),rs.getString(i));
                }
                maprow.put("0",map);
                result.add(maprow);
            } while (rs.next());
        }
        return  result;
    }

    private   List<FxjoutputEntity>  processMaptoList(List<Map<String ,Map<String ,String>>> result ){
        List<FxjoutputEntity> listres = new ArrayList<>();
        for (int i = 0;i< result.size();i++){
            FxjoutputEntity wmientity = new FxjoutputEntity();
            Map<String ,Map<String ,String>>   mapall = result.get(i);
            wmientity.setFxjx1(mapall.get("0").get("1"));
            wmientity.setFxjx2(mapall.get("0").get("2"));
            wmientity.setFxjx3(mapall.get("0").get("3"));
            wmientity.setFxjx4(mapall.get("0").get("4"));
            try{ wmientity.setFxjx5(mapall.get("0").get("5"));}catch (Exception e){}
            try{ wmientity.setFxjx6(mapall.get("0").get("6"));}catch (Exception e){}
            try{ wmientity.setFxjx7(mapall.get("0").get("7"));}catch (Exception e){}
            try{ wmientity.setFxjx8(mapall.get("0").get("8"));}catch (Exception e){}
            try{ wmientity.setFxjx9(mapall.get("0").get("9"));}catch (Exception e){}
            try{ wmientity.setFxjx10(mapall.get("0").get("10"));}catch (Exception e){}
            try{ wmientity.setFxjx11(mapall.get("0").get("11"));}catch (Exception e){}
            try{ wmientity.setFxjx12(mapall.get("0").get("12"));}catch (Exception e){}
            try{ wmientity.setFxjx13(mapall.get("0").get("13"));}catch (Exception e){}
            try{ wmientity.setFxjx14(mapall.get("0").get("14"));}catch (Exception e){}
            try{ wmientity.setFxjx15(mapall.get("0").get("15"));}catch (Exception e){}
            try{ wmientity.setFxjx16(mapall.get("0").get("16"));}catch (Exception e){}
            try{ wmientity.setFxjx17(mapall.get("0").get("17"));}catch (Exception e){}
            try{ wmientity.setFxjx18(mapall.get("0").get("18"));}catch (Exception e){}
            try{ wmientity.setFxjx19(mapall.get("0").get("19"));}catch (Exception e){}
            try{ wmientity.setFxjx20(mapall.get("0").get("20"));}catch (Exception e){}
            try{ wmientity.setFxjx21(mapall.get("0").get("21"));}catch (Exception e){}
            try{ wmientity.setFxjx22(mapall.get("0").get("22"));}catch (Exception e){}
            try{ wmientity.setFxjx23(mapall.get("0").get("23"));}catch (Exception e){}
            try{ wmientity.setFxjx24(mapall.get("0").get("24"));}catch (Exception e){}
            try{ wmientity.setFxjx25(mapall.get("0").get("25"));}catch (Exception e){}
            try{ wmientity.setFxjx26(mapall.get("0").get("26"));}catch (Exception e){}
            try{ wmientity.setFxjx27(mapall.get("0").get("27"));}catch (Exception e){}
            try{ wmientity.setFxjx28(mapall.get("0").get("28"));}catch (Exception e){}
            try{ wmientity.setFxjx29(mapall.get("0").get("29"));}catch (Exception e){}
            try{ wmientity.setFxjx30(mapall.get("0").get("30"));}catch (Exception e){}
            try{ wmientity.setFxjx31(mapall.get("0").get("31"));}catch (Exception e){}
            try{ wmientity.setFxjx32(mapall.get("0").get("32"));}catch (Exception e){}
            try{ wmientity.setFxjx33(mapall.get("0").get("33"));}catch (Exception e){}
            try{ wmientity.setFxjx34(mapall.get("0").get("34"));}catch (Exception e){}

            listres.add(wmientity);
        }
        return  listres;
    }


    private String processResult(ResultSet rs) throws Exception {
        String result=null;
        if (rs.next()) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int colNum = rsmd.getColumnCount();

            do {
                for (int i = 1; i <= colNum; i++) {
                    if (i == 1) {
                        result  = rs.getString(i);
                    } else {
                        result = result + ":"+rs.getString(i);
                    }

                }

            } while (rs.next());
        } else {
            System.out.println("query not result.");
        }
        return  result;
    }

    private Connection getConnection(String driver, String url, String user,
                                     String password) throws Exception {
        Class.forName(driver);
        return DriverManager.getConnection(url, user, password);

    }

    private void closeConnection(Connection con, Statement stmt)
            throws Exception {
        if (stmt != null) {
            stmt.close();
        }
        if (con != null) {
            con.close();
        }
    }


    /**
     * 将一个map组成的list转成实体类bean组成的list
     * @param mapList 存了map对象的list
     * @param clazz 需要将这些map转成哪个实体类对象
     * @return
     */
    public <T> List<T> convertMapListToBeanList(List<Map<String,Object>> mapList,Class<T> clazz){
        List<T> list=new ArrayList<T>();
        for(Map map:mapList){
            try {
                T obj=clazz.newInstance();//创建bean的实例对象
                for(Object o:map.keySet()){//遍历map的key
                    for(Method m:clazz.getMethods()){//遍历bean的类中的方法，找到set方法进行赋值
                        if(m.getName().toLowerCase().equals("set"+o.toString().toLowerCase())){
                            m.invoke(obj, map.get(o));
                        }
                    }
                }
                list.add(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


}


