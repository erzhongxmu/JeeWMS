package com.zzjee.yongyoubase.openapi4j.examples.oughtreceive;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.OughtreceiveService;

/**
 * 通过上游主键新增应收单信息
 * @author Administrator
 *
 */
public class OughtreceiveAddWithBizId {
	final static Logger logger = LogManager.getLogger(OughtreceiveAddWithBizId.class);

	public static void main(String[] args) {
		 String to_account = args[0];
		String bizId = args[1];
		 String jsonBody = args[2];
		
		OughtreceiveService oughtreceiveService = new OughtreceiveService();
		try {

			// 通过上游主键新增
			JSONObject record = oughtreceiveService.addByBizId(bizId, jsonBody, to_account);
			logger.info(record.toString());
		} catch (OpenAPIException e) {

			e.printStackTrace();
		}
	}
}
