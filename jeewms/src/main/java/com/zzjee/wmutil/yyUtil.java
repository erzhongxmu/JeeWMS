package com.zzjee.wmutil;

import com.alibaba.fastjson.JSONObject;
import com.xiaoleilu.hutool.http.HttpUtil;
import com.zzjee.ba.entity.BaGoodsTypeEntity;
import com.zzjee.ba.entity.BaUnitEntity;
import com.zzjee.md.entity.MdCusOtherEntity;
import com.zzjee.md.entity.MdGoodsEntity;
import com.zzjee.md.entity.MdSupEntity;
import com.zzjee.md.entity.MvGoodsEntity;
import com.zzjee.wm.entity.WmImNoticeHEntity;
import com.zzjee.wm.entity.WmImNoticeIEntity;
import com.zzjee.wm.entity.WmOmNoticeHEntity;
import com.zzjee.wm.entity.WmOmNoticeIEntity;
import com.zzjee.wm.service.WmImNoticeHServiceI;
import com.zzjee.wm.service.WmOmNoticeHServiceI;
import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.*;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.util.*;

public class yyUtil {
    /**
     * User: caoez
     * Date: 18-09-01
     * Time: 下午2:07
     */

    public static void    getProduct(String othercode) {

        String dbKey=  ResourceUtil.getConfigByName("yydbkey");
        List<Map<String, Object>> result=null;
        List<Map<String, Object>> resultdw=null;
        List<Map<String, Object>> resultgt=null;
        String querySql = "select * from Inventory";
        if(StringUtil.isNotEmpty(othercode)){
            querySql = "select * from Inventory where cInvCCode like '%"+ othercode+"%'" ;
        }
        Map queryparams =  new LinkedHashMap<String,Object>();
        SystemService systemService =ApplicationContextUtil.getContext().getBean(SystemService.class);


        if(StringUtils.isNotBlank(dbKey)) {
            result = DynamicDBUtil.findList(dbKey, SqlUtil.jeecgCreatePageSql(dbKey, querySql, queryparams, 1, 1000000));
        }


        querySql = "select * from ComputationUnit";

        if(StringUtils.isNotBlank(dbKey)) {
            resultdw = DynamicDBUtil.findList(dbKey, SqlUtil.jeecgCreatePageSql(dbKey, querySql, queryparams, 1, 1000000));
        }

        querySql = "select * from InventoryClass where iInvCGrade = '1' ";
        if(StringUtils.isNotBlank(dbKey)) {
            resultgt = DynamicDBUtil.findList(dbKey, SqlUtil.jeecgCreatePageSql(dbKey, querySql, queryparams, 1, 1000000));
        }
        if (resultgt!=null&&resultgt.size()>0) {
            for (int i = 0; i < resultgt.size(); i++) {
                Map<String, Object> prodbo = resultgt.get(i);
                BaGoodsTypeEntity mdn = new BaGoodsTypeEntity();
                mdn.setGoodsTypeCode(prodbo.get("cInvCCode").toString());
                mdn.setGoodsTypeName(prodbo.get("cInvCName").toString());
                try {
                    BaGoodsTypeEntity bagoodsType = systemService.findUniqueByProperty(
                            BaGoodsTypeEntity.class, "goodsTypeCode", mdn.getGoodsTypeCode());
                    if(bagoodsType==null){
                        bagoodsType = new BaGoodsTypeEntity();
                        MyBeanUtils.copyBeanNotNull2Bean(mdn,bagoodsType);
                        systemService.save(bagoodsType);
                    }else{
                        MyBeanUtils.copyBeanNotNull2Bean(mdn,bagoodsType);
                        systemService.updateEntitie(bagoodsType);
                    }
                }catch (Exception e){
                }
            }
        }


        if (resultdw!=null&&resultdw.size()>0) {
            for (int i = 0; i < resultdw.size(); i++) {
                Map<String, Object> prodbo = resultdw.get(i);
                BaUnitEntity mdn = new BaUnitEntity();
                mdn.setUnitCode(prodbo.get("cComunitCode").toString());
                mdn.setUnitZhName(prodbo.get("cComUnitName").toString());
                try{
                    mdn.setUnitChange(prodbo.get("iChangRate").toString());

                }catch (Exception e){
                    mdn.setUnitChange("1");

                }

                try {
                    BaUnitEntity baunit = systemService.findUniqueByProperty(
                            BaUnitEntity.class, "unitCode", mdn.getUnitCode());
                    if(baunit==null){
                        baunit = new BaUnitEntity();
                        MyBeanUtils.copyBeanNotNull2Bean(mdn,baunit);
                        systemService.save(baunit);
                    }else{
                        MyBeanUtils.copyBeanNotNull2Bean(mdn,baunit);
                        systemService.updateEntitie(baunit);
                    }
                }catch (Exception e){
                }
            }
        }
        if (result!=null&&result.size()>0){
            for(int i = 0;i<result.size();i++){
                Map<String, Object> prodbo = result.get(i);
                String ShpBianMa =  prodbo.get("cInvCode").toString();
                MdGoodsEntity mdn = new MdGoodsEntity();
                mdn.setShpBianMa(ShpBianMa);
                mdn.setSuoShuKeHu("hwm");
                mdn.setShpMingCheng(prodbo.get("cInvName").toString());
                mdn.setBzhiQi("360");
                try{
                    mdn.setChpShuXing(prodbo.get("cInvCCode").toString().substring(0,2));

                }catch (Exception e){

                }
//                mdn.setChpShuXing("其他");
                try{
                    mdn.setShpGuiGe(prodbo.get("cInvStd").toString());
                }catch (Exception e){

                }


                mdn.setJshDanWei(prodbo.get("cComUnitCode").toString());//拆零单位
                BaUnitEntity baunit = systemService.findUniqueByProperty(
                        BaUnitEntity.class, "unitCode", mdn.getJshDanWei());
                if (baunit !=null){
                    mdn.setJshDanWei(baunit.getUnitZhName());//拆零单位
                }
                try{
                mdn.setShlDanWei(prodbo.get("cSAComUnitCode").toString());//单位
                }catch (Exception e){

                }
                if(StringUtil.isEmpty(mdn.getShlDanWei())){
                    mdn.setChlShl("1");
                    mdn.setChlKongZhi("N");
                    mdn.setShlDanWei(baunit.getUnitZhName());

                }else{
                    try{
                        BaUnitEntity baunitz = systemService.findUniqueByProperty(
                                BaUnitEntity.class, "unitCode", mdn.getShlDanWei());
                        if (baunitz !=null){
                            mdn.setShlDanWei(baunitz.getUnitZhName());
                        }
                        mdn.setChlShl(baunitz.getUnitChange());
                        mdn.setChlKongZhi("Y");
                    }catch (Exception e){

                    }

                }
                try {
                    mdn.setShpTiaoMa(prodbo.get("cBarCode").toString());
                }catch (Exception e){
                }
                mdn.setCfWenCeng("低温");
                mdn.setJiZhunwendu("1");
                mdn.setTiJiCm("1");
                mdn.setZhlKg("1");
                mdn.setJfShpLei("10");
                mdn.setMpCengGao("99");
                mdn.setMpDanCeng("99");

            try {
                MdGoodsEntity mdGoods = systemService.findUniqueByProperty(
                        MdGoodsEntity.class, "shpBianMa", ShpBianMa);
                if(mdGoods==null){
                    mdGoods = new MdGoodsEntity();
                    MyBeanUtils.copyBeanNotNull2Bean(mdn,mdGoods);
                    systemService.save(mdGoods);
                }else{
                    MyBeanUtils.copyBeanNotNull2Bean(mdn,mdGoods);
                    systemService.updateEntitie(mdGoods);
                }
            }catch (Exception e){
            }
            }
        }
    }
    public static  void  getPord(String indate) {
//        PO_Pomain  采购订单主表
//        PO_Podetails  采购订单子表
        String dbKey=  ResourceUtil.getConfigByName("yydbkey");
        List<Map<String, Object>> result=null;
        List<Map<String, Object>> resultdetail=null;
        String querySql = "select * from RdRecord01 where ddate = '"+indate+"'";
        Map queryparams =  new LinkedHashMap<String,Object>();

        SystemService systemService =ApplicationContextUtil.getContext().getBean(SystemService.class);
        WmImNoticeHServiceI wmImNoticeHService =ApplicationContextUtil.getContext().getBean(WmImNoticeHServiceI.class);

        if(StringUtils.isNotBlank(dbKey)) {
            result = DynamicDBUtil.findList(dbKey, SqlUtil.jeecgCreatePageSql(dbKey, querySql, queryparams, 1, 1000000));
        }
        if (result!=null&&result.size()>0) {
            for (int i = 0; i < result.size(); i++) {
                try{
                    Thread.sleep(3000);

                }catch (Exception e){

                }
                Map<String, Object> prodbo = result.get(i);
                String poid =  prodbo.get("id").toString();
                String otherid =  prodbo.get("cCode").toString();

                if (StringUtil.isNotEmpty(poid)) {
                    WmImNoticeHEntity wmimh = systemService.findUniqueByProperty(WmImNoticeHEntity.class, "imCusCode", otherid);
                    if (wmimh == null) {
                        WmImNoticeHEntity wmImNoticeH = new WmImNoticeHEntity();
                        List<WmImNoticeIEntity> wmImNoticeIListnew = new ArrayList<WmImNoticeIEntity>();

                        wmImNoticeH.setOrderTypeCode("01");
                        String noticeid = wmUtil.getNextNoticeid(wmImNoticeH.getOrderTypeCode());

                        wmImNoticeH.setCusCode(ResourceUtil.getConfigByName("yy.cuscode"));
                        wmImNoticeH.setNoticeId(noticeid);
//                        wmImNoticeH.setPlatformCode(Integer.toString(billResult.getData().get(s).getPiId()));
//                        wmImNoticeH.setPiClass(billResult.getData().get(s).getPiClass());
//                        wmImNoticeH.setPiMaster(master);
//                        wmImNoticeH.setSupCode(billResult.getData().get(s).getPiCardcode());
//                        MdSupEntity mdsup = systemService.findUniqueByProperty(MdSupEntity.class, "gysBianMa", wmImNoticeH.getSupCode());
//                        if (mdsup != null) {
//                            wmImNoticeH.setSupName(mdsup.getZhongWenQch());
//                        }
                        try{
                            wmImNoticeH.setImBeizhu(prodbo.get("cMemo").toString());
                        }catch (Exception e){

                        }
                        wmImNoticeH.setImCusCode(otherid);
                        String querySqldetail = "select * from RdRecords01 where id = '"+poid+"'";
                        if (resultdetail!=null){
                            resultdetail.clear();
                        }
                        resultdetail = DynamicDBUtil.findList(dbKey, SqlUtil.jeecgCreatePageSql(dbKey, querySqldetail, queryparams, 1, 1000000));

                        for (int k = 0; k < resultdetail.size(); k++) {
                            WmImNoticeIEntity wmi = new WmImNoticeIEntity();
                            Map<String, Object> proddet = resultdetail.get(k);
                            wmi.setGoodsCode(proddet.get("cInvCode").toString());
                            MvGoodsEntity mvgoods = systemService.findUniqueByProperty(
                                    MvGoodsEntity.class, "goodsCode", wmi.getGoodsCode());
                            if (mvgoods != null) {
                                wmi.setGoodsName(mvgoods.getGoodsName());
                                wmi.setGoodsUnit(mvgoods.getShlDanWei());
                            }
                            wmi.setGoodsCount(Long.toString(new BigDecimal(proddet.get("iQuantity").toString()).setScale(0, RoundingMode.UP).longValue()));
//                               wmi.setGoodsPrdData(billResult.getData().get(s).getDetail().get(k).getPdProdmadedate2User());
//                            wmi.setOtherId();
                            wmImNoticeIListnew.add(wmi);
                        }
                        wmImNoticeHService.addMain(wmImNoticeH, wmImNoticeIListnew);
                    }
                } else {
                    continue;
                }
            }
        }
    }

