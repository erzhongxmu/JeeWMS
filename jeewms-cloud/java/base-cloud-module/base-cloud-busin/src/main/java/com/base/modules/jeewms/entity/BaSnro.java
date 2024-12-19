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
 * @Description: 自动编码
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Data
@TableName("ba_snro")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ba_snro对象", description="自动编码")
public class BaSnro implements Serializable {
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
	/**编号类型*/
	@Excel(name = "编号类型", width = 15, dictTable = "ba_order_type", dicText = "order_type_code", dicCode = "order_type_code")
	@Dict(dictTable = "ba_order_type", dicText = "order_type_code", dicCode = "order_type_code")
    @ApiModelProperty(value = "编号类型")
    private java.lang.String snroTypeCode;
	/**组织机构*/
	@Excel(name = "组织机构", width = 15)
    @ApiModelProperty(value = "组织机构")
    private java.lang.String snroOrg;
	/**前缀*/
	@Excel(name = "前缀", width = 15)
    @ApiModelProperty(value = "前缀")
    private java.lang.String snroFindex;
	/**分隔符*/
	@Excel(name = "分隔符", width = 15)
    @ApiModelProperty(value = "分隔符")
    private java.lang.String snroSplit;
	/**年位数*/
	@Excel(name = "年位数", width = 15)
    @ApiModelProperty(value = "年位数")
    private java.lang.String snroYear;
	/**月位数*/
	@Excel(name = "月位数", width = 15)
    @ApiModelProperty(value = "月位数")
    private java.lang.String snroMonth;
	/**日位数*/
	@Excel(name = "日位数", width = 15)
    @ApiModelProperty(value = "日位数")
    private java.lang.String snroDay;
	/**序号位数*/
	@Excel(name = "序号位数", width = 15)
    @ApiModelProperty(value = "序号位数")
    private java.lang.String snroSeri;
	/**示例号码*/
	@Excel(name = "示例号码", width = 15)
    @ApiModelProperty(value = "示例号码")
    private java.lang.String snroExp;
}
