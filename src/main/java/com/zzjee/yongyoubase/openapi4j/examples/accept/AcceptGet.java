package com.zzjee.yongyoubase.openapi4j.examples.accept;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.AcceptService;

/**
 * 批量获取收款单
 * @author Administrator
 *
 */
public class AcceptGet {
	final static Logger logger = LogManager.getLogger(AcceptGet.class);

	public static void main(String[] args) {
		//数据提供方id
		String to_account = args[0];
       //收款单编号
		String id = args[1];

		AcceptService acceptService = new AcceptService();
		try {
			JSONObject record = acceptService.get(id, to_account);
			logger.info(record.toString());
		} catch (OpenAPIException e) {
			e.printStackTrace();
		}
	}
}
