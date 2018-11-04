package com.zzjee.yongyoubase.openapi4j.examples.customer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.CustomerService;

public class CustomerAddWithBizId {
	final static Logger logger = LogManager.getLogger(CustomerAddWithBizId.class);

	public static void main(String[] args) {
		//数据提供方id
		String to_account = args[0];
		//上游主键
		String bizId = args[1];
		//需要提交的json格式数据
		String jsonBody = args[2];
		CustomerService customerService = new CustomerService();
		try {

			// 通过自己的交易号新增
			JSONObject record = customerService.addByBizId(bizId, jsonBody, to_account);
			logger.info(record.toString());
		} catch (OpenAPIException e) {

			e.printStackTrace();
		}
	}
}
