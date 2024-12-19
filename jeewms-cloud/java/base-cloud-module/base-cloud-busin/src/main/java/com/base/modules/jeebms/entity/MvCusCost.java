package com.base.modules.jeebms.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import com.base.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 客户账单
 * @Author: base-boot
 * @Date:   2021-12-25
 * @Version: V1.0
 */
@Data
@TableName("mv_cus_cost")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="mv_cus_cost对象", description="客户账单")
public class MvCusCost implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**客户编码*/
	@Excel(name = "客户编码", width = 15)
    @ApiModelProperty(value = "客户编码")
    private java.lang.String cusCode;
	/**中文全称*/
	@Excel(name = "中文全称", width = 15)
    @ApiModelProperty(value = "中文全称")
    private java.lang.String cusName;
	/**costData*/
	@Excel(name = "costData", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "costData")
    private java.util.Date costData;
}
