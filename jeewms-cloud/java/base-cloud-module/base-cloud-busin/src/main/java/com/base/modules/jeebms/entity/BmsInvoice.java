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
 * @Description: bms_invoice
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Data
@TableName("bms_invoice")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="bms_invoice对象", description="开票记录")
public class BmsInvoice implements Serializable {
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
    /**开票编号*/
    @Excel(name = "开票编号", width = 15)
    @ApiModelProperty(value = "开票编号")
    private java.lang.String invoiceNo;
    /**开票类型*/
    @Excel(name = "开票类型", width = 15)
    @ApiModelProperty(value = "开票类型")
    private java.lang.String invoiceType;
    /**发票号*/
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
    @Excel(name = "发票号", width = 15)
    @ApiModelProperty(value = "发票号")
    private java.lang.String invoiceDocNo;
    /**开票金额*/
    @Excel(name = "开票金额", width = 15)
    @ApiModelProperty(value = "开票金额")
    private java.math.BigDecimal invoiceSum;
    /**税额*/
    @Excel(name = "税额", width = 15)
    @ApiModelProperty(value = "税额")
    private java.math.BigDecimal invoiceSe;
    /**税率*/
    @Excel(name = "税率", width = 15)
    @ApiModelProperty(value = "税率")
    private java.math.BigDecimal costSl;
    /**账单编号*/
    @Excel(name = "账单编号", width = 15)
    @ApiModelProperty(value = "账单编号")
    private java.lang.String billNo;
    /**备注*/
    @Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;
    /**状态*/
    @Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.String status;
}
