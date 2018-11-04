package com.zzjee.yongyoubase.openapi4j.service;

import com.alibaba.fastjson.JSONObject;
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
 * 人员信息
 */
public class PersonService extends BaseService {
	final static Logger logger = LogManager.getLogger(PersonService.class);

	public PersonService() {
		this.access_token = TokenManager.getToKenId();
	}

	public PersonService(String token) {
		this.access_token = token;
	}
	/**
	 * 获取单个人员信息
	 * @param id           人员信息id
	 * @param to_account   目标企业code
	 * @return             人员信息
	 * @throws OpenAPIException
	 */
	public JSONObject get(String id, String to_account) throws OpenAPIException {
		JSONObject record;
		try {
			Map<String, String> paramMap = new HashMap();
			paramMap.put("to_account", to_account);
			paramMap.put("id", id);
			String url = this.createURL("person/get", paramMap);
			logger.debug(url);
			record = JSONObject.parseObject(HttpUtil.get(url));
		} catch (Exception e) {
			
			throw new OpenAPIException(e.getMessage(), e);
		}
		return record;
	}
	
	/**
	 * 批量获取人员信息
	 * @param paramMap  参数map
	 * @return
	 * @throws OpenAPIException
	 */
	public JSONObject batchGet(Map<String, String> paramMap) throws OpenAPIException {
		JSONObject record;
		try {
			String url = this.createURL("person/batch_get", paramMap);
			logger.debug(url);
			record = JSONObject.parseObject(HttpUtil.get(url));
		} catch (Exception e) {
			
			throw new OpenAPIException(e.getMessage(), e);
		}
		return record;
	}

}
