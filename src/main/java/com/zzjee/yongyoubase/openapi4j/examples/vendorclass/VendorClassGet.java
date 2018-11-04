package com.zzjee.yongyoubase.openapi4j.examples.vendorclass;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.VendorClassService;

public class VendorClassGet {
	final static Logger logger = LogManager.getLogger(VendorClassGet.class);

	public static void main(String[] args) {
		String to_account = args[0];	//提供方id
		String id = args[1];			//	供应商分类编码
		VendorClassService ds = new VendorClassService();
		try {
			JSONObject record = ds.get(id, to_account);
			logger.info(record.toString());
		} catch (OpenAPIException e) {
			e.printStackTrace();
		}
	}
}
