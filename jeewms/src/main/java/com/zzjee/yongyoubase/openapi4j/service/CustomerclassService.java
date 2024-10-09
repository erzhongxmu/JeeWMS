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
 * Copyright(c) 2015-2015 by yonyouup. All Rights Reserved 客户分类信息
 * 
 * @author liujl
 * @version <类版本> , 2015年12月20日
 * @see <相关类/方法>
 * @since <产品/模块版本>
 */
public class CustomerclassService extends BaseService{
	final static Logger logger = LogManager.getLogger(CustomerclassService.class);
	
	public CustomerclassService() {
		this.access_token = TokenManager.getToKenId();
	}

	public CustomerclassService(String token) {
		this.access_token = token;
	}
	/**
	 * 获取某个客户分类信息 
	 * @param id 客户分类编码
	 * @param to_account 提供方id
	 * @return
	 * @throws OpenAPIException
	 */
	public JSONObject get(String id, String to_account) throws OpenAPIException {
		JSONObject record;
		try {
			Map<String, String> paramMap = new HashMap();
			paramMap.put("to_account", to_account);
			paramMap.put("id", id);
			String url = this.createURL("customerclass/get", paramMap);
			logger.info(url);
			record = JSONObject.parseObject(HttpUtil.get(url));
		} catch (Exception e) {
			throw new OpenAPIException(e.getMessage(), e);
		}
		return record;
	}
	/**
	 * 批量获取客户分类信息
	 * @param paramMap
	 * @return
	 * @throws OpenAPIException
	 */
	public JSONObject batchGet(Map<String, String> paramMap) throws OpenAPIException {
		JSONObject record;
		try {
			String url = this.createURL("customerclass/batch_get", paramMap);
			logger.debug(url);
			record = JSONObject.parseObject(HttpUtil.get(url));
		} catch (Exception e) {
			
			throw new OpenAPIException(e.getMessage(), e);
		}
		return record;
	}
}
