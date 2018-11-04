package com.zzjee.yongyoubase.openapi4j.examples.unit;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.UnitService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

public class UnitGet {
	final static Logger logger = LogManager.getLogger(UnitGet.class);
	
	public static void main(String[] args) {
		String to_account = args[0];
		String id = args[1];
		UnitService ds = new UnitService();
		try {
			JSONObject record = ds.get(id, to_account);
			logger.info(record.toString());
		} catch (OpenAPIException e) {
			e.printStackTrace();
		}
	}
}
