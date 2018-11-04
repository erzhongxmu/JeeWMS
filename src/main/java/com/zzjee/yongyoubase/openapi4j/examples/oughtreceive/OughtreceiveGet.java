package com.zzjee.yongyoubase.openapi4j.examples.oughtreceive;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.OughtreceiveService;

/**
 * 批量获取应收单信息列表
 * @author Administrator
 *
 */
public class OughtreceiveGet {
	final static Logger logger = LogManager.getLogger(OughtreceiveGet.class);

	public static void main(String[] args) {
		
		//数据提供方id
		String to_account = args[0];
       //应收单编号
		String id = args[1];

		OughtreceiveService oughtreceiveService = new OughtreceiveService();
		try {
			JSONObject record = oughtreceiveService.get(id, to_account);
			logger.info(record.toString());
		} catch (OpenAPIException e) {
			e.printStackTrace();
		}
	}
}
