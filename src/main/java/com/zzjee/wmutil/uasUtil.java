package com.zzjee.wmutil;

import com.xiaoleilu.hutool.http.HttpUtil;
import net.sf.json.JSONObject;
import netscape.javascript.JSObject;
import org.apache.commons.chain.web.servlet.RequestParameterMapper;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.json.HTTP;
import org.springframework.util.DigestUtils;

import java.nio.charset.Charset;
import java.util.*;

public class uasUtil {
    /**
     * User: caoez
     * Date: 18-09-01
     * Time: 下午2:07
     */
    public static String createSign(Map<String, Object> params) {
        Set<String> keysSet = params.keySet();
        Object[] keys = keysSet.toArray();
        Arrays.sort(keys);
        StringBuffer temp = new StringBuffer();
        boolean first = true;
        for (Object key : keys) {
            if (first) {
                first = false;
            } else {
                temp.append("&");
            }
            temp.append(key).append("=");
            Object value = params.get(key);
            String valueString = "";
            if (null != value) {
                valueString = String.valueOf(value);
                temp.append(valueString);
            }
        }
        return DigestUtils.md5DigestAsHex(temp.toString().getBytes(Charset.forName("UTF-8"))).toUpperCase();
    }



    //
    public static uasloginres getToken(String master) {
        uasloginres uasloginres = null;
        String url;
        url = "http://36.110.2.114:8099/XKN/wms2uas/login.action";
        try{
            url = ResourceUtil.getConfigByName("uas.url")+"wms2uas/login.action";
        }catch (Exception e){

        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String userName= "xknTest";
        String passWord  = "xknTest123";
         master = "XKN_TEST";
        try{
            userName = ResourceUtil.getConfigByName("uas.userName");
        }catch (Exception e){
        }
        try{
             passWord = ResourceUtil.getConfigByName("uas.passWord");
        }catch (Exception e){
        }
        try{
            if(StringUtil.isEmpty(master)){
                master = ResourceUtil.getConfigByName("uas.master");
            }
        }catch (Exception e){
        }
        paramMap.put("userName",userName);
        paramMap.put("passWord",passWord);
        paramMap.put("master",master);
        String res = HttpUtil.post(url,paramMap);
        uasloginres = JSONHelper.fromJsonToObject(res,uasloginres.class);
        org.jeecgframework.core.util.LogUtil
                .info("===================获取TOKEN==================="+uasloginres);
        return  uasloginres;
    }
    public static productResult getProduct(Map<String, Object> params) {
        productResult productResult = null;
        String token=null;
        String url = ResourceUtil.getConfigByName("uas.url")+"basic/getProduct.action";
        String master = ResourceUtil.getConfigByName("uas.master");
        token = getToken("").getToken();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String formDate = DateUtils.date2Str(DateUtils.date_sdf);
        try{
            formDate = params.get("formDate").toString();
        }catch (Exception e){

        }
        paramMap.put("formDate",formDate);
        paramMap.put("token",token);
        paramMap.put("master",master);
        paramMap.put("timestamp",DateUtils.gettimestamp());
        String sign = createSign(paramMap);
        paramMap.put("sign",sign);
        String res = HttpUtil.post(url,paramMap);
        productResult = JSONHelper.fromJsonToObject(res,productResult.class);
        org.jeecgframework.core.util.LogUtil
                .info("===================获取商品==================="+productResult);
       return  productResult;
    }
    public static customerResult getCustomer(Map<String, Object> params) {
        customerResult customerResult = null;
        String token=null;
        String url = ResourceUtil.getConfigByName("uas.url")+"basic/getCustomer.action";
        String master = ResourceUtil.getConfigByName("uas.master");
        token = getToken("").getToken();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String formDate = DateUtils.date2Str(DateUtils.date_sdf);
        try{
            formDate = params.get("formDate").toString();
        }catch (Exception e){

        }
        paramMap.put("formDate",formDate);
        paramMap.put("token",token);
        paramMap.put("master",master);
        paramMap.put("timestamp",DateUtils.gettimestamp());
        String sign = createSign(paramMap);
        paramMap.put("sign",sign);
        String res = HttpUtil.post(url,paramMap);

        customerResult = JSONHelper.fromJsonToObject(res,customerResult.class);
        org.jeecgframework.core.util.LogUtil
                .info("===================获取客户==================="+customerResult);
        return  customerResult;
    }
    public static vendorResult getVendor(Map<String, Object> params) {
        vendorResult vendorResult = null;
        String token=null;
        String url = ResourceUtil.getConfigByName("uas.url")+"basic/getVendor.action";
        String master = ResourceUtil.getConfigByName("uas.master");
        token = getToken("").getToken();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String formDate = DateUtils.date2Str(DateUtils.date_sdf);
        try{
            formDate = params.get("formDate").toString();
        }catch (Exception e){

        }
        paramMap.put("formDate",formDate);
        paramMap.put("token",token);
        paramMap.put("master",master);
        paramMap.put("timestamp",DateUtils.gettimestamp());
        String sign = createSign(paramMap);
        paramMap.put("sign",sign);
        String res = HttpUtil.post(url,paramMap);

        vendorResult = JSONHelper.fromJsonToObject(res,vendorResult.class);
        org.jeecgframework.core.util.LogUtil
                .info("===================获取供应商==================="+vendorResult);
        return  vendorResult;
    }
    public static warehouseResult getWarehouse(Map<String, Object> params) {
        warehouseResult warehouseResult = null;
        String token=null;
        String url = ResourceUtil.getConfigByName("uas.url")+"basic/getWarehouse.action";
        String master = ResourceUtil.getConfigByName("uas.master");
        token = getToken("").getToken();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String formDate = DateUtils.date2Str(DateUtils.date_sdf);
        try{
            formDate = params.get("formDate").toString();
        }catch (Exception e){

        }
        paramMap.put("formDate",formDate);
        paramMap.put("token",token);
        paramMap.put("master",master);
        paramMap.put("timestamp",DateUtils.gettimestamp());
        String sign = createSign(paramMap);
        paramMap.put("sign",sign);
        String res = HttpUtil.post(url,paramMap);

        warehouseResult = JSONHelper.fromJsonToObject(res,warehouseResult.class);
        org.jeecgframework.core.util.LogUtil
                .info("===================获取仓库==================="+warehouseResult);
        return  warehouseResult;
    }

    public static billResult getBil(Map<String, Object> params) {
        billResult billResult = null;
        String token=null;
        String url = ResourceUtil.getConfigByName("uas.url")+"productio/getBill.action";
        String master = params.get("master").toString();
        token = getToken(master).getToken();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String formDate = DateUtils.date2Str(DateUtils.date_sdf);
        try{
            formDate = params.get("lastUpdateTime").toString();
        }catch (Exception e){

        }
        paramMap.put("pi_class",params.get("pi_class").toString());
        paramMap.put("lastUpdateTime",formDate);
        paramMap.put("token",token);
        paramMap.put("master",master);
        paramMap.put("timestamp",Calendar.getInstance().getTimeInMillis());
        String sign = createSign(paramMap);
        paramMap.put("sign",sign);
        String res = HttpUtil.post(url,paramMap);

        billResult = JSONHelper.fromJsonToObject(res,billResult.class);
        org.jeecgframework.core.util.LogUtil
                .info("===================获取单据==================="+billResult);
        return  billResult;
    }

    public static sdresult getsdBil(Map<String, Object> params) {
        sdresult sdresult = null;
        String token=null;
        String url = ResourceUtil.getConfigByName("uas.url")+"productio/getBill.action";
        String master = params.get("master").toString();
        token = getToken(master).getToken();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String formDate = DateUtils.date2Str(DateUtils.date_sdf);
        try{
            formDate = params.get("lastUpdateTime").toString();
        }catch (Exception e){

        }
        paramMap.put("pi_class",params.get("pi_class").toString());
        paramMap.put("lastUpdateTime",formDate);
        paramMap.put("token",token);
        paramMap.put("master",master);
        paramMap.put("timestamp",Calendar.getInstance().getTimeInMillis());
        String sign = createSign(paramMap);
        paramMap.put("sign",sign);
        String res = HttpUtil.post(url,paramMap);

        sdresult = JSONHelper.fromJsonToObject(res,sdresult.class);
        org.jeecgframework.core.util.LogUtil
                .info("===================获取单据==================="+sdresult);
        return  sdresult;
    }

    public static  resResult postBill(Map<String, Object> params) {
        resResult resResult = null;
        String token=null;
        String url = ResourceUtil.getConfigByName("uas.url")+"porductio/post.action";
        String master = params.get("master").toString();
        token = getToken(master).getToken();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("data", params.get("data").toString());
        paramMap.put("token",token);
        paramMap.put("master",master);
        paramMap.put("timestamp",Calendar.getInstance().getTimeInMillis());
        String sign = createSign(paramMap);
        paramMap.put("sign",sign);
        String res = HttpUtil.post(url,paramMap);

        resResult = JSONHelper.fromJsonToObject(res,resResult.class);
        org.jeecgframework.core.util.LogUtil
                .info("===================提交单据==================="+resResult);
        return  resResult;
    }

}
