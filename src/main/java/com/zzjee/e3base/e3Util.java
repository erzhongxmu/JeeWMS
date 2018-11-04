package com.zzjee.e3base;

import com.xiaoleilu.hutool.http.HttpUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.util.DigestUtils;

import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class e3Util {
    public static String getdate(Map<String, Object> params) {
        Set<String> keysSet = params.keySet();
        Object[] keys = keysSet.toArray();
//        Arrays.sort(keys);
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
        return temp.toString();
    }

    public static String createSign(LinkedHashMap<String, Object> params) {
//        Set<String> keysSet = params.keySet();
//        Object[] keys = keysSet.toArray();

        Iterator it = params.entrySet().iterator();
   //        Arrays.sort(keys);
        StringBuffer temp = new StringBuffer();
        boolean first = true;
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (first) {
                first = false;
            } else {
                temp.append("&");
            }
            temp.append(entry.getKey()).append("=");
            Object value = entry.getValue();
            String valueString = "";
            if (null != value) {
                valueString = String.valueOf(value);
                temp.append(valueString);
            }
        }
        System.out.print("1111111111111111111111111"+temp.toString());
        return DigestUtils.md5DigestAsHex(temp.toString().getBytes(Charset.forName("UTF-8")));
    }
    public static e3ShipOrder getBil(Map<String, Object> params) {
        e3ShipOrder e3ShipOrder =null;
        String url = ResourceUtil.getConfigByName("E3.url");
        String e3Key = ResourceUtil.getConfigByName("E3.key");
        String e3Secret = ResourceUtil.getConfigByName("E3.secret");
        Map<String, String> paramMapdata = new HashMap<String, String>();
        paramMapdata.put("startModified","2011-09-01 00:00:00");
        paramMapdata.put("endModified","2029-09-01 00:00:00");
        String shipping_sn = null;
        try{
            shipping_sn = params.get("shipping_sn").toString();
        }catch (Exception e){

        }
        if(StringUtil.isNotEmpty(shipping_sn)){
            paramMapdata.put("shipping_sn",shipping_sn);
        }
        String data=JSONHelper.map2json(paramMapdata);
        LinkedHashMap<String, Object> paramMap = new LinkedHashMap<String, Object>();
        paramMap.put("key",e3Key);
        paramMap.put("requestTime",DateUtils.getDataString(DateUtils.yyyymmddhhmmss));
        paramMap.put("secret",e3Secret);
        paramMap.put("version","0.1");
        paramMap.put("serviceType","shipping_get_order");
        paramMap.put("data",data);
        String sign = createSign(paramMap);
        paramMap.put("format","json");
        paramMap.put("sign",sign);
        paramMap.put("app_act","api/ec");
        paramMap.put("app_mode","func");
//        url = url+getdate(paramMap);
        System.out.print("url********************==="+url);
        try{
            String res = HttpUtil.post(url,paramMap);
            org.jeecgframework.core.util.LogUtil
                    .info("===================获取E3单据==================="+res);
            e3ShipOrder = JSONHelper.fromJsonToObject(res,e3ShipOrder.class);
        }catch (Exception e){
        }
        return  e3ShipOrder;
    }

    public static e3Response postBil(Map<String, Object> params) {
        e3Response e3Response =null;
        String url = ResourceUtil.getConfigByName("E3.url");
        String e3Key = ResourceUtil.getConfigByName("E3.key");
        String e3Secret = ResourceUtil.getConfigByName("E3.secret");
        Map<String, String> paramMapdata = new HashMap<String, String>();
        String order_sn = null;
        try{
            order_sn = params.get("order_sn").toString();
        }catch (Exception e){

        }
        if(StringUtil.isNotEmpty(order_sn)){
            paramMapdata.put("order_sn",order_sn);
        }
        String data=JSONHelper.map2json(paramMapdata);
        LinkedHashMap<String, Object> paramMap = new LinkedHashMap<String, Object>();
        paramMap.put("key",e3Key);
        paramMap.put("requestTime",DateUtils.getDataString(DateUtils.yyyymmddhhmmss));
        paramMap.put("secret",e3Secret);
        paramMap.put("version","0.1");
        paramMap.put("serviceType","pda_delivery_return");
        paramMap.put("data",data);
        String sign = createSign(paramMap);
        paramMap.put("format","json");
        paramMap.put("sign",sign);
        paramMap.put("app_act","api/ec");
        paramMap.put("app_mode","func");
//        url = url+getdate(paramMap);
        System.out.print("url********************==="+url);
        try{
            String res = HttpUtil.post(url,paramMap);
            org.jeecgframework.core.util.LogUtil
                    .info("===================POST E3单据==================="+res);
            e3Response = JSONHelper.fromJsonToObject(res,e3Response.class);
        }catch (Exception e){
        }
        return  e3Response;
    }

    public static e3Response postBarcode(e3PostBarcode e3PostBarcode) {
        e3Response e3Response =null;
        String url = ResourceUtil.getConfigByName("E3.url");
        String e3Key = ResourceUtil.getConfigByName("E3.key");
        String e3Secret = ResourceUtil.getConfigByName("E3.secret");
        Map<String, String> paramMapdata = new HashMap<String, String>();
        String databarcode = null;
        try{
            databarcode = JSONHelper.bean2json(e3PostBarcode).toString();
        }catch (Exception e){

        }
        JSONHelper.map2json(paramMapdata);
        LinkedHashMap<String, Object> paramMap = new LinkedHashMap<String, Object>();
        paramMap.put("key",e3Key);
        paramMap.put("requestTime",DateUtils.getDataString(DateUtils.yyyymmddhhmmss));
        paramMap.put("secret",e3Secret);
        paramMap.put("version","0.1");
        paramMap.put("serviceType","write_unique_code");
        paramMap.put("data",databarcode);
        String sign = createSign(paramMap);
        paramMap.put("format","json");
        paramMap.put("sign",sign);
        paramMap.put("app_act","api/ec");
        paramMap.put("app_mode","func");
//        url = url+getdate(paramMap);
        System.out.print("url********************==="+url);
        try{
            String res = HttpUtil.post(url,paramMap);
            org.jeecgframework.core.util.LogUtil
                    .info("===================POST E3Barcode==================="+res);
            e3Response = JSONHelper.fromJsonToObject(res,e3Response.class);
        }catch (Exception e){
        }
        return  e3Response;
    }

    /**
     * Unicode转 汉字字符串
     *
     * @param str \u6728
     * @return '木' 26408
     */
    public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            //group 6728
            String group = matcher.group(2);
            //ch:'木' 26408
            ch = (char) Integer.parseInt(group, 16);
            //group1 \u6728
            String group1 = matcher.group(1);
            str = str.replace(group1, ch + "");
        }
        return str;
    }


}
