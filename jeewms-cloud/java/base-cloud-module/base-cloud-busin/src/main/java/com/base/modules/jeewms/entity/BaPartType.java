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

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Description: 商品分类
 * @Author: base-boot
 * @Date:   2021-06-07
 * @Version: V1.0
 */
@Data
@TableName("ba_part_type")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ba_part_type对象", description="商品分类")
public class BaPartType implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**创建人登录名称*/
    @ApiModelProperty(value = "创建人登录名称")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人登录名称*/
    @ApiModelProperty(value = "更新人登录名称")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @Excel(name = "所属部门", width = 15)
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**分类名称*/
	@Excel(name = "分类名称", width = 15)
    @ApiModelProperty(value = "分类名称")
    @NotBlank(message = "分类名称不能为空")
    private java.lang.String typeName;
	/**堆码极限*/
//	@Excel(name = "堆码极限", width = 15)
    @ApiModelProperty(value = "堆码极限")
//    @NotBlank(message = "堆码极限不能为空")
    private java.lang.String stackLimit;
	/**最大库存量(件)*/
//	@Excel(name = "最大库存量(件)", width = 15)
    @ApiModelProperty(value = "最大库存量(件)")
//    @NotBlank(message = "最大库存量不能为空")
    private java.lang.String maxStock;
	/**备用1*/
	@Excel(name = "停用状态", width = 15)
    @ApiModelProperty(value = "停用状态")
    private java.lang.String attr1;
	/**备用2*/
	//@Excel(name = "备用2", width = 15)
    @ApiModelProperty(value = "备用2")
    private java.lang.String attr2;
	/**分类名称英文*/
	@Excel(name = "分类名称英文", width = 15)
    @ApiModelProperty(value = "分类名称英文")
    private java.lang.String attr3;
	/**分类代码*/
	@Excel(name = "分类代码", width = 15)
    @ApiModelProperty(value = "分类代码")
    private java.lang.String attr4;
	/**海关编码*/
	@Excel(name = "海关编码", width = 15)
    @ApiModelProperty(value = "海关编码")
    private java.lang.String attr5;

    /**用途*/
    @Excel(name = "用途", width = 15)
    @ApiModelProperty(value = "用途")
    private java.lang.String attr6;

    /**租户*/
//    // @Excel(name = "租户", width = 15)
    /*@ApiModelProperty(value = "租户")
    private Integer tenantId;*/

    /**库区名称英文*/
    @Excel(name = "库区名称英文", width = 15)
    @ApiModelProperty(value = "库区名称英文")
    private java.lang.String areaCodeEn;

    //备注
    @Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String remarks;
}
