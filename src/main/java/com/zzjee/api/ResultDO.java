//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.zzjee.api;
/**
 * @Title: Controller
 * @Description: 出货通知
 * @author erzhongxmu
 * @date 2017-08-15 23:18:59
 * @version V1.0
 *
 */
import java.io.Serializable;

public class ResultDO<T>  implements Serializable {
    private static final long serialVersionUID = -2042618546543630713L;
    private boolean isOK = true;
    private T obj;
    /**
     * 错误消息
     */
    private String errorMsg;
    /**
     * 错误代码
     */
    private String errorCode;

    public ResultDO() {
    }

    /**
     * 返回错误编码
     * @return 错误编码
     */
    public String getErrorCode() {
        return this.errorCode;
    }

    /**
     * 设置错误编码
     * @param errorCode 错误编码
     */
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
