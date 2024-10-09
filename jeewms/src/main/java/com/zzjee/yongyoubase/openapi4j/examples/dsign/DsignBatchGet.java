package com.zzjee.yongyoubase.openapi4j.examples.dsign;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.DsignService;


public class DsignBatchGet {
	final static Logger logger = LogManager.getLogger(DsignBatchGet.class);

	public static void main(String[] args) {

		String to_account = args[0];	//提供方id
		String page_index = args[1];	//	页号
		String rows_per_page = args[2];	//	每页行数
		String id_begin = args[3];		//	起始凭证类别标识
		String id_end = args[4];		//	结束凭证类别标识
		String type = args[5];			//凭证类别字关键字
		String type_name = args[6];		//	凭证类别名称关键字
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("to_account", to_account);
		paramMap.put("page_index", page_index);
		paramMap.put("rows_per_page", rows_per_page);
		paramMap.put("id_begin", id_begin);
		paramMap.put("id_end", id_end);
		paramMap.put("type", type);
		paramMap.put("type_name", type_name);
		
		DsignService ds = new DsignService();
		try {
			JSONObject record = ds.batchGet(paramMap);
			logger.debug(record.toJSONString());
		} catch (OpenAPIException e) {
			logger.error(e.getMessage());
		}
	}
}
