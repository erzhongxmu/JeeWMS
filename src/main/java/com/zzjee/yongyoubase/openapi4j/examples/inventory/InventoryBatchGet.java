package com.zzjee.yongyoubase.openapi4j.examples.inventory;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.InventoryService;


public class InventoryBatchGet {
	final static Logger logger = LogManager.getLogger(InventoryBatchGet.class);

	public static void main(String[] args) {

		String to_account = args[0];	//提供方id
		String page_index = args[1];	//	页号
		String rows_per_page = args[2];	//	每页行数
		String code_begin = args[3];	//	起始编码
		String code_end = args[4];		//	结束编码
		String name = args[5];			//	名称关键字
		String sort_code = args[6];		//	分类编码
		String sort_name = args[7];		//分类名称关键字
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("to_account", to_account);
		paramMap.put("page_index", page_index);
		paramMap.put("rows_per_page", rows_per_page);
		paramMap.put("code_begin", code_begin);
		paramMap.put("code_end", code_end);
		paramMap.put("name", name);
		paramMap.put("sort_code", sort_code);
		paramMap.put("sort_name", sort_name);
		
		InventoryService ds = new InventoryService();
		try {
			JSONObject record = ds.batchGet(paramMap);
			logger.debug(record.toJSONString());
		} catch (OpenAPIException e) {
			logger.error(e.getMessage());
		}
	}
}
