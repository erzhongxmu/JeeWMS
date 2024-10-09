package com.zzjee.yongyoubase.openapi4j.examples.customer;


import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.CustomerService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSONObject;


public class CustomerGet {
	final static Logger logger = LogManager.getLogger(CustomerGet.class);

	public static void main(String[] args) {
		//数据提供方id
		String to_account = args[0];
		//客户编码
		String id = args[1];
		CustomerService customerService = new CustomerService();
		try {
			JSONObject record = customerService.get(id, to_account);
			logger.info(record.toString());
		} catch (OpenAPIException e) {
			e.printStackTrace();
		}
	}
}
