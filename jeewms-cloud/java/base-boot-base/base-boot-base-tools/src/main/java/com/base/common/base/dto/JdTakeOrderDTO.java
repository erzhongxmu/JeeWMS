package com.base.common.base.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Author JiWang
 * @PROJECT zdwms
 * @Description
 * @Date 2021/10/21 20:17
 * 京东下单参数实体
 **/
@Data
@Builder
public class JdTakeOrderDTO {

    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 寄件人姓名
     */
    private String senderName;
    /**
     * 寄件人地址
     */
    private String sendAddress;
    /**
     * 寄件人电话
     */
    private String sendPhone;
    /**
     * 收件人姓名
     */
    private String receiveName;
    /**
     * 收件人地址
     */
    private String receiveAddress;
    /**
     * 收件人省份
     */
    private String receiveProvince;
    /**
     * 收件人市
     */
    private String receiveCity;
    /**
     * 收件人电话
     */
    private String receivePhone;
    /**
     * 包裹数（大于0，小于1000）
     */
    private Integer packageCount;
    /**
     * 订单总重量(单位：kg，保留小数点后两位)
     */
    private BigDecimal weight;
    /**
     * 体积(单位：cm3，保留小数点后两位)
     */
    private BigDecimal volumn;
    /**
     * 商品描述
     */
    private String description;
}
