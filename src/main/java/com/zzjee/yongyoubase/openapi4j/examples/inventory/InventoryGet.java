package com.zzjee.yongyoubase.openapi4j.examples.inventory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.InventoryService;

public class InventoryGet {
	final static Logger logger = LogManager.getLogger(InventoryGet.class);

	public static void main(String[] args) {
		String to_account = args[0];	//	提供方id
		String id = args[1];			//	存货编码
		InventoryService ds = new InventoryService();
		try {
			JSONObject record = ds.get(id, to_account);
			logger.info(record.toString());
		} catch (OpenAPIException e) {
			e.printStackTrace();
		}
	}
}
