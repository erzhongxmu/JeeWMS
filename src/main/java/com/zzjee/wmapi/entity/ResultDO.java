//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.zzjee.wmapi.entity;

import java.io.Serializable;

public class ResultDO<T>  implements Serializable {
    private static final long serialVersionUID = -2042618546543630713L;
    private boolean isOK = true;
    private T obj;
    private String errorMsg;
    private String errorCode;

    public ResultDO() {
    }
    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isOK() {
        return this.isOK;
    }

    public void setOK(boolean isOK) {
        this.isOK = isOK;
    }

    public T getObj() {
        return this.obj;
    }

    public ResultDO<T> setObj(T obj) {
        this.obj = obj;
        return this;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
