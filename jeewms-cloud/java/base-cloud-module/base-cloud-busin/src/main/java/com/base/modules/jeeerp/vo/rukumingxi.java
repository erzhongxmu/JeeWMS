package com.base.modules.jeeerp.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;


@Data
@ApiModel(value="chaXunMeiTianDingDan对象", description="")
public class rukumingxi {
    @Excel(name = "子PO号", width = 15)
    @ApiModelProperty(value = "子PO号")
    private String query01;
    @Excel(name = "客户名称", width = 15)
    @ApiModelProperty(value = "客户名称")
    private String query02;
    @Excel(name = "通知单状态", width = 15)
    @ApiModelProperty(value = "通知单状态")
    private String query03;
    @Excel(name = "商品编码", width = 15)
    @ApiModelProperty(value = "商品编码")
    private String query04;
    @Excel(name = "商品名称", width = 15)
    @ApiModelProperty(value = "商品名称")
    private String query05;
    @Excel(name = "通知单数量", width = 15)
    @ApiModelProperty(value = "通知单数量")
    private String query06;
    @Excel(name = "验收数量", width = 15)
    @ApiModelProperty(value = "验收数量")
    private String query07;
    @Excel(name = "单位", width = 15)
    @ApiModelProperty(value = "单位")
    private String query08;
    @Excel(name = "通知单号", width = 15)
    @ApiModelProperty(value = "通知单号")
    private String query09;
    @Excel(name = "入库时间", width = 15)
    @ApiModelProperty(value = "入库时间")
    private String query10;
}
