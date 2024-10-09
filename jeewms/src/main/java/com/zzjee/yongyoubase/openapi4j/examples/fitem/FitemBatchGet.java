package com.zzjee.yongyoubase.openapi4j.examples.fitem;

import java.util.HashMap;
import java.util.Map;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.FitemService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;


public class FitemBatchGet {
	final static Logger logger = LogManager.getLogger(FitemBatchGet.class);

	public static void main(String[] args) {

		String to_account = args[0];	//提供方id
		String item_class_code = args[1];	//	项目分类编码
		String item_class_name = args[2];	//	项目分类名称
		String item_name = args[3];		//	项目大类名称
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("to_account", to_account);
		paramMap.put("item_class_code", item_class_code);
		paramMap.put("item_class_name", item_class_name);
		paramMap.put("item_name", item_name);
		
		FitemService ds = new FitemService();
		try {
			JSONObject record = ds.batchGet(paramMap);
			logger.debug(record.toJSONString());
		} catch (OpenAPIException e) {
			logger.error(e.getMessage());
		}
	}
}
