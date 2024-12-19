package com.base.modules.jeeerp.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;


@Data
@ApiModel(value="chaXunMeiTianDingDan对象", description="")
public class huokuanzhichu {
    @Excel(name = "主PO", width = 15)
    @ApiModelProperty(value = "主PO")
    private String query01;
    @Excel(name = "客户", width = 15)
    @ApiModelProperty(value = "客户")
    private String query02;
    @Excel(name = "公司属性", width = 15)
    @ApiModelProperty(value = "公司属性")
    private String query03;
    @Excel(name = "供应商", width = 15)
    @ApiModelProperty(value = "供应商")
    private String query04;
    @Excel(name = "税点", width = 15)
    @ApiModelProperty(value = "税点")
    private String query05;
    @Excel(name = "含税总金额", width = 15)
    @ApiModelProperty(value = "含税总金额")
    private String query06;
    @Excel(name = "已付金额", width = 15)
    @ApiModelProperty(value = "已付金额")
    private String query07;
    @Excel(name = "余额", width = 15)
    @ApiModelProperty(value = "余额")
    private String query08;
    @Excel(name = "未完成数量", width = 15)
    @ApiModelProperty(value = "未完成数量")
    private String query10;
    @Excel(name = "合同建单日期", width = 15)
    @ApiModelProperty(value = "合同建单日期")
    private String query09;
}
