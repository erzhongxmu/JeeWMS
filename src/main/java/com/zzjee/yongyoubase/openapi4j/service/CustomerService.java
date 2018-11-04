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
<<<<<<< HEAD
 * 客户档案
 * @author Administrator
 *
=======
 * Copyright(c) 2015-2015 by yonyouup. All Rights Reserved 客户
 * 
 * @author liujl
 * @version <类版本> , 2015年12月20日
 * @see <相关类/方法>
 * @since <产品/模块版本>
>>>>>>> 4accb8be349427f21d4197b6daf8eb088d1de451
 */
public class CustomerService extends BaseService {
	final static Logger logger = LogManager.getLogger(CustomerService.class);

	public CustomerService() {

		this.access_token = TokenManager.getToKenId();

	}

	public CustomerService(String token) {
		this.access_token = token;
	}
	

	/**
	 * 获取某个客户信息 
	 * @param id 客户编码
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
			String url = this.createURL("customer/get", paramMap);
			logger.info(url);
			record = JSONObject.parseObject(HttpUtil.get(url));
		} catch (Exception e) {
			throw new OpenAPIException(e.getMessage(), e);
		}
		return record;
	}

	/**
	 * 根据查询条件批量获取客户信息
	 * @param paramMap
	 * @return
	 * @throws OpenAPIException
	 */
	public JSONObject batchGet(Map<String, String> paramMap) throws OpenAPIException {
		JSONObject record;
		try {
			String url = this.createURL("customer/batch_get", paramMap);
			logger.info(url);
			record = JSONObject.parseObject(HttpUtil.get(url));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new OpenAPIException(e.getMessage(), e);
		}
		return record;
	}

	/**
	 * 通过平台的交易号新增客户
	 * @param jsonBody
	 * @param to_account
	 * @return
	 * @throws OpenAPIException
	 */
	public JSONObject add(String jsonBody, String to_account) throws OpenAPIException {
		JSONObject record;
		try {
			String tradeId = TradeService.getTradeId();
			Map<String, String> paramMap = new HashMap();
			paramMap.put("to_account", to_account);
			paramMap.put("tradeid", tradeId);
			String url = this.createURL("customer/add", paramMap);
			logger.debug(url);
			String resultStr = HttpUtil.post(url, jsonBody);
			logger.debug(resultStr);
			JSONObject resultRecord = Record.parseObject(resultStr);
			Thread.sleep(3000);
			record = Record.parseObject(HttpUtil.get(resultRecord.getString("url")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new OpenAPIException(e.getMessage(), e);
		}
		return record;
	}

	/**
	 * 根据上游主键来新增客户
	 * @param bizId
	 * @param jsonBody
	 * @param to_account
	 * @return
	 * @throws OpenAPIException
	 */
	public JSONObject addByBizId(String bizId, String jsonBody, String to_account) throws OpenAPIException {
		JSONObject record;
		try {

			Map<String, String> paramMap = new HashMap();
			paramMap.put("to_account", to_account);
			paramMap.put("biz_id", bizId);
			String url = this.createURL("customer/add", paramMap);
			logger.info(url);
			String resultStr = HttpUtil.post(url, jsonBody);
			logger.info(resultStr);
			JSONObject resultRecord = Record.parseObject(resultStr);
			Thread.sleep(3000);
			record = Record.parseObject(HttpUtil.get(resultRecord.getString("url")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new OpenAPIException(e.getMessage(), e);
		}
		return record;
	}
	
	

}
