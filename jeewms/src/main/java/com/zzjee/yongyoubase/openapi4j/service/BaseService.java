package com.zzjee.yongyoubase.openapi4j.service;

import com.zzjee.wmutil.wmUtil;
import com.zzjee.yongyoubase.openapi4j.commons.TokenManager;
import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.model.Record;
import com.zzjee.yongyoubase.openapi4j.platform.TradeService;
import com.zzjee.yongyoubase.openapi4j.util.HttpUtil;
import com.zzjee.yongyoubase.openapi4j.util.PropUtil;
import org.jeecgframework.core.util.ResourceUtil;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;

public class BaseService implements Serializable {

	private static final long serialVersionUID = -8280794717935626188L;

	protected String access_token;

	protected String createURL(String apiName, Map<String, String> paramMap) {

//		Properties prop = PropUtil.getProperties("/config.properties");
//		String baseURL = prop.getProperty("baseURL");
//		String from_account = prop.getProperty("from_account");
//		String app_key = prop.getProperty("app_key");

		String baseURL = ResourceUtil.getConfigByName("baseURL");
		String from_account = ResourceUtil.getConfigByName("from_account");
		String app_key = ResourceUtil.getConfigByName("app_key");

		StringBuffer url = new StringBuffer();
		url.append(baseURL);
		url.append(apiName);
		url.append("?from_account=" + from_account + "&");
		url.append("app_key=" + app_key + "&");
		url.append("token=" + access_token + "&");
		if (paramMap != null && paramMap.size() > 0) {
			for (String key : paramMap.keySet()) {
				String value = paramMap.get(key);
				url.append(key + "=" + value + "&");
			}

		}
		url.deleteCharAt(url.length() - 1);
		return url.toString();
	}

}
