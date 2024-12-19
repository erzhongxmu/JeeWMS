package com.base.modules.jeewms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * wms: 订单推送费用对象模型(OrderFeeInfo)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderFeeInfo implements Serializable {
    private BigDecimal tax					;	//订单对应产生的税费 币种: 人民币 单位:分
    private BigDecimal	freight				;	//运费 币种: 人民币 单位:分
    private String	insurance			;	//保费 币种: 人民币 单位:分
    private BigDecimal	deduction_amount	;	//购抵扣金额 币种: 人民币 单位:分
    private String	deduction_note		;	//抵扣金额说明抵扣金额为0时填”无”，String类型

}
