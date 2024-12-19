package com.base.modules.jeeerp.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;


@Data
@ApiModel(value="chaXunMeiTianDingDan对象", description="")
public class XiangChuKuXiangQing {

    /**装箱单号*/
    @Excel(name = "装箱单号", width = 15)
    @ApiModelProperty(value = "装箱单号")
    private String query01;
    /**子PO号*/
    @Excel(name = "子PO号", width = 15)
    @ApiModelProperty(value = "子PO号")
    private String query02;
    /**客户名*/
    @Excel(name = "客户名", width = 15)
    @ApiModelProperty(value = "客户名")
    private String query03;
    /**商品编码*/
    @Excel(name = "商品编码", width = 15)
    @ApiModelProperty(value = "商品编码")
    private String query04;
    /**商品名称*/
    @Excel(name = "商品名称", width = 15)
    @ApiModelProperty(value = "商品名称")
    private String query05;
    /**托盘*/
    @Excel(name = "托盘", width = 15)
    @ApiModelProperty(value = "托盘")
    private String query06;
    /**数量*/
    @Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
    private String query07;
    /**单位*/
    @Excel(name = "单位", width = 15)
    @ApiModelProperty(value = "单位")
    private String query08;
    /**箱子规格*/
    @Excel(name = "箱子规格", width = 15)
    @ApiModelProperty(value = "箱子规格")
    private String query09;
    /**重量*/
    @Excel(name = "重量", width = 15)
    @ApiModelProperty(value = "重量")
    private String query10;
    /**出库时间*/
    @Excel(name = "出库时间", width = 15)
    @ApiModelProperty(value = "出库时间")
    private String query11;
    /**出库通知单号*/
    @Excel(name = "出库通知单号", width = 15)
    @ApiModelProperty(value = "出库通知单号")
    private String query12;
}
