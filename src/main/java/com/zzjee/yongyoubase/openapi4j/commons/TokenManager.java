package com.zzjee.yongyoubase.openapi4j.commons;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.util.HttpUtil;
import com.zzjee.yongyoubase.openapi4j.util.PropUtil;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.util.ResourceUtil;

import java.util.Properties;

public class TokenManager {

	public static AccessToken accessToken;

	public static String getToKenId() {

		long nowTime = System.currentTimeMillis();
		try {
			if (accessToken == null) {
				accessToken = getAccessToken();
			} else if ((nowTime - accessToken.getCreateTime() + 10000) >= accessToken.getExpiresIn()) {
				accessToken = getAccessToken();
			}

		} catch (OpenAPIException e) {
			e.printStackTrace();
		}

		return accessToken.getId();
	}

	private static AccessToken getAccessToken() throws OpenAPIException {
//		Properties prop = PropUtil.getProperties("/config.properties");
		String url = ResourceUtil.getConfigByName("api_url_token_get");
		String from_account = ResourceUtil.getConfigByName("from_account");
		String app_key = ResourceUtil.getConfigByName("app_key");
		String app_secret = ResourceUtil.getConfigByName("app_secret");
		url = StringUtils.replace(url, "{from_account}", from_account);
		url = StringUtils.replace(url, "{app_key}", app_key);
		url = StringUtils.replace(url, "{app_secret}", app_secret);

		String str = null;
		try {
			str = HttpUtil.get(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new OpenAPIException(e.getMessage(), e);
		}
		JSONObject jsonObject = JSONObject.parseObject(str);
		JSONObject tokenObject = jsonObject.getJSONObject("token");
		AccessToken token = new AccessToken();
		token.setId(tokenObject.getString("id"));
		token.setAppKey(tokenObject.getString("appKey"));
		token.setCreateTime(System.currentTimeMillis());
		token.setExpiresIn(tokenObject.getLongValue("expiresIn"));
		return token;
	}

	public static class AccessToken {

		private String id;

		private String appKey;

		private long createTime;

		private long expiresIn;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getAppKey() {
			return appKey;
		}

		public void setAppKey(String appKey) {
			this.appKey = appKey;
		}

		public long getCreateTime() {
			return createTime;
		}

		public void setCreateTime(long createTime) {
			this.createTime = createTime;
		}

		public long getExpiresIn() {
			return expiresIn;
		}

		public void setExpiresIn(long expiresIn) {
			this.expiresIn = expiresIn;
		}

	}
}
