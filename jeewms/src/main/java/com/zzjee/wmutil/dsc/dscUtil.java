package com.zzjee.wmutil.dsc;

import com.xiaoleilu.hutool.http.HttpUtil;
import com.zzjee.md.entity.MdCusOtherEntity;
import com.zzjee.md.entity.MdGoodsEntity;
import com.zzjee.md.entity.MvGoodsEntity;
import com.zzjee.wm.entity.WmOmNoticeHEntity;
import com.zzjee.wm.entity.WmOmNoticeIEntity;
import com.zzjee.wm.service.WmOmNoticeHServiceI;
import com.zzjee.wmutil.uasloginres;
import com.zzjee.wmutil.wmUtil;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.system.service.SystemService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class dscUtil {


//    dsc.user.list.get



//    page	Number	可选	1	默认	列表分页当前页
//    page_size	Number	可选	15	默认	列表分页每页显示条数
//    format	type	必须	json	json或xml	接口类型
//    dsc.goods.list.get



    public static void updateGoodsFromDsc(){
        String res = getGoods("1");
        System.out.println(res);
        int pagecount = 1;
        goodsListRes reslist =   JSONHelper.fromJsonToObject(res,goodsListRes.class);
        savegoodstowm(reslist);
        pagecount = reslist.getInfo().getPageCount();
        for(int i =2;i<=pagecount;i++){
            String page = Integer.toString(i);
            res = getGoods(page);
            reslist =   JSONHelper.fromJsonToObject(res,goodsListRes.class);
            savegoodstowm(reslist);
        }
    }


    public static void updateCusFromDsc(){
        String res = getCustomer("1");
        System.out.println(res);
        int pagecount = 1;
        customerListRes reslist =   JSONHelper.fromJsonToObject(res,customerListRes.class);
        saveothercustowm(reslist);
        pagecount = reslist.getInfo().getPageCount();
        for(int i =2;i<=pagecount;i++){
            String page = Integer.toString(i);
            res = getGoods(page);
            reslist =   JSONHelper.fromJsonToObject(res,customerListRes.class);
            saveothercustowm(reslist);
        }
    }

    public static void updateorderFromDsc(){
        String res = getOrderList("1","","");
        System.out.println(res);
        int pagecount = 1;
        orderListRes reslist =   JSONHelper.fromJsonToObject(res,orderListRes.class);
        saveordertowm(reslist);
        pagecount = reslist.getInfo().getPageCount();
        for(int i =2;i<=pagecount;i++){
            String page = Integer.toString(i);
            res = getOrderList(page,"","");
            reslist =   JSONHelper.fromJsonToObject(res,orderListRes.class);
            saveordertowm(reslist);
        }
    }


    public static String getGoods(String page){
        String baseurl = ResourceUtil.getConfigByName("dsc.url");
        Map<String, Object> paramMap = getbasepara();
        paramMap.put("method","dsc.goods.list.get");
        paramMap.put("page",page);
        String res = HttpUtil.get(baseurl,paramMap);
        return   res;
    }

    public static String getCustomer(String page){
        String baseurl = ResourceUtil.getConfigByName("dsc.url");
        Map<String, Object> paramMap = getbasepara();
        paramMap.put("method","dsc.user.list.get");
        paramMap.put("page",page);
        String res = HttpUtil.get(baseurl,paramMap);
        return   res;
    }

    public static String getOrderList(String page,String startAddTime,String endAddTime){
        String baseurl = ResourceUtil.getConfigByName("dsc.url");
        Map<String, Object> paramMap = getbasepara();
        paramMap.put("method","dsc.order.list.get");
        paramMap.put("page",page);
        if(StringUtil.isNotEmpty(startAddTime)){
            paramMap.put("start_add_time",startAddTime);

        }
        if(StringUtil.isNotEmpty(endAddTime)){
            paramMap.put("end_add_time",endAddTime);

        }
//        paramMap.put("order_status","1");//只取已确认的订单
        String res = HttpUtil.get(baseurl,paramMap);
        return   res;
    }

    public static String getOrder(String orderSn){
        String baseurl = ResourceUtil.getConfigByName("dsc.url");
        Map<String, Object> paramMap = getbasepara();
        paramMap.put("method","dsc.order.info.get");
        paramMap.put("order_sn",orderSn);
        String res = HttpUtil.get(baseurl,paramMap);
        return   res;
    }
    public static String getOrderGoods(String orderId){
        String baseurl = ResourceUtil.getConfigByName("dsc.url");
        Map<String, Object> paramMap = getbasepara();
        paramMap.put("method","dsc.order.goods.list.get");
        paramMap.put("order_id",orderId);
        String res = HttpUtil.get(baseurl,paramMap);
        return   res;
    }

    public static void savegoodstowm(goodsListRes reslist){
        String cusCode = ResourceUtil.getConfigByName("dsc.cuscode");
        SystemService systemService =ApplicationContextUtil.getContext().getBean(SystemService.class);
        for( goodsListRes.InfoBean.ListBean   t:reslist.getInfo().getList()){
            try{
                MdGoodsEntity mdgoos = systemService.findUniqueByProperty(MdGoodsEntity.class,"shpBianMakh",t.getGoodsSn());
                if(mdgoos==null){
                    mdgoos = new MdGoodsEntity();
                }
                mdgoos.setSuoShuKeHu(cusCode);
                mdgoos.setShpBianMakh(t.getGoodsSn());
                mdgoos.setShpBianMa(t.getGoodsSn());
                mdgoos.setShpMingCheng(unicodeDecode(t.getGoodsName()));
                mdgoos.setShpTiaoMa(t.getBarCode());
                mdgoos.setChlKongZhi("N");
                mdgoos.setChlShl("1");
                mdgoos.setShlDanWei("个");
                mdgoos.setJshDanWei("个");
                mdgoos.setBzhiQi("360");
                mdgoos.setChpShuXing(t.getCatId());
                mdgoos.setCfWenCeng("常温");
                systemService.saveOrUpdate(mdgoos);
            }catch (Exception e){
            }
        }
    }


    public static void saveothercustowm(customerListRes reslist){
        String cusCode = ResourceUtil.getConfigByName("dsc.cuscode");
        SystemService systemService =ApplicationContextUtil.getContext().getBean(SystemService.class);
        for( customerListRes.InfoBean.ListBean t:reslist.getInfo().getList()){
            try{
                MdCusOtherEntity md = systemService.findUniqueByProperty(MdCusOtherEntity.class,"keHuBianMa",t.getUserId());
                if(md==null){
                    md = new MdCusOtherEntity();
                }
                md.setSuoShuKeHu(cusCode);
                md.setKeHuBianMa(t.getUserId());
                md.setZhongWenQch(unicodeDecode(t.getUserName()+t.getNickName()));
                md.setDianHua(t.getMobilePhone());
                systemService.saveOrUpdate(md);
            }catch (Exception e){
            }
        }
    }

    public static void saveordertowm(orderListRes reslist){
        String cusCode = ResourceUtil.getConfigByName("dsc.cuscode");
        SystemService systemService =ApplicationContextUtil.getContext().getBean(SystemService.class);
        for(  orderListRes.InfoBean.ListBean t:reslist.getInfo().getList()){
            try{
                String orderSn = t.getOrderSn();
                String orderRes = getOrder(orderSn);
                orderRes order =   JSONHelper.fromJsonToObject(orderRes,orderRes.class);
                saveOneOrder(order);
            }catch (Exception e){

            }
        }
    }
    public static void saveOneOrder(orderRes orderhead) {
        String  cusCode = ResourceUtil.getConfigByName("dsc.cuscode");
        String  imcuscode =  orderhead.getInfo().getOrderSn();
        String   order_id =orderhead.getInfo().getOrderId();
        SystemService systemService =ApplicationContextUtil.getContext().getBean(SystemService.class);
        WmOmNoticeHServiceI wmOmNoticeHService =ApplicationContextUtil.getContext().getBean(WmOmNoticeHServiceI.class);
        WmOmNoticeHEntity wmimh = systemService.findUniqueByProperty(WmOmNoticeHEntity.class, "imCusCode", imcuscode);
        if (wmimh == null) {
            WmOmNoticeHEntity wmOmNoticeH = new WmOmNoticeHEntity();
            List<WmOmNoticeIEntity> wmomNoticeIListnew = new ArrayList<WmOmNoticeIEntity>();
             wmOmNoticeH.setOrderTypeCode("11");
            wmOmNoticeH.setCusCode(cusCode);
            String noticeid = wmUtil.getNextomNoticeId(wmOmNoticeH.getOrderTypeCode());
            wmOmNoticeH.setOmNoticeId(noticeid);
            wmOmNoticeH.setOcusCode(orderhead.getInfo().getUserId());
            MdCusOtherEntity mdcusother = systemService.findUniqueByProperty(MdCusOtherEntity.class, "keHuBianMa", wmOmNoticeH.getOcusCode());
            if (mdcusother != null) {
                wmOmNoticeH.setOcusName(mdcusother.getZhongWenQch());
            }
            wmOmNoticeH.setImCusCode(imcuscode);
            wmOmNoticeH.setOmBeizhu(orderhead.getInfo().getHowOos());
            wmOmNoticeH.setDelvMember(orderhead.getInfo().getConsignee());
            wmOmNoticeH.setDelvMobile(orderhead.getInfo().getMobile());
            wmOmNoticeH.setDelvAddr(orderhead.getInfo().getAddress());
            String orderGoodsRes = getOrderGoods(order_id);
            orderGoodsRes orderGoods =   JSONHelper.fromJsonToObject(orderGoodsRes,orderGoodsRes.class);
            for(com.zzjee.wmutil.dsc.orderGoodsRes.InfoBean.ListBean t:orderGoods.getInfo().getList()){
                WmOmNoticeIEntity wmi = new WmOmNoticeIEntity();
                wmi.setGoodsId(t.getGoodsId());
                MvGoodsEntity mvgoods = systemService.findUniqueByProperty(MvGoodsEntity.class, "goodsCode", wmi.getGoodsId());
                if (mvgoods != null) {
                    wmi.setGoodsName(mvgoods.getGoodsName());
                    wmi.setGoodsUnit(mvgoods.getShlDanWei());
                }
//                wmi.setGoodsProData(DateUtils.str2Date(billResult.getData().get(s).getDetail().get(k).getPdProdmadedate(), DateUtils.date_sdf));
                wmi.setGoodsQua(t.getGoodsNumber());
//                               wmi.setGoodsPrdData(billResult.getData().get(s).getDetail().get(k).getPdProdmadedate2User());
                wmi.setOtherId(t.getRecId());

                wmomNoticeIListnew.add(wmi);



            }

//            for (int k = 0; k <order.getInfo().; k++) {
//                WmOmNoticeIEntity wmi = new WmOmNoticeIEntity();
//                wmi.setGoodsId(billResult.getData().get(s).getDetail().get(k).getPdProdcode());
//                MvGoodsEntity mvgoods = systemService.findUniqueByProperty(MvGoodsEntity.class, "goodsCode", wmi.getGoodsId());
//                if (mvgoods != null) {
//                    wmi.setGoodsName(mvgoods.getGoodsName());
//                    wmi.setGoodsUnit(mvgoods.getShlDanWei());
//                }
////                wmi.setGoodsProData(DateUtils.str2Date(billResult.getData().get(s).getDetail().get(k).getPdProdmadedate(), DateUtils.date_sdf));
//                wmi.setGoodsQua(Integer.toString(billResult.getData().get(s).getDetail().get(k).getPdPurcoutqty()));
////                               wmi.setGoodsPrdData(billResult.getData().get(s).getDetail().get(k).getPdProdmadedate2User());
//                wmi.setOtherId(Integer.toString(billResult.getData().get(s).getDetail().get(k).getPdPdno()));
//
//                wmomNoticeIListnew.add(wmi);
//            }
            wmOmNoticeHService.addMain(wmOmNoticeH, wmomNoticeIListnew);
        }






    }

    public  static Map<String, Object> getbasepara(){
        Map<String, Object> paramMap = new HashMap<>();
        String baseukey = ResourceUtil.getConfigByName("dsc.key");
        paramMap.put("app_key",baseukey);
        paramMap.put("format","json");
        return paramMap;
    }

    /**
     * @Title: unicodeDecode
     * @Description: unicode解码
     * @param str
     * @return
     */
    public static String unicodeDecode(String string) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(string);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            string = string.replace(matcher.group(1), ch + "");
        }
        return string;
    }
}
