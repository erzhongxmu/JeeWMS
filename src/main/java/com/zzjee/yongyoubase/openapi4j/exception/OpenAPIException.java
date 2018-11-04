package com.zzjee.yongyoubase.openapi4j.exception;

import com.alibaba.fastjson.JSONObject;

public class OpenAPIException extends Exception {
	private String errcode;
	private String errmsg;

	public OpenAPIException(JSONObject jo) {
		super("\n error:" + jo.getString("errmsg") + " error_code:" + jo.getString("errcode"));
		this.errcode = jo.getString("errcode");
		this.errmsg = jo.getString("errmsg");
	}

	public OpenAPIException(String msg, Exception cause) {
		super(msg, cause);
	}

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}
