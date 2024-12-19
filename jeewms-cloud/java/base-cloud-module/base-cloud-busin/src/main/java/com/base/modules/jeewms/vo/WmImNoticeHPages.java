
package com.base.modules.jeewms.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(value="wm_im_notice_hPages对象", description="wm_im_notice_h")
public class WmImNoticeHPages{


    @Excel(name = "客户编码", width = 15 ,orderNum = "6")
    @ApiModelProperty(value = "客户编码")
    private String cusCode;

    @Excel(name = "客户名称", width = 15     ,orderNum = "5")
    @ApiModelProperty(value = "客户")
    private String cusName;

    @Excel(name = "预计到货时间", width = 15, format = "yyyy-MM-dd"      ,orderNum = "4")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "预计到货时间")
    private Date imData;

    /**订单类型*/
    @Excel(name = "订单类型", width = 15,dicCode = "im_order_type"      ,orderNum = "3")
    @ApiModelProperty(value = "订单类型")
    private String orderType;

    @Excel(name = "主PO号", width = 15 ,orderNum = "2")
    @ApiModelProperty(value = "主PO号")
    private String imBeizhu;

    /**进货通知单号*/
    @Excel(name = "进货通知单号", width = 15      ,orderNum = "1")
    @ApiModelProperty(value = "进货通知单号")
    private String noticeId;

    /**供应商编码*/
    @Excel(name = "供应商编码", width = 15)
    @ApiModelProperty(value = "供应商编码")
    private String supCode;


    @Excel(name = "销售单号", width = 15     ,orderNum = "0")
    private String u8ReturnCode;


    @Excel(name = "采购", width = 15)
    private String purchasename;


    @Excel(name = "内部发票号", width = 15)
    private String astreanum;


    @Excel(name = "商品编码", width = 15,orderNum = "1")
    @ApiModelProperty(value = "商品编码")
    private String goodsCode;

    /**数量*/
    @Excel(name = "数量", width = 15,orderNum = "2")
    @ApiModelProperty(value = "数量")
    private String goodsCount;



    /**批次*/
    @Excel(name = "批次", width = 15,orderNum = "8")
    @ApiModelProperty(value = "批次")
    private String goodsBatch;


    /**单位*/
    @Excel(name = "单位", width = 15,orderNum = "3")
    @ApiModelProperty(value = "单位")
    private String goodsUnit;

    /**收货登记数量*/
    @Excel(name = "收货登记数量", width = 15,orderNum = "4")
    @ApiModelProperty(value = "收货登记数量")
    private String goodsQmCount;

    /**goodsName*/
    @Excel(name = "商品名称", width = 15,orderNum = "0")
    @ApiModelProperty(value = "goodsName")
    private String goodsName;


    @Excel(name = "单价", width = 15,orderNum = "3")
    @ApiModelProperty("采购单价")
    private BigDecimal unitPrice;

    @Excel(name = "子PO号", width = 15,orderNum = "3")
    @ApiModelProperty(value = "子PO号")
    private String contractlno;

    @Excel(name = "验货类型", width = 15)
    private String totalamountvat;

    //出库日期
    @Excel(name = "发货日期", width = 15)
    @ApiModelProperty(value = "出库日期")
    private String chukudate;

    @Excel(name = "中文名称", width = 15)
    private String ywMingCheng;

    @Excel(name = "备注", width = 15)
    private String ImBeizhui;

}
