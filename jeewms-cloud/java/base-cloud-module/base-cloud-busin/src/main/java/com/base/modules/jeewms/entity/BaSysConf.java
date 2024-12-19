package com.base.modules.jeewms.entity;

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
 * @Description: 系统参数
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Data
@TableName("ba_sys_conf")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ba_sys_conf对象", description="系统参数")
public class BaSysConf implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**创建人名称*/
	// @Excel(name = "创建人名称", width = 15)
    @ApiModelProperty(value = "创建人名称")
    private java.lang.String createName;
	/**创建人登录名称*/
    @ApiModelProperty(value = "创建人登录名称")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人名称*/
	// @Excel(name = "更新人名称", width = 15)
    @ApiModelProperty(value = "更新人名称")
    private java.lang.String updateName;
	/**更新人登录名称*/
    @ApiModelProperty(value = "更新人登录名称")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**所属公司*/
	// @Excel(name = "所属公司", width = 15)
    @ApiModelProperty(value = "所属公司")
    private java.lang.String sysCompanyCode;
	/**系统参数类型*/
	@Excel(name = "系统参数类型", width = 15, dictTable = "ba_sysp_type", dicText = "sysp_type_code", dicCode = "sysp_type_code")
	@Dict(dictTable = "ba_sysp_type", dicText = "sysp_type_code", dicCode = "sysp_type_code")
    @ApiModelProperty(value = "系统参数类型")
    private java.lang.String sysConfType;
	/**业务操作环节*/
	@Excel(name = "业务操作环节", width = 15, dictTable = "ba_oper_step", dicText = "oper_step_code", dicCode = "oper_step_code")
	@Dict(dictTable = "ba_oper_step", dicText = "oper_step_code", dicCode = "oper_step_code")
    @ApiModelProperty(value = "业务操作环节")
    private java.lang.String sysConfStep;
	/**组织*/
	@Excel(name = "组织", width = 15)
    @ApiModelProperty(value = "组织")
    private java.lang.String sysConfOrg;
	/**合作伙伴*/
	@Excel(name = "合作伙伴", width = 15)
    @ApiModelProperty(value = "合作伙伴")
    private java.lang.String sysConfPartner;
	/**参数1*/
	@Excel(name = "参数1", width = 15)
    @ApiModelProperty(value = "参数1")
    private java.lang.String sysPara1;
	/**参数2*/
	@Excel(name = "参数2", width = 15)
    @ApiModelProperty(value = "参数2")
    private java.lang.String sysPara2;
	/**参数3*/
	@Excel(name = "参数3", width = 15)
    @ApiModelProperty(value = "参数3")
    private java.lang.String sysPara3;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String sysConfText;
}
