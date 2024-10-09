package com.zzjee.yongyoubase.openapi4j.examples.payunitclass;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.PayunitClassService;

public class PayunitClassGet {
	final static Logger logger = LogManager.getLogger(PayunitClassGet.class);

	public static void main(String[] args) {
		String to_account = args[0];	//提供方id
		String id = args[1];			//交易方分类编号
		PayunitClassService ds = new PayunitClassService();
		try {
			JSONObject record = ds.get(id, to_account);
			logger.info(record.toString());
		} catch (OpenAPIException e) {
			e.printStackTrace();
		}
	}
}
