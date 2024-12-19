package com.base.modules.jeeerp.vo;

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
 * @Description: busi_prd_ord
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
@ApiModel(value="busi_prd_ord对象", description="busi_prd_ord")
@Data
@TableName("busi_prd_ord")
public class BusiPrdOrdbzddexport implements Serializable {
    private static final long serialVersionUID = 1L;
    /**主批次*/
    @Excel(name = "生产PO", width = 15)
    @ApiModelProperty(value = "生产PO")
    private String query13;
    /**客户编码*/
    @Dict(dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
    @Excel(name = "客户", width = 15)
    @ApiModelProperty(value = "客户")
    private String query24;
    /**客户编码*/
    @Excel(name = "企业属性", width = 15)
    @ApiModelProperty(value = "企业属性")
    private String XingYeFenLei;
    /**商品编码/成品*/
    @Excel(name = "SKU", width = 15)
    @ApiModelProperty(value = "SKU")
    private String query10;
    /**商品名称/成品*/
    @Excel(name = "品名", width = 15)
    @ApiModelProperty(value = "品名")
    private String query11;
    /**数量*/
    @Excel(name = "加工数量", width = 15)
    @ApiModelProperty(value = "加工数量")
    private Double num01;



    /**单位*/
    @Excel(name = "单位", width = 15)
    @ApiModelProperty(value = "单位")
    private String query12;
    /**query31*/
    @Excel(name = "税率", width = 15)
    @ApiModelProperty(value = "税率")
    private String query35;
    /**query31*/
    @Excel(name = "单价", width = 15)
    @ApiModelProperty(value = "单价")
    private String query32;
    /**query18*/
    @Excel(name = "人工费", width = 15)
    @ApiModelProperty(value = "人工费")
    private String query19;
    /**query31*/
    @Excel(name = "工时", width = 15)
    @ApiModelProperty(value = "工时")
    private String query31;

    @Excel(name = "单价(含加工费)", width = 15)
    @ApiModelProperty(value = "单价(含加工费)")
    private String query33;

    /**query18*/
    @Excel(name = "出货日期", width = 15)
    @ApiModelProperty(value = "出货日期")
    private String query18;
    /**单据状态*/
    @Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private String query02;




    @Excel(name = "销售单号", width = 15)
    @ApiModelProperty(value = "销售单号")
    private String link03;

    @Excel(name = "内部发票号", width = 15)
    @ApiModelProperty(value = "内部发票号")
    private String query17;
    @Excel(name = "采购人", width = 15)
    @ApiModelProperty(value = "采购人")
    private String query16;
    @Excel(name = "计划期限", width = 15)
    @ApiModelProperty(value = "计划期限")
    private String query21;


    /**创建人名称*/
    @Excel(name = "创建人名称", width = 15)
    @ApiModelProperty(value = "创建人名称")
    private String createName;

    /**创建日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
    @Excel(name = "完成时间", width = 15)
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "完成时间")
    private String query22;
    /**备注1*/
    @Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String text01;
    /**单号*/
    @Excel(name = "单号", width = 15)
    @ApiModelProperty(value = "单号")
    private String query04;

}
