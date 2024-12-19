package com.base.modules.jeebms.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
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

/**
 * @Description: bms_cost_rule_h
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@ApiModel(value="bms_cost_rule_h对象", description="bms_cost_rule_h")
@Data
@TableName("bms_cost_rule_h")
public class BmsCostRuleH implements Serializable {
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
    @Excel(name = "合同编号", width = 15)
    @ApiModelProperty(value = "合同编号")
    private java.lang.String contNo;
    /**合同名称*/
    @Excel(name = "合同名称", width = 15)
    @ApiModelProperty(value = "合同名称")
    private java.lang.String contName;
    /**规则编号*/
    @Excel(name = "规则编号", width = 15)
    @ApiModelProperty(value = "规则编号")
    private java.lang.String costRuleNo;
    /**规则名称*/
    @Excel(name = "规则名称", width = 15)
    @ApiModelProperty(value = "规则名称")
    private java.lang.String costRuleName;
    /**费用编号*/
    @Excel(name = "费用编号", width = 15)
    @ApiModelProperty(value = "费用编号")
    private java.lang.String costNo;
    /**费用名称*/
    @Excel(name = "费用名称", width = 15)
    @ApiModelProperty(value = "费用名称")
    private java.lang.String costName;
    /**来源类型编号*/
    @Excel(name = "来源类型编号", width = 15)
    @ApiModelProperty(value = "来源类型编号")
    private java.lang.String costSNo;
    /**来源类型名称*/
    @Excel(name = "来源类型名称", width = 15)
    @ApiModelProperty(value = "来源类型名称")
    private java.lang.String costSName;
    /**计费对象类型*/
    @Excel(name = "计费对象类型", width = 15)
    @ApiModelProperty(value = "计费对象类型")
    private java.lang.String costObjType;
    /**计费对象编号*/
    @Excel(name = "计费对象编号", width = 15)
    @ApiModelProperty(value = "计费对象编号")
    private java.lang.String costObjNo;
    /**计费对象名称*/
    @Excel(name = "计费对象名称", width = 15)
    @ApiModelProperty(value = "计费对象名称")
    private java.lang.String costObjName;
    /**最低收费*/
    @Excel(name = "最低收费", width = 15)
    @ApiModelProperty(value = "最低收费")
    private java.math.BigDecimal costMin;
    /**最高收费*/
    @Excel(name = "最高收费", width = 15)
    @ApiModelProperty(value = "最高收费")
    private java.math.BigDecimal costMax;
    /**计费周期*/
    @Excel(name = "计费周期", width = 15)
    @ApiModelProperty(value = "计费周期")
    private java.lang.String costPeriod;
    /**有效开始时间*/
    @Excel(name = "有效开始时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "有效开始时间")
    private java.util.Date costValidBegin;
    /**有效结束时间*/
    @Excel(name = "有效结束时间",width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "有效结束时间")
    private java.util.Date costValidEnd;
}
