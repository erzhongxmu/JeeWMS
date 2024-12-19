package com.base.modules.jeewms.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户订单推送接口(电商平台（货主） -> WMS服务商)    请求参数
 */
@Data
@TableName("wms_user_order_push_data")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "wms_user_order_push_data对象", description = "推送wms数据")
public class WMSUserOrderPushData implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String orderId;
    /**
     * 支付单号
     */
    @ApiModelProperty(value = "支付单号")
    private String paymentId;
    /**
     * 支付方式 电商平台（货主）宝跨境,支付宝跨境 etc的int值
     */
    @ApiModelProperty(value = "支付方式 电商平台（货主）宝跨境,支付宝跨境 etc的int值")
    private Integer payMethodId;
    /**
     * 购买人帐号
     */
    @ApiModelProperty(value = "购买人帐号")
    private String purchaserAcount;
    /**
     * 购买人姓名
     */
    @ApiModelProperty(value = "购买人姓名")
    private String buyerName;
    /**
     * 购买人联系方式
     */
    @ApiModelProperty(value = "购买人联系方式")
    private String purchaserTel;
    /**
     * 购买人地址
     */
    @ApiModelProperty(value = "购买人地址")
    private String purchaserAddress;
    /**
     * 使用的优惠券金额 单位：分 币种：人民币
     */
    @ApiModelProperty(value = "使用的优惠券金额 单位：分 币种：人民币")
    private BigDecimal couponAmount;
    /**
     * 商品总件数
     */
    @ApiModelProperty(value = "商品总件数")
    private Integer totalCount;
    /**
     * 下单时间 格式：yyyy-MM-dd HH:mm:ss
     */
    @ApiModelProperty(value = "下单时间 格式：yyyy-MM-dd HH:mm:ss")
    private String orderCreateTime;
    /**
     * 支付时间 格式：yyyy-MM-dd HH:mm:ss
     */
    @ApiModelProperty(value = "支付时间 格式：yyyy-MM-dd HH:mm:ss")
    private String paytime;
    /**
     * 第三方在网仓注册的店铺ID
     */
    @ApiModelProperty(value = "第三方在网仓注册的店铺ID")
    private String shopId;
    /**
     * 物流公司编码 SF，ZTO，EMS等 见物流公司编码表
     */
    @ApiModelProperty(value = "物流公司编码 SF，ZTO，EMS等 见物流公司编码表")
    private String transportServiceCode;
    /**
     * 物流单号
     */
    @ApiModelProperty(value = "物流单号")
    private String transportOrderId;
    /**
     * 物流企业id，用于与mm对接的同步物流状态接口
     */
    @ApiModelProperty(value = "物流企业id，用于与mm对接的同步物流状态接口")
    private Integer logisCompanyId;
    /**
     * RSA加密的身份证号
     */
    @ApiModelProperty(value = "RSA加密的身份证号")
    private String receiverId;
    /**
     * 加密的身份证正反面照片URL
     */
    @ApiModelProperty(value = "加密的身份证正反面照片URL")
    private List<String> idPics;
    /**
     * 收件人邮编
     */
    @ApiModelProperty(value = "收件人邮编")
    private String receiverZip;
    /**
     * 收件人省份
     */
    @ApiModelProperty(value = "收件人省份")
    private String receiverProvince;
    /**
     * 收件人城市
     */
    @ApiModelProperty(value = "收件人城市")
    private String receiverCity;
    /**
     * 收件人县区
     */
    @ApiModelProperty(value = "收件人县区")
    private String receiverCounty;
    /**
     * 收件人地址
     */
    @ApiModelProperty(value = "收件人地址")
    private String receiverAddress;
    /**
     * 收件人姓名
     */
    @ApiModelProperty(value = "收件人姓名")
    private String receiverName;
    /**
     * 收件人手机
     */
    @ApiModelProperty(value = "收件人手机")
    private String receiverMobile;
    /**
     * 收件人固定电话
     */
    @ApiModelProperty(value = "收件人固定电话")
    private String receiverPhone;
    /**
     * 快递公司大头笔
     */
    @ApiModelProperty(value = "快递公司大头笔")
    private String logisticMark;
    /**
     * 渠道信息
     */
    @ApiModelProperty(value = "渠道信息")
    private Integer ownerType;
    /**
     * 渠道Id
     */
    @ApiModelProperty(value = "渠道Id")
    private String channelId;
    /**
     * 渠道名称
     */
    @ApiModelProperty(value = "渠道名称")
    private String channelName;
    /**
     * 主商品名称
     */
    @ApiModelProperty(value = "主商品名称")
    private String mainGoodsName;
    /**
     * 包装类型 0：海淘 1：中性 2：拼团 3：考拉工厂店
     */
    @ApiModelProperty(value = "包装类型 0：海淘 1：中性 2：拼团 3：考拉工厂店")
    private Integer neutralPackage;
    /**
     * 订单备注 目前为预留字段
     */
    @ApiModelProperty(value = "订单备注 目前为预留字段")
    private String remark;
    /**
     * 个人物品申报单预录入号码
     */
    @ApiModelProperty(value = "个人物品申报单预录入号码")
    private String goodsDelcareId;
    /**
     * 杭州或郑州海关放行方式
     */
    @ApiModelProperty(value = "杭州或郑州海关放行方式")
    private String customsReleaseMethod;
    /**
     * 订单货款 单位：分 币种：人民币 杭州秀品保税仓使用
     */
    @ApiModelProperty(value = "订单货款 单位：分 币种：人民币 杭州秀品保税仓使用")
    private BigDecimal orderGoodsAmount;
    /**
     * 订单实付金额 单位：分 币种：人民币 杭州秀品保税仓使用
     */
    @ApiModelProperty(value = "订单实付金额 单位：分 币种：人民币 杭州秀品保税仓使用")
    private BigDecimal orderTotalAmount;
    /**
     * 订单税款 单位：分 币种：人民币 杭州秀品保税仓使用
     */
    @ApiModelProperty(value = "订单税款 单位：分 币种：人民币 杭州秀品保税仓使用")
    private BigDecimal orderTaxAmount;
    /**
     * 物流标记，0：普通 1：次日达标记
     */
    @ApiModelProperty(value = " 物流标记，0：普通 1：次日达标记")
    private Integer logisticCondition;
    /**
     * 自选物流标记，0：没有自选物流，1：有自选物流
     */
    @ApiModelProperty(value = "自选物流标记，0：没有自选物流，1：有自选物流")
    private Integer logisticType;
    /**
     * 支付相关信息 个人物品清关时必填
     */
    @ApiModelProperty(value = "支付相关信息 个人物品清关时必填")
