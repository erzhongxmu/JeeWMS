package com.base.modules.jeebms.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;

/**
 * @Description: bms_cost_rule_i
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@ApiModel(value="bms_cost_rule_i对象", description="bms_cost_rule_i")
@Data
@TableName("bms_cost_rule_i")
public class BmsCostRuleI implements Serializable {
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
//	//@Excel(name = "逻辑删除", width = 15)
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
    /**合同编号*/
    @ApiModelProperty(value = "合同编号")
    private java.lang.String contNo;
    /**行项目号*/
    @Excel(name = "行项目号", width = 15)
    @ApiModelProperty(value = "行项目号")
    private java.lang.String itemNo;
    /**计费类型*/
    @Excel(name = "计费类型", width = 15)
    @ApiModelProperty(value = "计费类型")
    private java.lang.String costTypeNo;
    /**计费类型*/
    @Excel(name = "计费类型名称", width = 15)
    @ApiModelProperty(value = "计费类型名称")
    private java.lang.String costTypeName;
    /**开始数量*/
    @Excel(name = "开始数量", width = 15)
    @ApiModelProperty(value = "开始数量")
    private java.math.BigDecimal beginSum;
    /**结束数量*/
    @Excel(name = "结束数量", width = 15)
    @ApiModelProperty(value = "结束数量")
    private java.math.BigDecimal endSum;
    /**单位*/
    @Excel(name = "单位", width = 15)
    @ApiModelProperty(value = "单位")
    private java.lang.String unit;
    /**不含税价*/
    @Excel(name = "不含税价", width = 15)
    @ApiModelProperty(value = "不含税价")
    private java.math.BigDecimal costUnit;
    /**税率*/
    @Excel(name = "税率", width = 15)
    @ApiModelProperty(value = "税率")
    private java.math.BigDecimal costSl;
    /**含税价*/
    @Excel(name = "含税价", width = 15)
    @ApiModelProperty(value = "含税价")
    private java.math.BigDecimal costHsj;
    /**原含税价*/
    @Excel(name = "原含税价", width = 15)
    @ApiModelProperty(value = "原含税价")
    private java.math.BigDecimal costYhsj;
    /**货币*/
    @Excel(name = "货币", width = 15)
    @ApiModelProperty(value = "货币")
    private java.lang.String costHb;


    /**区间开始数量*/
    @Excel(name = "区间开始数量", width = 15)
    @ApiModelProperty(value = "区间开始数量")
    private java.math.BigDecimal qjbeginSum;
    /**结束数量*/
    @Excel(name = "区间结束数量", width = 15)
    @ApiModelProperty(value = "区间结束数量")
    private java.math.BigDecimal qjendSum;

    /**单位数量*/
    @Excel(name = "单位数量", width = 15)
    @ApiModelProperty(value = "单位数量")
    private java.math.BigDecimal unitnumber;


    /**区间价格*/
    @Excel(name = "区间价格", width = 15)
    @ApiModelProperty(value = "区间价格")
    private java.math.BigDecimal qjPrice;
}
