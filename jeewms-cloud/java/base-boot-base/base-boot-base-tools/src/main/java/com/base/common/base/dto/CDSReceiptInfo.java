package com.base.common.base.dto;

import lombok.Data;

@Data
public class CDSReceiptInfo {

    /**
     * 订单编号
     */
    private String	orderId;

    /**
     * 清单编号
     */
    private String	invtNo;

    /**
     * 海关总署清关状态码
     */
    private String	customsStatus;

    /**
     * 国检清关状态码
     * 非必须
     */
    private String	nationStatus;

    /**
     * 总署清清单回执时间格式为yyyyMMddhhmmssSSS
     */
    private String	returnTime;

    /**
     * 总署回执描述
     */
    private String	customsRemark;

    /**
     * 国检回执描述
     * 非必须
     */
    private String	nationRemark;
}
