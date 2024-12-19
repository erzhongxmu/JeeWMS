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
 * @Description: bms_cost_source
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Data
@TableName("bms_cost_source")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="bms_cost_source对象", description="计费来源")
public class BmsCostSource implements Serializable {
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
    /**来源编号*/
    @Excel(name = "来源编号", width = 15)
    @ApiModelProperty(value = "来源编号")
    private java.lang.String costSoNo;
    /**来源名称*/
    @Excel(name = "来源名称", width = 15)
    @ApiModelProperty(value = "来源名称")
    private java.lang.String costSoName;
    /**来源类型编号*/
    @Excel(name = "来源类型编号", width = 15)
    @ApiModelProperty(value = "来源类型编号")
    private java.lang.String costSNo;
    /**来源类型名称*/
    @Excel(name = "来源类型名称", width = 15)
    @ApiModelProperty(value = "来源类型名称")
    private java.lang.String costSName;
    /**来源类型描述*/
    @Excel(name = "来源类型描述", width = 15)
    @ApiModelProperty(value = "来源类型描述")
    private java.lang.String costSDesc;
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
    /**来源数量*/
    @Excel(name = "来源数量", width = 15)
    @ApiModelProperty(value = "来源数量")
    private java.math.BigDecimal costSoSum;
    /**来源单位*/
    @Excel(name = "来源单位", width = 15)
    @ApiModelProperty(value = "来源单位")
    private java.lang.String costSoUnit;
    /**来源日期*/
    @Excel(name = "来源日期", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "来源日期")
    private java.util.Date costSoDate;
    /**状态*/
    @Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.String status;

    /**仓库*/
    @Excel(name = "仓库", width = 15)
    @ApiModelProperty(value = "仓库")
    private java.lang.String whNo;


    /**关联单号*/
    @Excel(name = "关联单号", width = 15)
    @ApiModelProperty(value = "关联单号")
    private java.lang.String imNo;



    /**附件单号*/
    @Excel(name = "附件单号", width = 15)
    @ApiModelProperty(value = "附件单号")
    private java.lang.String accessoryNo;

    /**增值服务编号*/
    @Excel(name = "增值服务编号", width = 15)
    @ApiModelProperty(value = "增值服务编号")
    private java.lang.String serviceCode;
}