    public static  void  getcprd(String indate) {
//        PO_Pomain  成品入库主表
//        PO_Podetails  成品入库子表
        String dbKey=  ResourceUtil.getConfigByName("yydbkey");
        List<Map<String, Object>> result=null;
        List<Map<String, Object>> resultdetail=null;
        String querySql = "select * from RdRecord10 where ddate = '"+indate+"'";
        Map queryparams =  new LinkedHashMap<String,Object>();

        SystemService systemService =ApplicationContextUtil.getContext().getBean(SystemService.class);
        WmImNoticeHServiceI wmImNoticeHService =ApplicationContextUtil.getContext().getBean(WmImNoticeHServiceI.class);

        if(StringUtils.isNotBlank(dbKey)) {
            result = DynamicDBUtil.findList(dbKey, SqlUtil.jeecgCreatePageSql(dbKey, querySql, queryparams, 1, 1000000));
        }
        if (result!=null&&result.size()>0) {
            for (int i = 0; i < result.size(); i++) {
                try{
                    Thread.sleep(3000);

                }catch (Exception e){

                }
                Map<String, Object> prodbo = result.get(i);
                String poid =  prodbo.get("id").toString();
                String otherid =  prodbo.get("cCode").toString();

                if (StringUtil.isNotEmpty(poid)) {
                    WmImNoticeHEntity wmimh = systemService.findUniqueByProperty(WmImNoticeHEntity.class, "imCusCode", otherid);
                    if (wmimh == null) {
                        WmImNoticeHEntity wmImNoticeH = new WmImNoticeHEntity();
                        List<WmImNoticeIEntity> wmImNoticeIListnew = new ArrayList<WmImNoticeIEntity>();

                        wmImNoticeH.setOrderTypeCode("03");
                        String noticeid = wmUtil.getNextNoticeid(wmImNoticeH.getOrderTypeCode());

                        wmImNoticeH.setCusCode(ResourceUtil.getConfigByName("yy.cuscode"));
                        wmImNoticeH.setNoticeId(noticeid);
//                        wmImNoticeH.setPlatformCode(Integer.toString(billResult.getData().get(s).getPiId()));
//                        wmImNoticeH.setPiClass(billResult.getData().get(s).getPiClass());
//                        wmImNoticeH.setPiMaster(master);
//                        wmImNoticeH.setSupCode(billResult.getData().get(s).getPiCardcode());
//                        MdSupEntity mdsup = systemService.findUniqueByProperty(MdSupEntity.class, "gysBianMa", wmImNoticeH.getSupCode());
//                        if (mdsup != null) {
//                            wmImNoticeH.setSupName(mdsup.getZhongWenQch());
//                        }
                        try{
                            wmImNoticeH.setImBeizhu(prodbo.get("cMemo").toString());
                        }catch (Exception e){

                        }
//                        wmImNoticeH.setImCusCode(poid);
                        wmImNoticeH.setImCusCode(otherid);
                        String querySqldetail = "select * from RdRecords10 where id = '"+poid+"'";
                        if (resultdetail!=null){
                            resultdetail.clear();
                        }
                        resultdetail = DynamicDBUtil.findList(dbKey, SqlUtil.jeecgCreatePageSql(dbKey, querySqldetail, queryparams, 1, 1000000));

                        for (int k = 0; k < resultdetail.size(); k++) {
                            WmImNoticeIEntity wmi = new WmImNoticeIEntity();
                            Map<String, Object> proddet = resultdetail.get(k);
                            wmi.setGoodsCode(proddet.get("cInvCode").toString());
                            MvGoodsEntity mvgoods = systemService.findUniqueByProperty(
                                    MvGoodsEntity.class, "goodsCode", wmi.getGoodsCode());
                            if (mvgoods != null) {
                                wmi.setGoodsName(mvgoods.getGoodsName());
                                wmi.setGoodsUnit(mvgoods.getShlDanWei());
                            }
                            wmi.setGoodsCount(Long.toString(new BigDecimal(proddet.get("iQuantity").toString()).setScale(0, RoundingMode.UP).longValue()));
//                               wmi.setGoodsPrdData(billResult.getData().get(s).getDetail().get(k).getPdProdmadedate2User());
//                            wmi.setOtherId();
                            wmImNoticeIListnew.add(wmi);
                        }
                        wmImNoticeHService.addMain(wmImNoticeH, wmImNoticeIListnew);
                    }
                } else {
                    continue;
                }
            }
        }
    }
    public static  void  getqtrd(String indate) {
//        PO_Pomain  其他入库主表
//        PO_Podetails  其他入库子表
        String dbKey=  ResourceUtil.getConfigByName("yydbkey");
        List<Map<String, Object>> result=null;
        List<Map<String, Object>> resultdetail=null;
        String querySql = "select * from RdRecord08 where ddate = '"+indate+"'";
        Map queryparams =  new LinkedHashMap<String,Object>();

        SystemService systemService =ApplicationContextUtil.getContext().getBean(SystemService.class);
        WmImNoticeHServiceI wmImNoticeHService =ApplicationContextUtil.getContext().getBean(WmImNoticeHServiceI.class);

        if(StringUtils.isNotBlank(dbKey)) {
            result = DynamicDBUtil.findList(dbKey, SqlUtil.jeecgCreatePageSql(dbKey, querySql, queryparams, 1, 1000000));
        }
        if (result!=null&&result.size()>0) {
            try{
                Thread.sleep(3000);

            }catch (Exception e){

            }
            for (int i = 0; i < result.size(); i++) {
                Map<String, Object> prodbo = result.get(i);
                String poid =  prodbo.get("id").toString();
                String otherid =  prodbo.get("cCode").toString();

                if (StringUtil.isNotEmpty(poid)) {
                    WmImNoticeHEntity wmimh = systemService.findUniqueByProperty(WmImNoticeHEntity.class, "imCusCode", otherid);
                    if (wmimh == null) {
                        WmImNoticeHEntity wmImNoticeH = new WmImNoticeHEntity();
                        List<WmImNoticeIEntity> wmImNoticeIListnew = new ArrayList<WmImNoticeIEntity>();

                        wmImNoticeH.setOrderTypeCode("09");
                        String noticeid = wmUtil.getNextNoticeid(wmImNoticeH.getOrderTypeCode());

                        wmImNoticeH.setCusCode(ResourceUtil.getConfigByName("yy.cuscode"));
                        wmImNoticeH.setNoticeId(noticeid);
//                        wmImNoticeH.setPlatformCode(Integer.toString(billResult.getData().get(s).getPiId()));
//                        wmImNoticeH.setPiClass(billResult.getData().get(s).getPiClass());
//                        wmImNoticeH.setPiMaster(master);
//                        wmImNoticeH.setSupCode(billResult.getData().get(s).getPiCardcode());
//                        MdSupEntity mdsup = systemService.findUniqueByProperty(MdSupEntity.class, "gysBianMa", wmImNoticeH.getSupCode());
//                        if (mdsup != null) {
//                            wmImNoticeH.setSupName(mdsup.getZhongWenQch());
//                        }
                        try{
                            wmImNoticeH.setImBeizhu(prodbo.get("cMemo").toString());
                        }catch (Exception e){

                        }
                        wmImNoticeH.setImCusCode(otherid);
                        String querySqldetail = "select * from RdRecords08 where id = '"+poid+"'";
                        if (resultdetail!=null){
                            resultdetail.clear();
                        }
                        resultdetail = DynamicDBUtil.findList(dbKey, SqlUtil.jeecgCreatePageSql(dbKey, querySqldetail, queryparams, 1, 1000000));

                        for (int k = 0; k < resultdetail.size(); k++) {
                            WmImNoticeIEntity wmi = new WmImNoticeIEntity();
                            Map<String, Object> proddet = resultdetail.get(k);
                            wmi.setGoodsCode(proddet.get("cInvCode").toString());
                            MvGoodsEntity mvgoods = systemService.findUniqueByProperty(
                                    MvGoodsEntity.class, "goodsCode", wmi.getGoodsCode());
                            if (mvgoods != null) {
                                wmi.setGoodsName(mvgoods.getGoodsName());
                                wmi.setGoodsUnit(mvgoods.getShlDanWei());
                            }
                            wmi.setGoodsCount(Long.toString(new BigDecimal(proddet.get("iQuantity").toString()).setScale(0, RoundingMode.UP).longValue()));
//                               wmi.setGoodsPrdData(billResult.getData().get(s).getDetail().get(k).getPdProdmadedate2User());
//                            wmi.setOtherId();
                            wmImNoticeIListnew.add(wmi);
                        }
                        wmImNoticeHService.addMain(wmImNoticeH, wmImNoticeIListnew);
                    }
                } else {
                    continue;
                }
            }
        }
    }
    public static  void  getPo(String indate) {
//        PO_Pomain  采购订单主表
//        PO_Podetails  采购订单子表
        String dbKey=  ResourceUtil.getConfigByName("yydbkey");
        List<Map<String, Object>> result=null;
        List<Map<String, Object>> resultdetail=null;
        String querySql = "select * from PO_Pomain where dpodate = '"+indate+"'";
        Map queryparams =  new LinkedHashMap<String,Object>();

        SystemService systemService =ApplicationContextUtil.getContext().getBean(SystemService.class);
        WmImNoticeHServiceI wmImNoticeHService =ApplicationContextUtil.getContext().getBean(WmImNoticeHServiceI.class);

        if(StringUtils.isNotBlank(dbKey)) {
            result = DynamicDBUtil.findList(dbKey, SqlUtil.jeecgCreatePageSql(dbKey, querySql, queryparams, 1, 1000000));
        }
        if (result!=null&&result.size()>0) {
            for (int i = 0; i < result.size(); i++) {
                try{
                    Thread.sleep(3000);

                }catch (Exception e){

                }
                Map<String, Object> prodbo = result.get(i);
                String poid =  prodbo.get("poid").toString();

                if (StringUtil.isNotEmpty(poid)) {
                    WmImNoticeHEntity wmimh = systemService.findUniqueByProperty(WmImNoticeHEntity.class, "imCusCode", poid);
                    if (wmimh == null) {
                        WmImNoticeHEntity wmImNoticeH = new WmImNoticeHEntity();
                        List<WmImNoticeIEntity> wmImNoticeIListnew = new ArrayList<WmImNoticeIEntity>();

                        wmImNoticeH.setOrderTypeCode("01");
                        String noticeid = wmUtil.getNextNoticeid(wmImNoticeH.getOrderTypeCode());

                        wmImNoticeH.setCusCode(ResourceUtil.getConfigByName("yy.cuscode"));
                        wmImNoticeH.setNoticeId(noticeid);
//                        wmImNoticeH.setPlatformCode(Integer.toString(billResult.getData().get(s).getPiId()));
//                        wmImNoticeH.setPiClass(billResult.getData().get(s).getPiClass());
//                        wmImNoticeH.setPiMaster(master);
//                        wmImNoticeH.setSupCode(billResult.getData().get(s).getPiCardcode());
//                        MdSupEntity mdsup = systemService.findUniqueByProperty(MdSupEntity.class, "gysBianMa", wmImNoticeH.getSupCode());
//                        if (mdsup != null) {
//                            wmImNoticeH.setSupName(mdsup.getZhongWenQch());
//                        }
                        try{
                            wmImNoticeH.setImBeizhu(prodbo.get("cMemo").toString());
                        }catch (Exception e){

                        }
                        wmImNoticeH.setImCusCode(poid);
                        String querySqldetail = "select * from PO_Podetails where poid = '"+poid+"'";
                        if (resultdetail!=null){
                            resultdetail.clear();
                        }
                        resultdetail = DynamicDBUtil.findList(dbKey, SqlUtil.jeecgCreatePageSql(dbKey, querySqldetail, queryparams, 1, 1000000));

                        for (int k = 0; k < resultdetail.size(); k++) {
                            WmImNoticeIEntity wmi = new WmImNoticeIEntity();
                            Map<String, Object> proddet = resultdetail.get(k);
                            wmi.setGoodsCode(proddet.get("cInvCode").toString());
                            MvGoodsEntity mvgoods = systemService.findUniqueByProperty(
                                    MvGoodsEntity.class, "goodsCode", wmi.getGoodsCode());
                            if (mvgoods != null) {
                                wmi.setGoodsName(mvgoods.getGoodsName());
                                wmi.setGoodsUnit(mvgoods.getShlDanWei());
                            }
                            wmi.setGoodsCount(Long.toString(new BigDecimal(proddet.get("iQuantity").toString()).setScale(0, RoundingMode.UP).longValue()));
//                               wmi.setGoodsPrdData(billResult.getData().get(s).getDetail().get(k).getPdProdmadedate2User());
//                            wmi.setOtherId();
                            wmImNoticeIListnew.add(wmi);
                        }
                        wmImNoticeHService.addMain(wmImNoticeH, wmImNoticeIListnew);
                    }
                } else {
                    continue;
                }
            }
            }
    }



