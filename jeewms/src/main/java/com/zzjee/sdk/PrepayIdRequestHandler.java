package com.zzjee.sdk;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PrepayIdRequestHandler extends RequestHandler {

    public PrepayIdRequestHandler(HttpServletRequest request,
                                  HttpServletResponse response) {
        super(request, response);
    }

    public String createMD5Sign() {
        StringBuffer sb = new StringBuffer();
        Set es = super.getAllParameters().entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            sb.append(k + "=" + v + "&");
        }
        String params=sb.append("key="+ConstantUtil.APP_KEY).substring(0);
        String sign = MD5Util.MD5Encode(params, "utf8");
        return sign.toUpperCase();
    }

    // 提交预支付
    public String sendPrepay() throws Exception {
        String prepayid = "";
        Set es=super.getAllParameters().entrySet();
        Iterator it=es.iterator();
        StringBuffer sb = new StringBuffer("<xml>");
        while(it.hasNext()){
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            sb.append("<"+k+">"+v+"</"+k+">");
        }
        sb.append("</xml>");
        String params=sb.substring(0);
        System.out.println("请求参数："+params);
        String requestUrl = super.getGateUrl();
        System.out.println("请求url："+requestUrl);
        TenpayHttpClient httpClient = new TenpayHttpClient();
        httpClient.setReqContent(requestUrl);
        String resContent = "";
        if (httpClient.callHttpPost(requestUrl, params)) {
            resContent = httpClient.getResContent();
            System.out.println("获取prepayid的返回值："+resContent);
            Map<String,String> map=XMLUtil.doXMLParse(resContent);
            if(map.containsKey("prepay_id")) {
                prepayid=map.get("prepay_id");
            }
        }
        return prepayid;
    }
}
