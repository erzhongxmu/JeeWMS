package com.zzjee.yongyoubase.openapi4j.model;

import com.alibaba.fastjson.JSONObject;

public class Record extends JSONObject {

	public static Record parseRecord(String jsonStr) {
		return (Record) parseObject(jsonStr);
	}

}