    public static  void getSdvl(String indate) {
//  /        PO_Pomain  采购订单主表
//        PO_Podetails  采购订单子表
        String dbKey=  ResourceUtil.getConfigByName("yydbkey");
        List<Map<String, Object>> result=null;
        List<Map<String, Object>> resultdetail=null;
        String querySql = "select * from DispatchList  where cvouchtype  = '05' and  ddate = '"+indate+"'";
        Map queryparams =  new LinkedHashMap<String,Object>();
        SystemService systemService =ApplicationContextUtil.getContext().getBean(SystemService.class);
        WmOmNoticeHServiceI wmOmNoticeHService =ApplicationContextUtil.getContext().getBean(WmOmNoticeHServiceI.class);
        if(StringUtils.isNotBlank(dbKey)) {
            result = DynamicDBUtil.findList(dbKey, SqlUtil.jeecgCreatePageSql(dbKey, querySql, queryparams, 1, 1000000));
        }
        if (result!=null&&result.size()>0) {
            for (int i = 0; i < result.size(); i++) {
                try{
                    Thread.sleep(3000);

                }catch (Exception e){

                }
                Map<String, Object> prodbo = result.get(i);
                String imcuscode =  prodbo.get("DLID").toString();
                if (StringUtil.isNotEmpty(imcuscode)) {
                    WmOmNoticeHEntity wmimh = systemService.findUniqueByProperty(WmOmNoticeHEntity.class, "imCusCode", imcuscode);
                    if (wmimh == null) {
                        WmOmNoticeHEntity wmOmNoticeH = new WmOmNoticeHEntity();
                        List<WmOmNoticeIEntity> wmomNoticeIListnew = new ArrayList<WmOmNoticeIEntity>();
                        wmOmNoticeH.setOrderTypeCode("11");
                        wmOmNoticeH.setCusCode(ResourceUtil.getConfigByName("yy.cuscode"));
                        String noticeid = wmUtil.getNextomNoticeId(wmOmNoticeH.getOrderTypeCode());
                        wmOmNoticeH.setOmNoticeId(noticeid);
                        wmOmNoticeH.setImCusCode(imcuscode);
                        try{
                            wmOmNoticeH.setOmBeizhu(prodbo.get("cMemo").toString());

                        }catch (Exception e){

                        }
//                        String querySqldetail = "select * from DispatchLists  where cWhCode = '28' and  DLID = '"+imcuscode+"'";
                                                String querySqldetail = "select * from DispatchLists  where   DLID = '"+imcuscode+"'";

                        if (resultdetail!=null){
                            resultdetail.clear();
                        }
                        resultdetail = DynamicDBUtil.findList(dbKey, SqlUtil.jeecgCreatePageSql(dbKey, querySqldetail, queryparams, 1, 1000000));
                        for (int k = 0; k < resultdetail.size(); k++) {
                            WmOmNoticeIEntity wmi = new WmOmNoticeIEntity();
                            Map<String, Object> proddet = resultdetail.get(k);
                            wmi.setGoodsId(proddet.get("cInvCode").toString());
                            MvGoodsEntity mvgoods = systemService.findUniqueByProperty(
                                    MvGoodsEntity.class, "goodsCode", wmi.getGoodsId());
                            if (mvgoods != null) {
                                wmi.setGoodsName(mvgoods.getGoodsName());
                                wmi.setGoodsUnit(mvgoods.getShlDanWei());
                            }
                            wmi.setGoodsQua(Long.toString(new BigDecimal(proddet.get("iQuantity").toString()).setScale(0, RoundingMode.UP).longValue()));
                            wmomNoticeIListnew.add(wmi);
                        }
                        wmOmNoticeHService.addMain(wmOmNoticeH, wmomNoticeIListnew);
                    }
                } else {
                    continue;
                }
            }
        }
    }



