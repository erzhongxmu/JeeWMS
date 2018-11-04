package com.zzjee.yongyoubase.openapi4j.examples.accept;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.AcceptService;

/**
 * 通过上游主键新增收款单
 * @author Administrator
 *
 */
public class AcceptAddWithBizId {
	final static Logger logger = LogManager.getLogger(AcceptAddWithBizId.class);

	public static void main(String[] args) {
		 String to_account = args[0];
		String bizId = args[1];
		 String jsonBody = args[2];
		
		 AcceptService acceptService = new AcceptService();
		try {

			// 通过自己的交易号新增
			JSONObject record = acceptService.addByBizId(bizId, jsonBody, to_account);
			logger.info(record.toString());
		} catch (OpenAPIException e) {

			e.printStackTrace();
		}
	}
}
