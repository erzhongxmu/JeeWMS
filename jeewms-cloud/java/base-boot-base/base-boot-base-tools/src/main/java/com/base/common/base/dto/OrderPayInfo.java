package com.base.common.base.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderPayInfo {

    /**
     * 实际支付金额
     */
    private BigDecimal pay_amount;

    /**
     * 支付企业海关注册编号
     * 非必须
     */
    private String	payment_company_code;

    /**
     * 支付企业海关注册名称
     * 非必须
     */
    private String	payment_company_name;

    /**
     * 支付交易号
     */
    private String	payment_id;
}
