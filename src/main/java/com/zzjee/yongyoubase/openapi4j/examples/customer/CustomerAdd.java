package com.zzjee.yongyoubase.openapi4j.examples.customer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.CustomerService;

public class CustomerAdd {
	final static Logger logger = LogManager.getLogger(CustomerAdd.class);

	public static void main(String[] args) {
		//提供方id
		String to_account = args[0];
		//需要提交的json格式数据
		String jsonBody =  args[1];
		CustomerService customerService = new CustomerService();
		try {
			// 通过平台交易号新增
			JSONObject record = customerService.add(jsonBody, to_account);

		} catch (OpenAPIException e) {

			e.printStackTrace();
		}
	}
}
