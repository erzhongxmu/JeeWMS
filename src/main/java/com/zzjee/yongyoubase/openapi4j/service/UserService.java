package com.zzjee.yongyoubase.openapi4j.service;

import com.alibaba.fastjson.JSONObject;
import com.zzjee.wmutil.wmUtil;
import com.zzjee.yongyoubase.openapi4j.commons.TokenManager;
import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.model.Record;
import com.zzjee.yongyoubase.openapi4j.platform.TradeService;
import com.zzjee.yongyoubase.openapi4j.util.HttpUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户
 */
public class UserService extends BaseService {
	
	final static Logger logger = LogManager.getLogger(UserService.class);

	public UserService() {
		this.access_token = TokenManager.getToKenId();
	}

	public UserService(String token) {
		this.access_token = token;
	}
	
	/**
	 * 用户登陆
	 * @param userId   用户id
	 * @param password	密码
	 * @param to_account  提供方企业code
	 * @return			登陆结果
	 * @throws OpenAPIException
	 */
	public JSONObject login(String userId, String password, String to_account) throws OpenAPIException {
		JSONObject record;
		try {
			
			String jsonBody = String.format("{{\"user\":{{\"user_id\":\"%s\",\"password\":\"%s\"}}}}"
                    , userId
                    , password);
			Map<String, String> paramMap = new HashMap();
			paramMap.put("to_account", to_account);
			
			String url = this.createURL("user/login", paramMap);
			logger.debug(url);
			String resultStr = HttpUtil.post(url, jsonBody);
			
			JSONObject resultRecord = Record.parseObject(resultStr);
			record = resultRecord;
		} catch (Exception e) {
			throw new OpenAPIException(e.getMessage(), e);
		}
		return record;
	}

}
