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
 * @Description: bms_bill_i
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Data
@TableName("bms_bill_i")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="bms_bill_i对象", description="账单详情")
public class BmsBillI implements Serializable {
    private static final long serialVersionUID = 1L;

    /**主键*/
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
    /**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
    /**创建日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
    /**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
    /**更新日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    //	 @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
    /**所属部门*/
    //	@ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
    /**逻辑删除*/
    //@Excel(name = "逻辑删除", width = 15)
    //	 @ApiModelProperty(value = "逻辑删除")
    private java.lang.Integer isDel;
    /**备用1*/
//	//	@Excel(name = "备用1", width = 15)
//   //	@ApiModelProperty(value = "备用1")
    private java.lang.String attr1;
    /**备用2*/
    //	@Excel(name = "备用2", width = 15)
    //	@ApiModelProperty(value = "备用2")
    private java.lang.String attr2;
    /**备用3*/
    //	@Excel(name = "备用3", width = 15)
    //	@ApiModelProperty(value = "备用3")
    private java.lang.String attr3;
    /**备用4*/
    //	@Excel(name = "备用4", width = 15)
    //	@ApiModelProperty(value = "备用4")
    private java.lang.String attr4;
    /**备用5*/
    //	@Excel(name = "备用5", width = 15)
    //	@ApiModelProperty(value = "备用5")
    private java.lang.String attr5;
    /**租户字段*/
    //	 //@Excel(name = "租户字段", width = 15))
    //	 @ApiModelProperty(value = "租户字段")
    private java.lang.String tenantId;
    /**账单编号*/
    @ApiModelProperty(value = "账单编号")
    private java.lang.String billNo;
    /**费用编号*/
    @Excel(name = "费用编号", width = 15)
    @ApiModelProperty(value = "费用编号")
    private java.lang.String costNo;
    /**费用名称*/
    @Excel(name = "费用名称", width = 15)
    @ApiModelProperty(value = "费用名称")
    private java.lang.String costName;
    /**费用描述*/
    @Excel(name = "费用描述", width = 15)
    @ApiModelProperty(value = "费用描述")
    private java.lang.String costDesc;

    /**来源编号*/
    @Excel(name = "来源编号", width = 15)
    @ApiModelProperty(value = "来源编号")
    private java.lang.String costSoNo;
    /**来源名称*/
    @Excel(name = "来源名称", width = 15)
    @ApiModelProperty(value = "来源名称")
    private java.lang.String costSoName;
    /**来源日期*/
    @Excel(name = "来源日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "来源日期")
    private java.util.Date costSoDate;
    /**计费税率*/
    @Excel(name = "计费税率", width = 15)
    @ApiModelProperty(value = "计费税率")
    private java.math.BigDecimal costSl;
    /**货币*/
    @Excel(name = "货币", width = 15)
    @ApiModelProperty(value = "货币")
    private java.lang.String costHb;
    /**计费不含税价*/
    @Excel(name = "计费不含税价", width = 15)
    @ApiModelProperty(value = "计费不含税价")
    private java.math.BigDecimal costCoBhsj;
    /**计费原含税价*/
    @Excel(name = "计费原含税价", width = 15)
    @ApiModelProperty(value = "计费原含税价")
    private java.math.BigDecimal costCoYhsj;
}
