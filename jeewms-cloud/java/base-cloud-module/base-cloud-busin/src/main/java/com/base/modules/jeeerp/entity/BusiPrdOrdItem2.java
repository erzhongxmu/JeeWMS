package com.base.modules.jeeerp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.base.common.aspect.annotation.Dict;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: busi_prd_ord_item
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
@ApiModel(value="busi_prd_ord_item对象", description="busi_prd_ord_item")
@Data
@TableName("busi_prd_ord_item")
public class BusiPrdOrdItem2 implements Serializable {
    private static final long serialVersionUID = 1L;

    /**主键*/
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
    /**创建人名称*/
//@Excel(name = "创建人名称", width = 15)
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
//@Excel(name = "更新人名称", width = 15)
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

    @Excel(name = "子PO", width = 15)
    @ApiModelProperty(value = "子PO")
    private String query01;
    @Excel(name = "客户名称", width = 15)
    @ApiModelProperty(value = "客户名称")
    private String query02;
    @Excel(name = "企业属性", width = 15)
    @ApiModelProperty(value = "企业属性")
    private String query03;
    @Excel(name = "制造商", width = 15)
    @ApiModelProperty(value = "制造商")
    private String query04;
    @Excel(name = "采购员", width = 15)
    @ApiModelProperty(value = "采购员")
    private String query05;
    @Excel(name = "SKU", width = 15)
    @ApiModelProperty(value = "SKU")
    private String query06;
    @Excel(name = "产品名称", width = 15)
    @ApiModelProperty(value = "产品名称")
    private String query07;
    @Excel(name = "订单数量", width = 15)
    @ApiModelProperty(value = "订单数量")
    private String query08;
    @Excel(name = "跟单员", width = 15)
    @ApiModelProperty(value = "跟单员")
    private String query09;
    @Excel(name = "验货类型", width = 15)
    @ApiModelProperty(value = "验货类型")
    private String query10;
    @Excel(name = "验货工时", width = 15)
    @ApiModelProperty(value = "验货工时")
    private String query11;
    @Excel(name = "验货数", width = 15)
    @ApiModelProperty(value = "验货数")
    private String query12;
    @Excel(name = "良品数", width = 15)
    @ApiModelProperty(value = "良品数")
    private String query13;
    @Excel(name = "不良品数", width = 15)
    @ApiModelProperty(value = "不良品数")
    private String query14;
    @Excel(name = "不良比例", width = 15)
    @ApiModelProperty(value = "不良比例")
    private String query15;
    @Excel(name = "不良描述", width = 15)
    @ApiModelProperty(value = "不良描述")
    private String query16;
    @Excel(name = "最终判定", width = 15)
    @ApiModelProperty(value = "最终判定")
    private String query17;
    @Excel(name = "验货员", width = 15)
    @ApiModelProperty(value = "验货员")
    private String query18;
    @Excel(name = "验货日期", width = 15)
    @ApiModelProperty(value = "验货日期")
    private String query19;
    @Excel(name = "箱长宽高", width = 15)
    @ApiModelProperty(value = "箱长宽高")
    private String query20;
    @Excel(name = "每箱重量", width = 15)
    @ApiModelProperty(value = "每箱重量")
    private String query21;
    @Excel(name = "尾箱备注", width = 15)
    @ApiModelProperty(value = "尾箱备注")
    private String query22;
//    @Excel(name = "附件", width = 15)
//    @ApiModelProperty(value = "附件")
//    private String attr1;
}
