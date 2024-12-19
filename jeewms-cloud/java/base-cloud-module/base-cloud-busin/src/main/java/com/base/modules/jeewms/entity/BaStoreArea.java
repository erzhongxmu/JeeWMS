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
 * @Description: 库区
 * @Author: base-boot
 * @Date:   2021-06-07
 * @Version: V1.0
 */
@Data
@TableName("ba_store_area")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ba_store_area对象", description="库区")
public class BaStoreArea implements Serializable {
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
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**库区编码*/
	@Excel(name = "库区编码", width = 15)
    @ApiModelProperty(value = "库区编码")
    private java.lang.String areaCode;
	/**库区名称*/
	@Excel(name = "库区名称", width = 15)
    @ApiModelProperty(value = "库区名称")
    private java.lang.String areaName;
	/**仓库*/
	@Excel(name = "仓库", width = 15, dictTable = "ba_store", dicText = "store_name", dicCode = "store_code")
	@Dict(dictTable = "ba_store", dicText = "store_name", dicCode = "store_code")
    @ApiModelProperty(value = "仓库")
    private java.lang.String wareCode;
	/**仓库名称*/
//	@Excel(name = "仓库名称", width = 15)
    @ApiModelProperty(value = "仓库名称")
    private java.lang.String wareName;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;

    @Excel(name = "良次品", width = 15)
    @ApiModelProperty(value = "良次品，Y良品，N次品")
    private String areaStatus;
    /**租户*/
    // @Excel(name = "租户", width = 15)
    /*@ApiModelProperty(value = "租户")
    private Integer tenantId;*/

    //备注
    @Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String remarks;

    @Excel(name = "库区名称英文", width = 15)
    @ApiModelProperty(value = "库区名称英文")
    private String areaCodeEn;
}