    public static  void getqtck(String indate) {
//  /        PO_Pomain  其他出库单主表
//        PO_Podetails  其他出库单主表子表
        String dbKey=  ResourceUtil.getConfigByName("yydbkey");
        List<Map<String, Object>> result=null;
        List<Map<String, Object>> resultdetail=null;
        String querySql = "select * from RdRecord09  where   ddate = '"+indate+"'";
        Map queryparams =  new LinkedHashMap<String,Object>();
        SystemService systemService =ApplicationContextUtil.getContext().getBean(SystemService.class);
        WmOmNoticeHServiceI wmOmNoticeHService =ApplicationContextUtil.getContext().getBean(WmOmNoticeHServiceI.class);
        if(StringUtils.isNotBlank(dbKey)) {
            result = DynamicDBUtil.findList(dbKey, SqlUtil.jeecgCreatePageSql(dbKey, querySql, queryparams, 1, 1000000));
        }
        if (result!=null&&result.size()>0) {
            for (int i = 0; i < result.size(); i++) {
                try{
                    Thread.sleep(3000);

                }catch (Exception e){

                }
                Map<String, Object> prodbo = result.get(i);
                String imcuscode =  prodbo.get("id").toString();
                String otherid =  prodbo.get("cCode").toString();

                if (StringUtil.isNotEmpty(imcuscode)) {
                    WmOmNoticeHEntity wmimh = systemService.findUniqueByProperty(WmOmNoticeHEntity.class, "imCusCode", otherid);
                    if (wmimh == null) {
                        WmOmNoticeHEntity wmOmNoticeH = new WmOmNoticeHEntity();
                        List<WmOmNoticeIEntity> wmomNoticeIListnew = new ArrayList<WmOmNoticeIEntity>();
                        wmOmNoticeH.setOrderTypeCode("19");
                        wmOmNoticeH.setCusCode(ResourceUtil.getConfigByName("yy.cuscode"));
                        String noticeid = wmUtil.getNextomNoticeId(wmOmNoticeH.getOrderTypeCode());
                        wmOmNoticeH.setOmNoticeId(noticeid);
                        wmOmNoticeH.setImCusCode(otherid);
                        try{
                            wmOmNoticeH.setOmBeizhu(prodbo.get("cMemo").toString());

                        }catch (Exception e){

                        }
//                        String querySqldetail = "select * from DispatchLists  where cWhCode = '28' and  DLID = '"+imcuscode+"'";
                        String querySqldetail = "select * from RdRecords09  where   id = '"+imcuscode+"'";

                        if (resultdetail!=null){
                            resultdetail.clear();
                        }
                        resultdetail = DynamicDBUtil.findList(dbKey, SqlUtil.jeecgCreatePageSql(dbKey, querySqldetail, queryparams, 1, 1000000));
                        for (int k = 0; k < resultdetail.size(); k++) {
                            WmOmNoticeIEntity wmi = new WmOmNoticeIEntity();
                            Map<String, Object> proddet = resultdetail.get(k);
                            wmi.setGoodsId(proddet.get("cInvCode").toString());
                            MvGoodsEntity mvgoods = systemService.findUniqueByProperty(
                                    MvGoodsEntity.class, "goodsCode", wmi.getGoodsId());
                            if (mvgoods != null) {
                                wmi.setGoodsName(mvgoods.getGoodsName());
                                wmi.setGoodsUnit(mvgoods.getShlDanWei());
                            }
                            wmi.setGoodsQua(Long.toString(new BigDecimal(proddet.get("iQuantity").toString()).setScale(0, RoundingMode.UP).longValue()));
                            wmomNoticeIListnew.add(wmi);
                        }
                        wmOmNoticeHService.addMain(wmOmNoticeH, wmomNoticeIListnew);
                    }
                } else {
                    continue;
                }
            }
        }
    }

