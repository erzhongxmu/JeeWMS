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
 * 应收单业务类
 * @author Administrator
 *
 */
public class OughtreceiveService extends BaseService {
	final static Logger logger = LogManager.getLogger(OughtreceiveService.class);

	public OughtreceiveService() {

		this.access_token = TokenManager.getToKenId();

	}

	public OughtreceiveService(String token) {
		this.access_token = token;
	}

	/**
	 * 通过应收单id号获取单个应收单信息
	 * @param id
	 * @param to_account
	 * @return
	 * @throws OpenAPIException
	 */
	public JSONObject get(String id, String to_account) throws OpenAPIException {
		JSONObject record;
		try {
			Map<String, String> paramMap = new HashMap();
			paramMap.put("to_account", to_account);
			paramMap.put("id", id);
			String url = this.createURL("oughtreceive/get", paramMap);
			logger.debug(url);
			record = JSONObject.parseObject(HttpUtil.get(url));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new OpenAPIException(e.getMessage(), e);
		}
		return record;
	}

	/**
	 * 通过各种查询条件批量获取应收单 查询条件以map形式传递
	 * @param paramMap
	 * @return
	 * @throws OpenAPIException
	 */
	public JSONObject batchGet(Map<String, String> paramMap) throws OpenAPIException {
		JSONObject record;
		try {
			String url = this.createURL("oughtreceivelist/batch_get", paramMap);
			logger.info(url);
			record = JSONObject.parseObject(HttpUtil.get(url));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new OpenAPIException(e.getMessage(), e);
		}
		return record;
	}
	
	/**
	 * 通过平台的交易号新增应收单
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
			String url = this.createURL("oughtreceive/add", paramMap);
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
	 * 通过自身业务交易号生成应收单
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
			String url = this.createURL("oughtreceive/add", paramMap);
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
