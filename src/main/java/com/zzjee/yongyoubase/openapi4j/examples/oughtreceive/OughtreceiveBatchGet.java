package com.zzjee.yongyoubase.openapi4j.examples.oughtreceive;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.OughtreceiveService;

/**
 * 获取应收单信息
 * @author Administrator
 *
 */
public class OughtreceiveBatchGet {
	final static Logger logger = LogManager.getLogger(OughtreceiveBatchGet.class);

	public static void main(String[] args) {
		

		//数据提供方id  必填
		String to_account = args[0];
		//页号  非必填
		String page_index = args[1];
		//每页行数 非必填
		String rows_per_page = args[2];
		//起始编号 非必填
		String code_begin = args[3];
		//结束编号 非必填
		String code_end = args[4];
		//关键字名称 非必填
		String name = args[5];
				
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("to_account", to_account);
		paramMap.put("name", name);
		paramMap.put("page_index", page_index);
		paramMap.put("rows_per_page", rows_per_page);
		paramMap.put("code_begin", code_begin);
		paramMap.put("code_end", code_end);

		OughtreceiveService ds = new OughtreceiveService();
		try {
			JSONObject record = ds.batchGet(paramMap);
			logger.info(record.toString());
		} catch (OpenAPIException e) {
			e.printStackTrace();
		}
	}
}
