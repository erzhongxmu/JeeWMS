package com.base.modules.jeeerp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: conf_erp
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
@ApiModel(value="conf_erp对象", description="conf_erp")
@Data
@TableName("conf_erp")
public class TaxClassification implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人名称*/
//	@Excel(name = "创建人名称", width = 15)
    @ApiModelProperty(value = "创建人名称")
    private String createName;
	/**创建人登录名称*/
    @ApiModelProperty(value = "创建人登录名称")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人名称*/
//	@Excel(name = "更新人名称", width = 15)
    @ApiModelProperty(value = "更新人名称")
    private String updateName;
	/**更新人登录名称*/
    @ApiModelProperty(value = "更新人登录名称")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**query01*/
//	@Excel(name = "query01", width = 15)
    @ApiModelProperty(value = "query01")
    private String query01;
	/**query02*/
//	@Excel(name = "query02", width = 15)
    @ApiModelProperty(value = "query02")
    private String query02;
	/**query03*/
//	@Excel(name = "query03", width = 15)
    @ApiModelProperty(value = "query03")
    private String query03;
	/**配置编码*/
	@Excel(name = "商品编码", width = 15)
    @ApiModelProperty(value = "商品编码")
    private String query04;
	/**query05*/
	@Excel(name = "品名", width = 15)
    @ApiModelProperty(value = "品名")
    private String query05;
	/**query06*/
	@Excel(name = "供应商编码", width = 15)
    @ApiModelProperty(value = "供应商编码")
    private String query06;
	/**query07*/
	@Excel(name = "税收分类编码", width = 15)
    @ApiModelProperty(value = "税收分类编码")
    private String query07;
	/**query08*/
	@Excel(name = "开票品名", width = 15)
    @ApiModelProperty(value = "开票品名")
    private String query08;
	/**query09*/
	@Excel(name = "规格型号", width = 15)
    @ApiModelProperty(value = "规格型号")
    private String query09;
	/**query10*/
	@Excel(name = "单位", width = 15)
    @ApiModelProperty(value = "单位")
    private String query10;
}
