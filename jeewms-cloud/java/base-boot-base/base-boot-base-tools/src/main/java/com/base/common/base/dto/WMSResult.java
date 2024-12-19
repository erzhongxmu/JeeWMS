package com.base.common.base.dto;

import java.util.List;
import java.util.Objects;

public class WMSResult<T> {

    //   是否调用成功
    private Boolean success;

    //  错误码
    private Integer error_code;

    //   错误描述
    private String error_msg;

    //返回参数
    private T data;


    public WMSResult() {
    }

    public WMSResult(Boolean success, Integer error_code) {
        this.success = success;
        this.error_code = error_code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getError_code() {
        return error_code;
    }

    public void setError_code(Integer error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WMSResult{" +
                "success=" + success +
                ", error_code=" + error_code +
                ", error_msg='" + error_msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static WMSResult success(){
        return new WMSResult<>(Boolean.TRUE,200);
    }

    public static WMSResult success(String message){
        WMSResult<Object> result = new WMSResult<>(Boolean.TRUE, 200);
        result.setError_msg(message);
        return result;
    }

    public static WMSResult error(){
        return new WMSResult<>(Boolean.FALSE,500);
    }
    public static WMSResult error(String message){
        WMSResult<Object> result = new WMSResult<>(Boolean.FALSE, 500);
        result.setError_msg(message);
        return result;
    }
}