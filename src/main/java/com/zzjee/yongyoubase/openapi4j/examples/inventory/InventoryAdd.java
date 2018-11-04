package com.zzjee.yongyoubase.openapi4j.examples.inventory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.platform.TradeService;
import com.zzjee.yongyoubase.openapi4j.service.InventoryService;

public class InventoryAdd {
	final static Logger logger = LogManager.getLogger(InventoryAdd.class);

	public static void main(String[] args) {
		String to_account = "test_wanxing";		//提供方id
		//要新增的存货的json格式
		String jsonBody = "{\"inventory\":{\"code\":\"0394\",\"name\":\"0394n\",\"sort_code\":\"03\",\"main_measure\":\"0101\",\"entry\":[{\"invcode\":\"0392\"}]}}";
		InventoryService inventoryService = new InventoryService();
		try {
			//新增时需要手动获取tradeId以保证此次操作唯一
			String tradeId = TradeService.getTradeId();
			// 通过平台存货新增
			JSONObject record = inventoryService.add(jsonBody, to_account, tradeId);
			logger.debug(record.toJSONString());
		} catch (OpenAPIException e) {
			
			e.printStackTrace();
		}
	}
}
