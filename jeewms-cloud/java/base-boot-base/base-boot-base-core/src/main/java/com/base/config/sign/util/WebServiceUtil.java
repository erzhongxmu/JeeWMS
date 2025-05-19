package com.base.config.sign.util;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Slf4j
public class WebServiceUtil {

	private static RequestConfig requestConfig;

	public static JSONObject httpPost(String url, String params) {
		String charset = "UTF-8";
		String result = null;
		CloseableHttpClient httpClient = null;
		try {
			SSLConnectionSocketFactory scfg = new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build(), NoopHostnameVerifier.INSTANCE);
			httpClient = HttpClients.custom().setSSLSocketFactory(scfg).setConnectionManagerShared(true).build();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

			BasicHttpEntity requestBody = new BasicHttpEntity();
			String json = "Json=" + params;
			requestBody.setContent(new ByteArrayInputStream(json.getBytes(charset)));
			requestBody.setContentLength(json.getBytes(charset).length);
			httpPost.setEntity(requestBody);
			System.out.println("执行客户端请求>>>");
			// 执行客户端请求
			trustAllHosts();
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
//			System.out.println("执行客户端请求>>> entity= "+EntityUtils.toString(entity));
			if (entity != null) {
				System.out.println("执行客户端请求>>> line 142 ");
				result = EntityUtils.toString(entity, charset);
				System.out.println("执行客户端请求>>> result= "+ result);
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			// 关闭连接
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		if(result.indexOf("{")>-1){
			result = result.substring(result.indexOf("{"),result.indexOf("}")+1);
		}
		return JSONObject.parseObject(result);
	}

	public static JSONObject httpGet(String url, String json) throws Exception {
		JSONObject result = null;
		// 创建Httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 定义请求的参数
		URI uri = new URIBuilder(url).setParameter("Json", json).build();
		URIBuilder aa = new URIBuilder(url).setParameter("Json", json).setParameter("Json", json);
		// 创建http GET请求
		HttpGet httpGet = new HttpGet(uri);
		//response 对象
		CloseableHttpResponse response = null;
		try {
			// 执行http get请求
			response = httpclient.execute(httpGet);
			String content = EntityUtils.toString(response.getEntity(), "UTF-8");
			System.out.println("U8返回的内容："+content);
			if(content.indexOf("{")>-1){
				content = content.substring(content.indexOf("{"),content.indexOf("}")+1);
			}
			result = JSONObject.parseObject(content);
			System.out.println("================"+result.get("success"));
		} finally {
			if (response != null) {
				response.close();
			}
			httpclient.close();
		}
		return result;
	}

	public static String PostMessage(String wsdl, String body,String token) {
		String contentType = "application/json";
		int timeout = 2000000;
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(wsdl);
		// 设置连接超时
		client.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
		// 设置读取时间超时
		client.getHttpConnectionManager().getParams().setSoTimeout(timeout);
		RequestEntity requestEntity = new StringRequestEntity(body);
		if(token!=null){
			postMethod.setRequestHeader(new Header("API_TOKEN",token));
		}
		postMethod.setRequestHeader(new Header("Content-Type",contentType));

		String result = null;
		try {
			// 设置请求体
			postMethod.setRequestEntity(requestEntity);
			int status = client.executeMethod(postMethod);
			// 打印请求状态码
			log.debug("status:" + status);
			// 获取响应体输入流
			InputStream is = postMethod.getResponseBodyAsStream();
			byte[] by = IOUtils.toByteArray(is);
			is.read(by);
			// 获取请求结果字符串
			result = new String(by, StandardCharsets.UTF_8);
			log.debug("utf-8-------------------------------------" + result);

			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		return result;

	}


	public static String GetMessage(String wsdl, String body,String token) {
//		String contentType = "application/soap+xml;charset=UTF-8;action=\"urn:sap-com:document:sap:soap:functions:mc-style:Z_B2B_LONG_TEXT:ZB2bLongTextRequest\"";
		String contentType = "application/json";
		int timeout = 2000000;
		HttpClient client = new HttpClient();
		GetMethod getMethod = new GetMethod(wsdl);
		// 设置连接超时
		client.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
		// 设置读取时间超时
		client.getHttpConnectionManager().getParams().setSoTimeout(timeout);


//		RequestEntity requestEntity = new StringRequestEntity(body);
		if(token!=null){
			getMethod.setRequestHeader(new Header("API_TOKEN",token));
		}
		getMethod.setRequestHeader(new Header("Content-Type",contentType));


		String result = null;
		try {

			int status = client.executeMethod(getMethod);
			// 打印请求状态码
			System.out.println("status:" + status);
			// 获取响应体输入流
			InputStream is = getMethod.getResponseBodyAsStream();
			byte[] by = IOUtils.toByteArray(is);
			is.read(by);
			// 获取请求结果字符串
			result = new String(by, StandardCharsets.UTF_8);
			System.out.println("utf-8-------------------------------------" + result);

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return result;

	}



	public static String getSapMessage(String wsdl, String body) {
//		String contentType = "application/soap+xml;charset=UTF-8;action=\"urn:sap-com:document:sap:soap:functions:mc-style:Z_B2B_LONG_TEXT:ZB2bLongTextRequest\"";
		String contentType = "text/xml;charset=utf-8";
		String username="ALEREMOTE";
		String password="jj@ale123";
		String token = RestfulUtil.getBase64(username+":"+password);
		int timeout = 2000000;
		System.out.println("HttpClient 发送SOAP请求");
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(wsdl);
		// 设置连接超时
		client.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
		// 设置读取时间超时
		client.getHttpConnectionManager().getParams().setSoTimeout(timeout);
		RequestEntity requestEntity = new StringRequestEntity(body);
		postMethod.setRequestHeader(new Header("Authorization", "Basic " + token));
		postMethod.setRequestHeader(new Header("Content-Type",contentType));
		postMethod.setRequestHeader(new Header("Accept-Language", "zh-cn,zh;q=0.5"));

		String result = null;
		try {
			//System.out.println("requestEntity---------"+requestEntity);
			// 设置请求体
			postMethod.setRequestEntity(requestEntity);
			int status = client.executeMethod(postMethod);
			// 打印请求状态码
			System.out.println("status:" + status);
			// 获取响应体输入流
			InputStream is = postMethod.getResponseBodyAsStream();
			byte[] by = IOUtils.toByteArray(is);
			is.read(by);
			// 获取请求结果字符串
			result = new String(by, StandardCharsets.UTF_8);
			System.out.println("utf-8-------------------------------------" + result);
			//try {
			//File file = new File("/" + File.separator + "Users" + File.separator + "huaqihao" + File.separator + "Desktop" + File.separator + "台湾KHS" + File.separator + "测试问题" + File.separator + "sapXML" + File.separator + "VBBKCLongText.xml");
			//if(!file.getParentFile().exists()){
			//	file.getParentFile().mkdirs();
			//}

			//	OutputStream output = new FileOutputStream(file);
			//	output.write(by);

			//这个方法也可以把底层的流给关闭了

			//	output.flush();
			//	output.close();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return result;

	}

	/**
	 * 调用对方接口方法
	 * @param path 对方或第三方提供的路径
	 * @param data 向对方或第三方发送的数据，大多数情况下给对方发送JSON数据让对方解析
	 */
	public static void interfaceUtil(String path,String data) {
		try {
			URL url = new URL(path);
			//打开和url之间的连接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			PrintWriter out = null;
			//请求方式
            conn.setRequestMethod("GET");
//           //设置通用的请求属性
			conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
			conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
			conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty("Host", "192.168.12.103:8010");
			conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36 SLBrowser/7.0.0.8031 SLBChan/33");
			//设置是否向httpUrlConnection输出，设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
			//最常用的Http请求无非是get和post，get请求可以获取静态页面，也可以把参数放在URL字串后面，传递给servlet，
			//post与get的 不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。
			conn.setDoOutput(true);
			conn.setDoInput(true);
			//获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			//发送请求参数即数据
			out.print(data);
			//缓冲数据
			out.flush();
			//获取URLConnection对象对应的输入流
			InputStream is = conn.getInputStream();
			//构造一个字符流缓存
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String str = "";
			while ((str = br.readLine()) != null) {
				System.out.println(str);
			}
			//关闭流
			is.close();
			//断开连接，最好写上，disconnect是在底层tcp socket链接空闲时才切断。如果正在被其他线程使用就不切断。
			//固定多线程的话，如果不disconnect，链接会增多，直到收发不出信息。写上disconnect后正常一些。
			conn.disconnect();
			System.out.println("完整结束");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String send(String url, JSONObject jsonObject) throws ParseException, IOException {
		String body = "";

		//创建httpclient对象
		CloseableHttpClient client = HttpClients.createDefault();
		//创建post方式请求对象
		HttpPost httpPost = new HttpPost(url);

		//装填参数
		StringEntity s = new StringEntity(jsonObject.toString(), "utf-8");
		s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		//设置参数到请求对象中
		httpPost.setEntity(s);
		System.out.println("请求地址："+url);
//        System.out.println("请求参数："+nvps.toString());

		//设置header信息
		//指定报文头【Content-type】、【User-Agent】
//        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		//执行请求操作，并拿到结果（同步阻塞）
		CloseableHttpResponse response = client.execute(httpPost);
		//获取结果实体
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			//按指定编码转换结果实体为String类型
			body = EntityUtils.toString(entity, "UTF-8");
			System.out.println(body);
		}
		EntityUtils.consume(entity);
		//释放链接
		response.close();
		return body;
	}

	public static void trustAllHosts() {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[] {};
			}
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
		} };

		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HostnameVerifier hv = new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		} catch (Exception e) {
			//e.printStackTrace();
		}
	}


	public static void main(String[] args) throws Exception {
		String json = "{\"Cmd\":\"CCPRKDAdd\",\"SetBook\":\"888\",\"SetYear\":\"2022\",\"LoginName\":\"999999\",\"LoginPwd\":\"Zx123123\",\"Params\":{\"json\":\"[{\\\"AddType\\\":1,\\\"dDate\\\":\\\"2022-01-10\\\",\\\"cCode\\\":\\\"SCRK20220110-0001\\\",\\\"cVenCode\\\":\\\"zx00001\\\",\\\"dARVDate\\\":\\\"2022-01-10\\\",\\\"bIsRedVouch\\\":false,\\\"cWhName\\\":\\\"BMC成品\\\",\\\"cRdCode\\\":\\\"12\\\",\\\"cWhCode\\\":\\\"53\\\",\\\"Details\\\":[{\\\"iMPoIds\\\":1000153888,\\\"cInvCode\\\":\\\"HL01000808\\\",\\\"iQuantity\\\":1.000000,\\\"cBatch\\\":\\\"pl2106030\\\",\\\"cMoLotCode\\\":\\\"pl2106030\\\"},{\\\"iMPoIds\\\":1000153883,\\\"cInvCode\\\":\\\"HL02000810\\\",\\\"iQuantity\\\":1.000000,\\\"cBatch\\\":\\\"pl2106030\\\",\\\"cMoLotCode\\\":\\\"pl2106030\\\"},{\\\"iMPoIds\\\":1000153880,\\\"cInvCode\\\":\\\"RS01501367\\\",\\\"iQuantity\\\":2.000000,\\\"cBatch\\\":\\\"pl2106030\\\",\\\"cMoLotCode\\\":\\\"pl2106030\\\"},{\\\"iMPoIds\\\":1000153885,\\\"cInvCode\\\":\\\"HL02001164\\\",\\\"iQuantity\\\":1.000000,\\\"cBatch\\\":\\\"pl2106030\\\",\\\"cMoLotCode\\\":\\\"pl2106030\\\"},{\\\"iMPoIds\\\":1000153887,\\\"cInvCode\\\":\\\"HL02000808\\\",\\\"iQuantity\\\":1.000000,\\\"cBatch\\\":\\\"pl2106030\\\",\\\"cMoLotCode\\\":\\\"pl2106030\\\"},{\\\"iMPoIds\\\":1000153879,\\\"cInvCode\\\":\\\"RS01501365\\\",\\\"iQuantity\\\":2.000000,\\\"cBatch\\\":\\\"pl2106030\\\",\\\"cMoLotCode\\\":\\\"pl2106030\\\"},{\\\"iMPoIds\\\":1000153882,\\\"cInvCode\\\":\\\"HL01000220\\\",\\\"iQuantity\\\":4.000000,\\\"cBatch\\\":\\\"pl2106030\\\",\\\"cMoLotCode\\\":\\\"pl2106030\\\"},{\\\"iMPoIds\\\":1000153884,\\\"cInvCode\\\":\\\"HL01000810\\\",\\\"iQuantity\\\":1.000000,\\\"cBatch\\\":\\\"pl2106030\\\",\\\"cMoLotCode\\\":\\\"pl2106030\\\"},{\\\"iMPoIds\\\":1000153886,\\\"cInvCode\\\":\\\"HL01001164\\\",\\\"iQuantity\\\":1.000000,\\\"cBatch\\\":\\\"pl2106030\\\",\\\"cMoLotCode\\\":\\\"pl2106030\\\"},{\\\"iMPoIds\\\":1000153881,\\\"cInvCode\\\":\\\"HL02000252\\\",\\\"iQuantity\\\":4.000000,\\\"cBatch\\\":\\\"pl2106030\\\",\\\"cMoLotCode\\\":\\\"pl2106030\\\"}]}]\"}}";
		//PostMessage("https://www.zxyun119.com:443/thirdPartyApi/login/api/2.0/auth/getToken",json,null);
//		GetMessage("https://www.zxyun119.com:443/thirdPartyApi/ThreePlaceREST/api/2.0/project/getProjectListInfo?pageNum=1&pageSize=10",json,"97e95972-5527-4fb2-864c-2b6bcdcc547a");
//		GetMessage("http://192.168.12.103:8010/Service/160Service.asmx/U8API",json,null);
//		httpGet("http://192.168.12.103:8010/Service/160Service.asmx/U8API",json);
//		Map map1 = new LinkedHashMap();
//		map1.put("Cmd","CGRKDAdd");
//		map1.put("SetBook","888");
//		map1.put("SetYear","2021");
//		map1.put("LoginName","9002");
//		map1.put("LoginPwd","qqq");
//		Map map2 = new LinkedHashMap();
//		List<Map> mapList = new ArrayList<>();
//		Map map3 = new LinkedHashMap();
//		map3.put("AddType",2);
//		map3.put("cCode","CG0000000001");
//		map3.put("dDate","2021-12-01");
//		map3.put("cVenCode","ceshi01");
//		map3.put("dARVDate","2021-12-01");
//		map3.put("bIsRedVouch",false);
//		map3.put("cOrderCode","PO2020030490");
//		map3.put("cpersoncode",null);
//		map3.put("cMemo","Webservice生成");
//		map3.put("cWhCode","19");
//		map3.put("cRdCode","11");
//		List<Map> mapList1 = new ArrayList<>();
//		for(int i=1;i<=2;i++){
//			Map map4 = new LinkedHashMap();
//			map4.put("iPOsID",Integer.parseInt("1000004989"));
//			map4.put("cInvCode","RS01400002");
//			map4.put("iQuantity",new BigDecimal("8100"));
//			map4.put("iUnitCost",new BigDecimal("0.75"));
//			map4.put("cbatch","PC20211201");
//			map4.put("cbMemo","测试数据-1");
//			map4.put("cPosition","12");
//			map4.put("cFree10","11111111111");
//			map4.put("cFree9","1");
//			mapList1.add(map4);
//		}
//		map3.put("Details",mapList1);
//		mapList.add(map3);
//		map2.put("json", JSON.toJSONString(mapList));
//		map1.put("Params",map2);
//		JSONObject jsonObj=new JSONObject(map1);
//		System.out.println(jsonObj.toString());
//		System.out.println(Integer.parseInt("1000004989"));
		httpPost("http://192.168.12.103:8010/Service/160Service.asmx/U8API",json);
	}
}
