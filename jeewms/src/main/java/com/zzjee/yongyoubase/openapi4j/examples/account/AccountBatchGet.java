package com.zzjee.yongyoubase.openapi4j.examples.account;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.AccountService;


public class AccountBatchGet {
	final static Logger logger = LogManager.getLogger(AccountBatchGet.class);

	public static void main(String[] args) {

		String to_account = args[0];	//提供方id
		String page_index = args[1];	//页号
		String rows_per_page = args[2];	//每页行数
		String code_begin = args[3];	//	起始帐套号
		String code_end = args[4];		//结束帐套号
		String name = args[5];			//帐套名关键字
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("to_account", to_account);
		paramMap.put("name", name);
		paramMap.put("page_index", page_index);
		paramMap.put("rows_per_page", rows_per_page);
		paramMap.put("code_begin", code_begin);
		paramMap.put("code_end", code_end);
		
		AccountService ds = new AccountService();
		try {
			JSONObject record = ds.batchGet(paramMap);
			logger.debug(record.toJSONString());
		} catch (OpenAPIException e) {
			logger.error(e.getMessage());
		}
	}
}
