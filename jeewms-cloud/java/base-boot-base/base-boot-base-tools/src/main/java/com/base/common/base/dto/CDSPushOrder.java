package com.base.common.base.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CDSPushOrder {

    /**
     * 在cds配置的名称
     */
    private String companyName;

    /**
     * 订单编号
     */
    private String orderId;

    /**
     * 申报订单号
     */
    private String inOrderNo;

    /**
     * 净重
     */
    private String netWeight;

    /**
     * 毛重
     */
    private String grossWeight;

    /**
     * 订购人所在平台注册号
     */
    private String purchaserAcount;

    /**
     * 订购人姓名
     */
    private String buyerName;

    /**
     * 订购人身份证号
     */
    private String receiverId;

    /**
     * 订购人电话
     */
    private String purchaserTel;

    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 收货人电话
     */
    private String receiverMobile;

    /**
     * 收货人地址
     */
    private String receiverAddress;

    /**
     * 收货人邮编
     * 非必须
     */
    private String receiverZip;

    /**
     * 物流企业编码
     */
    private String transportServiceCode;

    /**
     * 物流运单编号
     */
    private String transportOrderId;

    /**
     * 订单货款
     */
    private String orderGoodsAmount;

    /**
     * 订单创建时间
     */
    private String orderCreateTime;

    /**
     * 是否保价
     */
    private String needInsuredFee;

    /**
     * 备注
     * 非必须
     */
    private String note;

    /**
     * 收货人省
     */
    private String consigneeProv;

    /**
     * 收货人市
     */
    private String consigneeCity;

    /**
     * 收货人区县
     */
    private String consigneeCounty;

    /**
     * 收货人镇
     * 非必须
     */
    private String consigneeTown;

    /**
     * 收货人详细地址
     */
    private String consigneeDetailedAddress;

    /**
     * 费用信息
     */
    private OrderFeeInfo feeInfo;

    /**
     * 支付相关信息
     */
    private OrderPayInfo payInfo;

    /**
     * 商品明细
     */
    private List<OrderItem> orderItems;
}