//    private OrderPayInfo payInfo;
    private String payInfo;
    /**
     * 净重（ 理论重量） 单位：克
     */
    @ApiModelProperty(value = "净重（ 理论重量） 单位：克")
    private Integer netWeight;
    /**
     * 商品明细
     */
    @ApiModelProperty(value = "商品明细")
//    private List<OrderItem> orderItems;
    private String orderItems;
    /**
     * 包材推荐信息
     */
    @ApiModelProperty(value = "包材推荐信息")
//    private List<OrderPackMatsDetail> packMats;
    private String packMats;
    /**
     * 进口类型
     */
    @ApiModelProperty(value = "进口类型")
    private Integer importType;
    /**
     * 费用信息
     */
    @ApiModelProperty(value = "费用信息")
//    private OrderFeeInfo feeInfo;
    private String feeInfo;
    /**
     * 清关口岸代码
     */
    @ApiModelProperty(value = "清关口岸代码")
    private String declPort;
    /**
     * 发票数据
     */
    @ApiModelProperty(value = "发票数据")
//    private OrderInvoiceFlag invoiceFlags;
    private String invoiceFlags;
    /**
     * 商家id
     */
    @ApiModelProperty(value = "商家id")
    private Integer merchantId;
    /**
     * 给中外运使用的, value="netease", 中外运使用该字段+stock_id来标识客户
     */
    @ApiModelProperty(value = "给中外运使用的, value=\"netease\", 中外运使用该字段+stock_id来标识客户")
    private String eStorageNo;
    /**
     * 币种编码, 中外运专用字段
     */
    @ApiModelProperty(value = "币种编码, 中外运专用字段")
    private String bargainCurrency;
    /**
     * 中外运专用
     */
    @ApiModelProperty(value = "中外运专用")
    private String supplyChannel;
    /**
     * 溯源码值
     */
    @ApiModelProperty(value = "溯源码值")
    private String sourceValue;
    /**
     * 溯源码图值
     */
    @ApiModelProperty(value = "溯源码图值")
    private String sourceGraphValue;
    /**
     * 应发货时间
     */
    @ApiModelProperty(value = "应发货时间")
    private String lastOutTime;
    /**
     * 应揽收时间
     */
    @ApiModelProperty(value = "应揽收时间")
    private String orderCollectTime;

}


