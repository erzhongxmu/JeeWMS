package com.zzjee.yongyoubase.openapi4j.examples.unit;

import java.util.HashMap;
import java.util.Map;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.UnitService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

public class UnitBatchGet {

	final static Logger logger = LogManager.getLogger(UnitBatchGet.class);
	
	public static void main(String[] args) {

		String to_account = args[0];
		String page_index = args[1];
		String rows_per_page = args[2];
		String code_begin = args[3];
		String code_end = args[4];
		String name = args[5];
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("to_account", to_account);
		paramMap.put("page_index", page_index);
		paramMap.put("rows_per_page", rows_per_page);
		paramMap.put("code_begin", code_begin);
		paramMap.put("code_end", code_end);
		paramMap.put("name", name);
		
		UnitService ds = new UnitService();
		try {
			JSONObject record = ds.batchGet(paramMap);
			logger.debug(record.toJSONString());
		} catch (OpenAPIException e) {
			logger.error(e.getMessage());
		}
	}

}
