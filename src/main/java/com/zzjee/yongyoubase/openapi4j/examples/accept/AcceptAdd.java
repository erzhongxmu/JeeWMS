package com.zzjee.yongyoubase.openapi4j.examples.accept;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.AcceptService;
/**
 * 新增收款单
 * @author Administrator
 *
 */
public class AcceptAdd {
	final static Logger logger = LogManager.getLogger(AcceptAdd.class);

	public static void main(String[] args) {
		 String to_account = args[0];
		 String jsonBody = args[1];
		
		          AcceptService acceptService = new AcceptService();
		try {
			// 通过平台交易号新增
			JSONObject record = acceptService.add(jsonBody, to_account);

		} catch (OpenAPIException e) {

			e.printStackTrace();
		}
	}
}
