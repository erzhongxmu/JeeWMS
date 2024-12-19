package com.base.common.base.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author JiWang
 * @PROJECT zdwms
 * @Description
 * @Date 2021/10/21 20:32
 * 京东申报运单参数实体
 **/
@Data
@Builder
public class JdDeclareOrderDTO {

    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 申报类型，1-新增; 2-变更并重推;3-重推
     */
    private String type;
    /**
     * 物流企业的运单包裹面单号
     */
    private String logisticsNo;
    /**
     * 货物运输费用
     */
    private BigDecimal freight;
    /**
     * 货物保险费用
     */
    private BigDecimal insuredFee;
    /**
     * 净重（kg）
     */
    private BigDecimal netWeight;
    /**
     * 毛重（kg）
     */
    private BigDecimal weight;
    /**
     * 价值（kg）
     */
    private BigDecimal worth;
    /**
     * 单个运单下包裹数 件数(包裹数,固定传1)
     */
    private Integer packNo;
    /**
     * 主要商品名称
     */
    private String goodsName;
    /**
     * 发货人信息
     */
    private String senderName;
    /**
     * 发货人地址
     */
    private String sendAddress;
    /**
     * 发货人电话
     */
    private String sendPhone;
    /**
     * 收货人姓名
     */
    private String consignee;
    /**
     * 收货人县或区
     */
    private String district;
    /**
     * 收货人地址（省）
     */
    private String receiveProvince;
    /**
     * 收货人地址（市）
     */
    private String receiveCity;
    /**
     * 收货人地址
     */
    private String consigneeAddress;
    /**
     * 收货人电话
     */
    private String consigneePhone;
}
