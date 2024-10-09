package com.zzjee.yongyoubase.openapi4j.examples.account;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.AccountService;

public class AccountGet {
	final static Logger logger = LogManager.getLogger(AccountGet.class);

	public static void main(String[] args) {
		String to_account = args[0];           //提供方id
		String id = args[1];					//帐套号
		AccountService ds = new AccountService();
		try {
			JSONObject record = ds.get(id, to_account);
			logger.info(record.toString());
		} catch (OpenAPIException e) {
			e.printStackTrace();
		}
	}
}
