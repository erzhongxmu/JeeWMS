package com.zzjee.yongyoubase.openapi4j.examples.freearch;

import java.util.HashMap;
import java.util.Map;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.FreearchService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

public class FreearchBatchGet {

	final static Logger logger = LogManager.getLogger(FreearchBatchGet.class);
	
	public static void main(String[] args) {

		String to_account = args[0];
		String page_index = args[1];
		String rows_per_page = args[2];
		String field_name = args[3];
		String alias = args[4];
		String item = args[5];
		String value = args[6];
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("to_account", to_account);
		paramMap.put("page_index", page_index);
		paramMap.put("rows_per_page", rows_per_page);
		paramMap.put("field_name", field_name);
		paramMap.put("alias", alias);
		paramMap.put("item", item);
		paramMap.put("value", value);
		
		FreearchService ds = new FreearchService();
		try {
			JSONObject record = ds.batchGet(paramMap);
			logger.debug(record.toJSONString());
		} catch (OpenAPIException e) {
			logger.error(e.getMessage());
		}
	}

}
