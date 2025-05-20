package com.zzjee.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 消息主体
 *
 * @author admin
 * @date 2016/10/31
 */
public class AnnouncementSendModel {
    /**
     * 发布时间
     */
    private java.lang.String startTime;
    /**
     * 标题
     */
    private java.lang.String titile;
    /**
     * 内容
     */
    private java.lang.String msgContent;
    /**
     * 摘要
     */
    private java.lang.String msgAbstract;

    /**
     * 获取发布时间
     *
     * @return
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * 设置发布时间
     *
     * @param startTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取标题
     *
     * @return
     */
    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgAbstract() {
        return msgAbstract;
    }

    public void setMsgAbstract(String msgAbstract) {
        this.msgAbstract = msgAbstract;
    }
}
