package com.base.config.sign.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

/**
 * author:lucas date:2017.5.2 function:md5加密 传进来json串和token做比较
 */
public class RestfulUtil {
	final static Logger LOGGER = LoggerFactory.getLogger(RestfulUtil.class);
	// 公共KEY值，用于验证加密token
	private static final String KEY = "khsB2b";

	/**
	 * 检验请求token是否合法
	 */
	public static boolean Certification(String json, String token) {
		String md5 = null;
		json += "&" + KEY;
		try {
			json = new String(json.getBytes(), StandardCharsets.UTF_8);
			md5 = RestfulUtil.getMD5str(json);
            return token != null && md5 != null && token.equals(md5);
		} catch (Exception e) {
			LOGGER.error("Certification 驗證失敗 error", e);
			return false;
		}

	}

	/**
	 * 用于将传入字符串进行md5加密，返回密文String
	 */
	public static String getMD5str(String express) throws NoSuchAlgorithmException {
		MessageDigest mdInst = MessageDigest.getInstance("MD5");
		mdInst.update(express.getBytes());
		byte[] mb = mdInst.digest();
		int i;
		StringBuffer buf = new StringBuffer();
		for (int offset = 0; offset < mb.length; offset++) {
			i = mb[offset];
			if (i < 0) {
				i += 256;
			}
			if (i < 16) {
				buf.append("0");
			}
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
	}

	/**
	 * 检验请求时间是否超时
	 */
	public static boolean isReqTimeOut(String timestamp) {
		Timestamp now = new Timestamp(System.currentTimeMillis());

		try {
			Integer reqTime = Integer.parseInt(timestamp);
			// 判断请求发起时间是否在当前时间一分钟以内
            return 0 <= Math.abs(now.getTime() / 1000 - reqTime) && Math.abs(now.getTime() / 1000 - reqTime) < 600;
		} catch (Exception e) {
			LOGGER.error("isReqTimeOut 驗證失敗 error", e);
			return false;
		}
	}

	// Base64加密
	public static String getBase64(String str) {
		byte[] b = null;
		String s = null;
        b = str.getBytes(StandardCharsets.UTF_8);
		if (b != null) {
			s = Base64.encode(b);
		}
		return s;
	}

	// Base64解密
	public static String getFromBase64(String s) {
		byte[] b = null;
		String result = null;
		if (s != null) {
			Base64 decoder = new Base64();
			try {
				b = Base64.decode(s);
				result = new String(b, StandardCharsets.UTF_8);
			} catch (Exception e) {
				LOGGER.error("getFromBase64 解密失敗 error", e);
			}
		}
		return result;
	}

	// 用于密码加密
	public static String md5(String data) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(data.getBytes());
			StringBuffer buf = new StringBuffer();
			byte[] bits = md.digest();
			for (int i = 0; i < bits.length; i++) {
				int a = bits[i];
				if (a < 0) {
					a += 256;
				}
				if (a < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(a));
			}
			return buf.toString();
		} catch (Exception e) {
			LOGGER.error("md5 加密失敗 error", e);
			return null;
		}
	}

	public static String getMessage(String token, String uri) {
		LOGGER.info("---------------------------進入getMessage---------------------------");
		String result = null;
		HttpClient httpClient = HttpClientBuilder.create().build();
		//HttpGet httpGet = new HttpGet(uri);
		HttpGet httpGet = new HttpGet("");
		httpGet.addHeader(new BasicHeader("Authorization", "Basic " + token));
		httpGet.addHeader("Content-Type", "application/json");
		httpGet.setHeader("Accept", "application/json");
		// Make the request
		HttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			LOGGER.error("getMessage ClientProtocolException error", e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error("getMessage IOException error", e);
		}
		// Process the results
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != 200) {
			LOGGER.warn("請求失敗");
		} else {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				try {
					result = EntityUtils.toString(entity);
					if (result.startsWith("\"") && result.endsWith("\"")) {
						result = result.substring(1, result.length() - 1);
					}
					if (result.contains("\\")) {
						result = result.replace("\\", "");
					}
					// 这个方法也可以把底层的流给关闭了
					EntityUtils.consume(entity);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					LOGGER.error("md5 ParseException error", e);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					LOGGER.error("md5 IOException error", e);
				}
			} else {
				LOGGER.info("---------------------返回內容為空---------------------------");
			}
		}
		return result;
	}

}
