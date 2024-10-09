package com.zzjee.yongyoubase.openapi4j.examples.inventory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.InventoryService;

public class InventoryAddWithBizId {
	final static Logger logger = LogManager.getLogger(InventoryAddWithBizId.class);

	public static void main(String[] args) {
		
		String to_account = "test_wanxing";		//提供方id
		String bizId = "0395";					//上游主键
		String jsonBody = "{\"inventory\":{\"code\":\"0395\",\"name\":\"0395n\",\"sort_code\":\"03\",\"main_measure\":\"0101\",\"entry\":[{\"invcode\":\"0392\"}]}}";
		InventoryService inventoryService = new InventoryService();
		try {
			
			// 通过上游主键新增
			JSONObject record = inventoryService.addByBizId(bizId, jsonBody, to_account);
			logger.info(record.toString());
		} catch (OpenAPIException e) {

			e.printStackTrace();
		}
	}
}
