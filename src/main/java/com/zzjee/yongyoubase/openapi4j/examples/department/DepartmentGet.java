package com.zzjee.yongyoubase.openapi4j.examples.department;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.DepartmentService;

public class DepartmentGet {
	final static Logger logger = LogManager.getLogger(DepartmentGet.class);

	public static void main(String[] args) {
		//数据提供方id
		String to_account = args[0];
		//部门编码
		String id = args[1];
		DepartmentService departmentService = new DepartmentService();
		try {
			JSONObject record = departmentService.get(id, to_account);
			logger.info(record.toString());
		} catch (OpenAPIException e) {
			e.printStackTrace();
		}
	}
}
