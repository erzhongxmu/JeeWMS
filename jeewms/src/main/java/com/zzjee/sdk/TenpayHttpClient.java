package com.zzjee.sdk;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class TenpayHttpClient {




    /** 请求内容，无论post和get，都用get方式提供 */
    private String reqContent;

    /** 应答内容 */
    private String resContent;

    /** 请求方法 */
    private String method;

    /** 错误信息 */
    private String errInfo;

    /** 超时时间,以秒为单位 */
    private int timeOut;

    /** http应答编码 */
    private int responseCode;

    /** 字符编码 */
    private String charset;

    private InputStream inputStream;

    public TenpayHttpClient() {
        this.reqContent = "";
        this.resContent = "";
        this.method = "POST";
        this.errInfo = "";
        this.timeOut = 30;//30秒

        this.responseCode = 0;
        this.charset = "utf8";

        this.inputStream = null;
    }

    /**
     * 设置请求内容
     * @param reqContent 表求内容
     */
    public void setReqContent(String reqContent) {
        this.reqContent = reqContent;
    }

    /**
     * 获取结果内容
     * @return String
     * @throws IOException
     */
    public String getResContent() {
        try {
            this.doResponse();
        } catch (IOException e) {
            this.errInfo = e.getMessage();
            //return "";
        }

        return this.resContent;
    }

    /**
     * 设置请求方法post或者get
     * @param method 请求方法post/get
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * 获取错误信息
     * @return String
     */
    public String getErrInfo() {
        return this.errInfo;
    }

    /**
     * 设置超时时间,以秒为单位
     * @param timeOut 超时时间,以秒为单位
     */
    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    /**
     * 获取http状态码
     * @return int
     */
    public int getResponseCode() {
        return this.responseCode;
    }

    protected void callHttp() throws IOException {

        if("POST".equals(this.method.toUpperCase())) {
            String url = HttpClientUtil.getURL(this.reqContent);
            String queryString = HttpClientUtil.getQueryString(this.reqContent);
            byte[] postData = queryString.getBytes(this.charset);
            this.httpPostMethod(url, postData);

            return ;
        }

        this.httpGetMethod(this.reqContent);

    }

    public boolean callHttpPost(String url, String postdata) {
        boolean flag = false;
        byte[] postData;
        try {
            postData = postdata.getBytes(this.charset);
            this.httpPostMethod(url, postData);
            flag = true;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return flag;
    }

    /**
     * 以http post方式通信
     * @param url
     * @param postData
     * @throws IOException
     */
    protected void httpPostMethod(String url, byte[] postData)
            throws IOException {

        HttpURLConnection conn = HttpClientUtil.getHttpURLConnection(url);

        this.doPost(conn, postData);
    }

    /**
     * 以http get方式通信
     *
     * @param url
     * @throws IOException
     */
    protected void httpGetMethod(String url) throws IOException {

        HttpURLConnection httpConnection =
                HttpClientUtil.getHttpURLConnection(url);

        this.setHttpRequest(httpConnection);

        httpConnection.setRequestMethod("GET");

        this.responseCode = httpConnection.getResponseCode();

        this.inputStream = httpConnection.getInputStream();

    }

    /**
     * 以https get方式通信
     * @param url
     * @param sslContext
     * @throws IOException
     */
    protected void httpsGetMethod(String url, SSLContext sslContext)
            throws IOException {

        SSLSocketFactory sf = sslContext.getSocketFactory();

        HttpsURLConnection conn = HttpClientUtil.getHttpsURLConnection(url);

        conn.setSSLSocketFactory(sf);

        this.doGet(conn);

    }

    protected void httpsPostMethod(String url, byte[] postData,
                                   SSLContext sslContext) throws IOException {

        SSLSocketFactory sf = sslContext.getSocketFactory();

        HttpsURLConnection conn = HttpClientUtil.getHttpsURLConnection(url);

        conn.setSSLSocketFactory(sf);

        this.doPost(conn, postData);

    }

    /**
     * 设置http请求默认属性
     * @param httpConnection
     */
    protected void setHttpRequest(HttpURLConnection httpConnection) {

        //设置连接超时时间
        httpConnection.setConnectTimeout(this.timeOut * 1000);


        //不使用缓存
        httpConnection.setUseCaches(false);

        //允许输入输出
        httpConnection.setDoInput(true);
        httpConnection.setDoOutput(true);

    }

    /**
     * 处理应答
     * @throws IOException
     */
    protected void doResponse() throws IOException {

        if(null == this.inputStream) {
            return;
        }

        //获取应答内容
        this.resContent=HttpClientUtil.InputStreamTOString(this.inputStream,this.charset);

        //关闭输入流
        this.inputStream.close();

    }

    /**
     * post方式处理
     * @param conn
     * @param postData
     * @throws IOException
     */
    protected void doPost(HttpURLConnection conn, byte[] postData)
            throws IOException {

        // 以post方式通信
        conn.setRequestMethod("POST");

        // 设置请求默认属性
        this.setHttpRequest(conn);

        // Content-Type
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");

        BufferedOutputStream out = new BufferedOutputStream(conn
                .getOutputStream());

        final int len = 1024; // 1KB
        HttpClientUtil.doOutput(out, postData, len);

        // 关闭流
        out.close();

        // 获取响应返回状态码
        this.responseCode = conn.getResponseCode();

        // 获取应答输入流
        this.inputStream = conn.getInputStream();

    }

    /**
     * get方式处理
     * @param conn
     * @throws IOException
     */
    protected void doGet(HttpURLConnection conn) throws IOException {

        //以GET方式通信
        conn.setRequestMethod("GET");

        //设置请求默认属性
        this.setHttpRequest(conn);

        //获取响应返回状态码
        this.responseCode = conn.getResponseCode();

        //获取应答输入流
        this.inputStream = conn.getInputStream();
    }


}