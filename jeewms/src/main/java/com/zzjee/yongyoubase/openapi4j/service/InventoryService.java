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
 * 存货信息
 */
public class InventoryService extends BaseService {
	final static Logger logger = LogManager.getLogger(InventoryService.class);

	public InventoryService() {
		this.access_token = TokenManager.getToKenId();
	}

	public InventoryService(String token) {
		this.access_token = token;
	}
	
	/**
	 * 获取单个存货信息
	 * @param id           存货信息 id
	 * @param to_account   目标企业code
	 * @return             存货信息
	 * @throws OpenAPIException
	 */
	public JSONObject get(String id, String to_account) throws OpenAPIException {
		JSONObject record;
		try {
			Map<String, String> paramMap = new HashMap();
			paramMap.put("to_account", to_account);
			paramMap.put("id", id);
			String url = this.createURL("inventory/get", paramMap);
			logger.debug(url);
			record = JSONObject.parseObject(HttpUtil.get(url));
		} catch (Exception e) {
			
			throw new OpenAPIException(e.getMessage(), e);
		}
		return record;
	}
	
	/**
	 * 批量获取存货信息
	 * @param paramMap  参数map
	 * @return
	 * @throws OpenAPIException
	 */
	public JSONObject batchGet(Map<String, String> paramMap) throws OpenAPIException {
		JSONObject record;
		try {
			String url = this.createURL("inventory/batch_get", paramMap);
			logger.debug(url);
			record = JSONObject.parseObject(HttpUtil.get(url));
		} catch (Exception e) {
			
			throw new OpenAPIException(e.getMessage(), e);
		}
		return record;
	}
	
	/**
	 * 新增存货
	 * @param jsonBody      存货内容
	 * @param to_account    目标企业code
	 * @param tradeId       交易号
	 * @return              操作结果
	 * @throws OpenAPIException
	 */
	public JSONObject add(String jsonBody, String to_account, String tradeId) throws OpenAPIException {
		JSONObject record;
		try {
			
			Map<String, String> paramMap = new HashMap();
			paramMap.put("to_account", to_account);
			paramMap.put("tradeid", tradeId);
			String url = this.createURL("inventory/add", paramMap);
			logger.debug(url);
			String resultStr = HttpUtil.post(url, jsonBody);
			
			JSONObject resultRecord = Record.parseObject(resultStr);
			record = resultRecord;
			/*Thread.sleep(3000);
			record = Record.parseObject(HttpUtil.get(resultRecord.getString("url")));*/
		} catch (Exception e) {
			
			throw new OpenAPIException(e.getMessage(), e);
		}
		return record;
	}
	
	/**
	 * 新增存货
	 * @param bizId       上游主键
	 * @param jsonBody    存货内容
	 * @param to_account  目标企业code
	 * @return			      操作结果
	 * @throws OpenAPIException
	 */
	public JSONObject addByBizId(String bizId, String jsonBody, String to_account) throws OpenAPIException {
		JSONObject record;
		try {

			Map<String, String> paramMap = new HashMap();
			paramMap.put("to_account", to_account);
			paramMap.put("biz_id", bizId);
			String url = this.createURL("inventory/add", paramMap);
			logger.debug(url);
			String resultStr = HttpUtil.post(url, jsonBody);
			
			JSONObject resultRecord = Record.parseObject(resultStr);
			record = resultRecord;
			/*Thread.sleep(3000);
			record = Record.parseObject(HttpUtil.get(resultRecord.getString("url")));*/
		} catch (Exception e) {
			throw new OpenAPIException(e.getMessage(), e);
		}
		return record;
	}

}
