package com.zzjee.sdk;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 应答处理类
 * 应答处理类继承此类，重写isTenpaySign方法即可。
 *
 */
public class ResponseHandler {

    /** 密钥 */
    private String key;

    /** 应答的参数 */
    private SortedMap parameters;

    private HttpServletRequest request;

    private HttpServletResponse response;

    private String uriEncoding;

    /**
     * 构造函数
     *
     * @param request
     * @param response
     */
    public ResponseHandler(HttpServletRequest request,
                           HttpServletResponse response)  {
        this.request = request;
        this.response = response;

        this.key = "";
        this.parameters = new TreeMap();
        this.uriEncoding = "";

        Map m = this.request.getParameterMap();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String k = (String) it.next();
            String v = ((String[]) m.get(k))[0];
            this.setParameter(k, v);
        }

    }

    /**
     *获取密钥
     */
    public String getKey() {
        return key;
    }

    /**
     *设置密钥
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取参数值
     * @param parameter 参数名称
     * @return String
     */
    public String getParameter(String parameter) {
        String s = (String)this.parameters.get(parameter);
        return (null == s) ? "" : s;
    }

    /**
     * 设置参数值
     * @param parameter 参数名称
     * @param parameterValue 参数值
     */
    public void setParameter(String parameter, String parameterValue) {
        String v = "";
        if(null != parameterValue) {
            v = parameterValue.trim();
        }
        this.parameters.put(parameter, v);
    }

    /**
     * 返回所有的参数
     * @return SortedMap
     */
    public SortedMap getAllParameters() {
        return this.parameters;
    }

    /**
     * 是否财付通签名,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     * @return boolean
     */
    public boolean isTenpaySign() {
        StringBuffer sb = new StringBuffer();
        Set es = this.parameters.entrySet();

        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if(!"sign".equals(k) && null != v && !"".equals(v)) {
                sb.append(k + "=" + v + "&");
            }
        }

        sb.append("key=" + this.getKey());

        //算出摘要
        String enc = TenpayUtil.getCharacterEncoding(this.request, this.response);
        String sign = MD5Util.MD5Encode(sb.toString(), enc).toLowerCase();

        String tenpaySign = this.getParameter("sign").toLowerCase();

        return tenpaySign.equals(sign);
    }

    /**
     * 返回处理结果给财付通服务器。
     * @param msg: Success or fail。
     * @throws IOException
     */
    public void sendToCFT(String msg) throws IOException {
        String strHtml = msg;
        PrintWriter out = this.getHttpServletResponse().getWriter();
        out.println(strHtml);
        out.flush();
        out.close();

    }

    /**
     * 获取uri编码
     * @return String
     */
    public String getUriEncoding() {
        return uriEncoding;
    }

    /**
     * 设置uri编码
     * @param uriEncoding
     * @throws UnsupportedEncodingException
     */
    public void setUriEncoding(String uriEncoding)
            throws UnsupportedEncodingException {
        if (!"".equals(uriEncoding.trim())) {
            this.uriEncoding = uriEncoding;

            // 编码转换
            String enc = TenpayUtil.getCharacterEncoding(request, response);
            Iterator it = this.parameters.keySet().iterator();
            while (it.hasNext()) {
                String k = (String) it.next();
                String v = this.getParameter(k);
                v = new String(v.getBytes(uriEncoding.trim()), enc);
                this.setParameter(k, v);
            }
        }
    }

    protected HttpServletRequest getHttpServletRequest() {
        return this.request;
    }

    protected HttpServletResponse getHttpServletResponse() {
        return this.response;
    }

}