    public static  void getsdck(String indate) {
//  /        PO_Pomain  其他出库单主表
//        PO_Podetails  其他出库单主表子表
        String dbKey=  ResourceUtil.getConfigByName("yydbkey");
        List<Map<String, Object>> result=null;
        List<Map<String, Object>> resultdetail=null;
        String querySql = "select * from RdRecord32  where   ddate = '"+indate+"'";
        Map queryparams =  new LinkedHashMap<String,Object>();
        SystemService systemService =ApplicationContextUtil.getContext().getBean(SystemService.class);
        WmOmNoticeHServiceI wmOmNoticeHService =ApplicationContextUtil.getContext().getBean(WmOmNoticeHServiceI.class);
        if(StringUtils.isNotBlank(dbKey)) {
            result = DynamicDBUtil.findList(dbKey, SqlUtil.jeecgCreatePageSql(dbKey, querySql, queryparams, 1, 1000000));
        }
        if (result!=null&&result.size()>0) {
            for (int i = 0; i < result.size(); i++) {
                try{
                    Thread.sleep(3000);

                }catch (Exception e){

                }
                Map<String, Object> prodbo = result.get(i);
                String imcuscode =  prodbo.get("id").toString();
                String otherid =  prodbo.get("cCode").toString();

                if (StringUtil.isNotEmpty(imcuscode)) {
                    WmOmNoticeHEntity wmimh = systemService.findUniqueByProperty(WmOmNoticeHEntity.class, "imCusCode", otherid);
                    if (wmimh == null) {
                        WmOmNoticeHEntity wmOmNoticeH = new WmOmNoticeHEntity();
                        List<WmOmNoticeIEntity> wmomNoticeIListnew = new ArrayList<WmOmNoticeIEntity>();
                        wmOmNoticeH.setOrderTypeCode("11");
                        wmOmNoticeH.setCusCode(ResourceUtil.getConfigByName("yy.cuscode"));
                        String noticeid = wmUtil.getNextomNoticeId(wmOmNoticeH.getOrderTypeCode());
                        wmOmNoticeH.setOmNoticeId(noticeid);
                        wmOmNoticeH.setImCusCode(otherid);
                        try{
                            wmOmNoticeH.setOmBeizhu(prodbo.get("cMemo").toString());

                        }catch (Exception e){

                        }
//                        String querySqldetail = "select * from DispatchLists  where cWhCode = '28' and  DLID = '"+imcuscode+"'";
                        String querySqldetail = "select * from RdRecords32  where   id = '"+imcuscode+"'";

                        if (resultdetail!=null){
                            resultdetail.clear();
                        }
                        resultdetail = DynamicDBUtil.findList(dbKey, SqlUtil.jeecgCreatePageSql(dbKey, querySqldetail, queryparams, 1, 1000000));
                        for (int k = 0; k < resultdetail.size(); k++) {
                            WmOmNoticeIEntity wmi = new WmOmNoticeIEntity();
                            Map<String, Object> proddet = resultdetail.get(k);
                            wmi.setGoodsId(proddet.get("cInvCode").toString());
                            MvGoodsEntity mvgoods = systemService.findUniqueByProperty(
                                    MvGoodsEntity.class, "goodsCode", wmi.getGoodsId());
                            if (mvgoods != null) {
                                wmi.setGoodsName(mvgoods.getGoodsName());
                                wmi.setGoodsUnit(mvgoods.getShlDanWei());
                            }
                            wmi.setGoodsQua(Long.toString(new BigDecimal(proddet.get("iQuantity").toString()).setScale(0, RoundingMode.UP).longValue()));
                            wmomNoticeIListnew.add(wmi);
                        }
                        wmOmNoticeHService.addMain(wmOmNoticeH, wmomNoticeIListnew);
                    }
                } else {
                    continue;
                }
            }
        }
    }

