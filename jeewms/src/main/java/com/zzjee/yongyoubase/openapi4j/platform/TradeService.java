package com.zzjee.yongyoubase.openapi4j.platform;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.commons.TokenManager;
import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.util.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.util.ResourceUtil;

import java.util.Properties;


public class TradeService {
	

	/**
	 * 获取交易号
	 * @return
	 * @throws OpenAPIException
	 */
	    public static String getTradeId() throws OpenAPIException {
//	    	Properties prop = PropUtil.getProperties("/config.properties");
//	        String url = prop.getProperty("api_url_trade_get");
//	        String from_account = prop.getProperty("from_account");
//	        String app_key = prop.getProperty("app_key");

			String url = ResourceUtil.getConfigByName("api_url_trade_get");
			String from_account = ResourceUtil.getConfigByName("from_account");
			String app_key = ResourceUtil.getConfigByName("app_key");
	        String token = TokenManager.getToKenId();
	        url = StringUtils.replace(url, "{from_account}", from_account);
	        url = StringUtils.replace(url, "{app_key}", app_key);
	        url = StringUtils.replace(url, "{token}", token);

	         String tradeId = null;
			try {
				String str =  HttpUtil.get(url);
				 JSONObject jsonObject = JSONObject.parseObject(str);
			     JSONObject tradeObject = jsonObject.getJSONObject("trade");
			     tradeId = tradeObject.getString("id");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new OpenAPIException(e.getMessage(),e);
			}
	       
	       
	        return tradeId;
	    }
	    
	   

	   
}
