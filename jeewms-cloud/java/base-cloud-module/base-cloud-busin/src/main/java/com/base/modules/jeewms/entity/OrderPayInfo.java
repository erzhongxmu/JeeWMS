package com.base.modules.jeewms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * WMS 订单推送支付信息模型(OrderPayInfo)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayInfo implements Serializable {
    private String	payment_id			;	//支付交易号 支付公司提供
    private String	payment_company_name;	//支付企业名称
    private String	platform_domain		;	//电商平台域名
    private String	platform_id			;	//电商平台编号
    private String	platform_name		;	//电商平台名称
    private BigDecimal pay_amount			;	//支付金额 单位：分 币种：通常为人民币
    private String	pay_currency_code	;	//支付币制代码
    private BigDecimal		pay_rmb_amount		;	//人民币金额 单位：分

}
