package com.zzjee.yongyoubase.openapi4j.examples.oughtreceive;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.OughtreceiveService;

/**
 * 应收单信息新增
 * @author Administrator
 *
 */
public class OughtreceiveAdd {
	final static Logger logger = LogManager.getLogger(OughtreceiveAdd.class);

	public static void main(String[] args) {
		 String to_account = args[0];
		 String jsonBody = args[1];
		
		          OughtreceiveService oughtreceiveService = new OughtreceiveService();
		try {
			// 通过平台交易号新增应收单信息
			JSONObject record = oughtreceiveService.add(jsonBody, to_account);

		} catch (OpenAPIException e) {

			e.printStackTrace();
		}
	}
}
