package com.base.common.base.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CancelOrderInfo {

    /**
     * 在cds配置的名称
     */
    private String	companyName;

    /**
     * 订单编号
     */
    private String	orderId;

    /**
     * 取消原因
     */
    private String	reason;
}
