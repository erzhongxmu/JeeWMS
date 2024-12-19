package com.base.modules.jeewms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: wv_day_cost_sum
 * @Author: base-boot
 * @Date:   2021-10-21
 * @Version: V1.0
 */
@Data
@TableName("wv_day_cost_sum")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="wv_day_cost_sum对象", description="wv_day_cost_sum")
public class WvDayCostSum implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**费用日期*/
	@Excel(name = "费用日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "费用日期")
    private java.util.Date costData;
	/**客户*/
	@Excel(name = "客户", width = 15)
    @ApiModelProperty(value = "客户")
    private java.lang.String cusCode;
	/**客户名称*/
	@Excel(name = "客户名称", width = 15)
    @ApiModelProperty(value = "客户名称")
    private java.lang.String cusName;
	/**费用*/
	@Excel(name = "费用", width = 15)
    @ApiModelProperty(value = "费用")
    private java.lang.String costCode;
	/**费用名称*/
	@Excel(name = "费用名称", width = 15)
    @ApiModelProperty(value = "费用名称")
    private java.lang.String costName;
	/**是否已结算*/
	@Excel(name = "是否已结算", width = 15)
    @ApiModelProperty(value = "是否已结算")
    private java.lang.String costJs;
	/**原价*/
	@Excel(name = "原价", width = 15)
    @ApiModelProperty(value = "原价")
    private java.lang.Double yuanj;
	/**不含税*/
	@Excel(name = "不含税", width = 15)
    @ApiModelProperty(value = "不含税")
    private java.lang.Double bhsj;
	/**税*/
	@Excel(name = "税", width = 15)
    @ApiModelProperty(value = "税")
    private java.lang.Double shuie;
	/**是否结算*/
	@Excel(name = "是否结算", width = 15)
    @ApiModelProperty(value = "是否结算")
    private java.lang.Double costSl;
	/**含税价*/
	@Excel(name = "含税价", width = 15)
    @ApiModelProperty(value = "含税价")
    private java.lang.Double hansj;
	/**数量单位*/
	@Excel(name = "数量单位", width = 15)
    @ApiModelProperty(value = "数量单位")
    private java.lang.String costUnit;
}