    public static  void getclck(String indate) {
//  /        PO_Pomain  其他出库单主表
////        PO_Podetails  其他出库单主表子表
        String dbKey=  ResourceUtil.getConfigByName("yydbkey");
        List<Map<String, Object>> result=null;
        List<Map<String, Object>> resultdetail=null;
        String querySql = "select * from RdRecord11  where   ddate = '"+indate+"'";
        Map queryparams =  new LinkedHashMap<String,Object>();
        SystemService systemService =ApplicationContextUtil.getContext().getBean(SystemService.class);
        WmOmNoticeHServiceI wmOmNoticeHService =ApplicationContextUtil.getContext().getBean(WmOmNoticeHServiceI.class);
        if(StringUtils.isNotBlank(dbKey)) {
            result = DynamicDBUtil.findList(dbKey, SqlUtil.jeecgCreatePageSql(dbKey, querySql, queryparams, 1, 1000000));
        }
        if (result!=null&&result.size()>0) {
            for (int i = 0; i < result.size(); i++) {
                try{
                    Thread.sleep(3000);

                }catch (Exception e){

                }
                Map<String, Object> prodbo = result.get(i);
                String imcuscode =  prodbo.get("id").toString();
                String otherid =  prodbo.get("cCode").toString();

                if (StringUtil.isNotEmpty(imcuscode)) {
                    WmOmNoticeHEntity wmimh = systemService.findUniqueByProperty(WmOmNoticeHEntity.class, "imCusCode", otherid);
                    if (wmimh == null) {
                        WmOmNoticeHEntity wmOmNoticeH = new WmOmNoticeHEntity();
                        List<WmOmNoticeIEntity> wmomNoticeIListnew = new ArrayList<WmOmNoticeIEntity>();
                        wmOmNoticeH.setOrderTypeCode("02");
                        wmOmNoticeH.setCusCode(ResourceUtil.getConfigByName("yy.cuscode"));
                        String noticeid = wmUtil.getNextomNoticeId(wmOmNoticeH.getOrderTypeCode());
                        wmOmNoticeH.setOmNoticeId(noticeid);
                        wmOmNoticeH.setImCusCode(otherid);
                        try{
                            wmOmNoticeH.setOmBeizhu(prodbo.get("cMemo").toString());

                        }catch (Exception e){

                        }
//                        String querySqldetail = "select * from DispatchLists  where cWhCode = '28' and  DLID = '"+imcuscode+"'";
                        String querySqldetail = "select * from RdRecords11  where   id = '"+imcuscode+"'";

                        if (resultdetail!=null){
                            resultdetail.clear();
                        }
                        resultdetail = DynamicDBUtil.findList(dbKey, SqlUtil.jeecgCreatePageSql(dbKey, querySqldetail, queryparams, 1, 1000000));
                        for (int k = 0; k < resultdetail.size(); k++) {
                            WmOmNoticeIEntity wmi = new WmOmNoticeIEntity();
                            Map<String, Object> proddet = resultdetail.get(k);
                            wmi.setGoodsId(proddet.get("cInvCode").toString());
                            MvGoodsEntity mvgoods = systemService.findUniqueByProperty(
                                    MvGoodsEntity.class, "goodsCode", wmi.getGoodsId());
                            if (mvgoods != null) {
                                wmi.setGoodsName(mvgoods.getGoodsName());
                                wmi.setGoodsUnit(mvgoods.getShlDanWei());
                            }
                            wmi.setGoodsQua(Long.toString(new BigDecimal(proddet.get("iQuantity").toString()).setScale(0, RoundingMode.UP).longValue()));
                            wmomNoticeIListnew.add(wmi);
                        }
                        wmOmNoticeHService.addMain(wmOmNoticeH, wmomNoticeIListnew);
                    }
                } else {
                    continue;
                }
            }
        }
    }
    public  static void addOtherOut(Map<String, Object> params){
     String to_account = params.get("to_account").toString();	//提供方id        String page_index = args[1];// 页号

     String jsonBody = params.get("jsonBody").toString();
     OtherOutService otherOutService = new OtherOutService();
     try {
         otherOutService.add(jsonBody, to_account);
     } catch (OpenAPIException e) {
         e.printStackTrace();
     }
 }
    public  static void addOtherIn(Map<String, Object> params){
        String to_account = params.get("to_account").toString();	//提供方id        String page_index = args[1];// 页号
        String jsonBody = params.get("jsonBody").toString();
        OtherInService otherInService = new OtherInService();
        try {
            otherInService.add(jsonBody, to_account);
        } catch (OpenAPIException e) {
            e.printStackTrace();
        }
    }

}
