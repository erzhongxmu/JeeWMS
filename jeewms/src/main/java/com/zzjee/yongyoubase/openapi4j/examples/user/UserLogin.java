package com.zzjee.yongyoubase.openapi4j.examples.user;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.UserService;


public class UserLogin {
	final static Logger logger = LogManager.getLogger(UserLogin.class);

	public static void main(String[] args) {
		String to_account = args[0];	//提供方企业id
		String userId = args[1];		//用户id
		String password = args[2];		//密码
		
		UserService ds = new UserService();
		try {
			JSONObject record = ds.login(userId, password, to_account);
			logger.debug(record.toJSONString());
		} catch (OpenAPIException e) {
			logger.error(e.getMessage());
		}
		
	}
}
