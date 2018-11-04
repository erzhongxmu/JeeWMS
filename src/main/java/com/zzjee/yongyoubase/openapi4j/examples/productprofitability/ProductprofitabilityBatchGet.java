package com.zzjee.yongyoubase.openapi4j.examples.productprofitability;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.ProductprofitabilityService;

/**
 * 商业盈利状况评价
 * @author Administrator
 *
 */
public class ProductprofitabilityBatchGet {
	final static Logger logger = LogManager.getLogger(ProductprofitabilityBatchGet.class);

	public static void main(String[] args) {

		//数据提供方id 必填
		String to_account = args[0];
		//页号 非必填
		String page_index = args[1];
		//每页行数 非必填
		String rows_per_page = args[2];
		//年份 非必填
		String year = args[3];
		//月份 非必填
		String month = args[4];
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("to_account", to_account);
		paramMap.put("page_index", page_index);
		paramMap.put("rows_per_page", rows_per_page);
		paramMap.put("year", year);
		paramMap.put("month", month);

		ProductprofitabilityService productprofitabilityService = new ProductprofitabilityService();
		try {
			JSONObject record = productprofitabilityService.batchGet(paramMap);
			logger.info(record.toJSONString());
		} catch (OpenAPIException e) {

			logger.error(e.getMessage());
		}
	}
}
