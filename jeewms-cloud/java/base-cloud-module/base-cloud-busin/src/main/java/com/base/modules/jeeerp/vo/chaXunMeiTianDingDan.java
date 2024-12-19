package com.base.modules.jeeerp.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;


@Data
@ApiModel(value="chaXunMeiTianDingDan对象", description="")
public class chaXunMeiTianDingDan {
    /**采购名*/
    @Excel(name = "采购名", width = 15)
    @ApiModelProperty(value = "采购名")
    private String query01;
    /**业务类型*/
    @Excel(name = "业务类型", width = 15)
    @ApiModelProperty(value = "业务类型")
    private String query02;
    /**样品订单*/
    @Excel(name = "样品订单", width = 15)
    @ApiModelProperty(value = "样品订单")
    private String query03;
    /**大货订单*/
    @Excel(name = "大货订单", width = 15)
    @ApiModelProperty(value = "大货订单")
    private String query04;
    /**统计日期*/
    @Excel(name = "统计日期", width = 15)
    @ApiModelProperty(value = "统计日期")
    private String query05;
}
