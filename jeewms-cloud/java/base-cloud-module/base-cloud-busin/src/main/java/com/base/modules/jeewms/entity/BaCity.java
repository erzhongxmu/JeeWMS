package com.base.modules.jeewms.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @Description: 地区信息
 * @Author: base-boot
 * @Date:   2021-05-19
 * @Version: V1.0
 */
@Data
@TableName("ba_city")
@ApiModel(value="ba_city对象", description="地区信息")
public class BaCity implements Serializable {
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
	/**地区代码*/
	@Excel(name = "地区代码", width = 15)
	@ApiModelProperty(value = "地区代码")
	private java.lang.String cityCode;
	/**地区名称*/
	@Excel(name = "地区名称", width = 15)
	@ApiModelProperty(value = "地区名称")
	private java.lang.String cityName;
	/**地区助记码*/
	@Excel(name = "地区助记码", width = 15)
	@ApiModelProperty(value = "地区助记码")
	private java.lang.String citySerc;
	/**城市类型*/
	@Excel(name = "城市类型", width = 15, dictTable = "ba_city_type", dicText = "city_type_name", dicCode = "city_type_code")
	@Dict(dictTable = "ba_city_type", dicText = "city_type_name", dicCode = "city_type_code")
	@ApiModelProperty(value = "城市类型")
	private java.lang.String cityTypeCode;
	/**片区信息*/
	@Excel(name = "片区信息", width = 15, dictTable = "ba_barea", dicText = "barea_name", dicCode = "barea_code")
	@Dict(dictTable = "ba_barea", dicText = "barea_name", dicCode = "barea_code")
	@ApiModelProperty(value = "片区信息")
	private java.lang.String bareaCode;
	/**大区信息*/
	@Excel(name = "大区信息", width = 15, dictTable = "ba_area", dicText = "area_name", dicCode = "area_code")
	@Dict(dictTable = "ba_area", dicText = "area_name", dicCode = "area_code")
	@ApiModelProperty(value = "大区信息")
	private java.lang.String dareaCode;
	/**停用*/
	@Excel(name = "停用", width = 15, dicCode = "is_del")
	@Dict(dicCode = "is_del")
	@ApiModelProperty(value = "停用")
	private java.lang.String cityDel;
	/**父级节点*/
	@Excel(name = "父级节点", width = 15)
	@ApiModelProperty(value = "父级节点")
	private java.lang.String pid;
	/**是否有子节点*/
	@Excel(name = "是否有子节点", width = 15, dicCode = "yn")
	@Dict(dicCode = "yn")
	@ApiModelProperty(value = "是否有子节点")
	private java.lang.String hasChild;

	@TableField(exist = false)
	private List<BaCity> baCityChild;
}
