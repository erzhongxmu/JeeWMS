package com.zzjee.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.io.Serializable;

/**
 *   接口返回数据格式
 * @author base-boot
 * @email baseos@163.com
 * @date  2019年1月19日
 */

@ApiModel(value="接口返回对象", description="接口返回对象")
public class ResultApi<T> implements Serializable {


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getOnlTable() {
		return onlTable;
	}

	public void setOnlTable(String onlTable) {
		this.onlTable = onlTable;
	}

	private static final long serialVersionUID = 1L;

	/**
	 * 成功标志
	 */
	@ApiModelProperty(value = "成功标志")
	private boolean success = true;

	/**
	 * 返回处理消息
	 */
	@ApiModelProperty(value = "返回处理消息")
	private String message = "操作成功！";

	/**
	 * 返回代码
	 */
	@ApiModelProperty(value = "返回代码")
	private Integer code = 0;

	/**
	 * 返回数据对象 data
	 */
	@ApiModelProperty(value = "返回数据对象")
	private T result;

	/**
	 * 时间戳
	 */
	@ApiModelProperty(value = "时间戳")
	private long timestamp = System.currentTimeMillis();

	public ResultApi() {

	}

	public ResultApi<T> success(String message) {
		this.message = message;
		this.code = CommonConstant.SC_OK_200;
		this.success = true;
		return this;
	}

	@Deprecated
	public static ResultApi<Object> ok() {
		ResultApi<Object> r = new ResultApi<Object>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setMessage("成功");
		return r;
	}

	@Deprecated
	public static ResultApi<Object> ok(String msg) {
		ResultApi<Object> r = new ResultApi<Object>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setMessage(msg);
		return r;
	}

	@Deprecated
	public static ResultApi<Object> ok(Object data) {
		ResultApi<Object> r = new ResultApi<Object>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setResult(data);
		return r;
	}

	public static<T> ResultApi<T> OK() {
		ResultApi<T> r = new ResultApi<T>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setMessage("成功");
		return r;
	}

	public static<T> ResultApi<T> OK(T data) {
		ResultApi<T> r = new ResultApi<T>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setResult(data);
		return r;
	}

	public static<T> ResultApi<T> OK(String msg, T data) {
		ResultApi<T> r = new ResultApi<T>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setMessage(msg);
		r.setResult(data);
		return r;
	}

	public static<T> ResultApi<T> error(String msg, T data) {
		ResultApi<T> r = new ResultApi<T>();
		r.setSuccess(false);
		r.setCode(CommonConstant.SC_INTERNAL_SERVER_ERROR_500);
		r.setMessage(msg);
		r.setResult(data);
		return r;
	}

	public static ResultApi<Object> error(String msg) {
		return error(CommonConstant.SC_INTERNAL_SERVER_ERROR_500, msg);
	}

	public static ResultApi<Object> error(int code, String msg) {
		ResultApi<Object> r = new ResultApi<Object>();
		r.setCode(code);
		r.setMessage(msg);
		r.setSuccess(false);
		return r;
	}

	public ResultApi<T> error500(String message) {
		this.message = message;
		this.code = CommonConstant.SC_INTERNAL_SERVER_ERROR_500;
		this.success = false;
		return this;
	}
	/**
	 * 无权限访问返回结果
	 */
	public static ResultApi<Object> noauth(String msg) {
		return error(CommonConstant.SC_BASE_NO_AUTHZ, msg);
	}

	@JsonIgnore
	private String onlTable;

}
