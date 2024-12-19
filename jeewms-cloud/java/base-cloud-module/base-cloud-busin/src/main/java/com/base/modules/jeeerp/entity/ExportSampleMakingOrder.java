package com.base.modules.jeeerp.entity;

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

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: base_craft
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
@Data
@TableName("ExportSampleMakingOrder")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ExportSampleMakingOrder对象", description="ExportSampleMakingOrder")
public class ExportSampleMakingOrder implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主PO*/
	@Excel(name = "主PO", width = 15)
    @ApiModelProperty(value = "主PO")
    private String query13;

    /**客户*/
    @Excel(name = "客户", width = 15)
    @ApiModelProperty(value = "客户")
    private String query24;

    /**预计到货时间*/
    @Excel(name = "预计到货时间", width = 15)
    @ApiModelProperty(value = "预计到货时间")
    private String query21;

    /**销售单号*/
    @Excel(name = "销售单号", width = 15)
    @ApiModelProperty(value = "销售单号")
    private String link03;

    /**内部发票号*/
    @Excel(name = "内部发票号", width = 15)
    @ApiModelProperty(value = "内部发票号")
    private String query17;

    /**打样单号*/
    @Excel(name = "打样单号", width = 15)
    @ApiModelProperty(value = "打样单号")
    private String query04;

    /**供应商编码*/
    @Excel(name = "供应商编码", width = 15)
    @ApiModelProperty(value = "供应商编码")
    private String query08;

    /**供应商名称*/
    @Excel(name = "供应商名称", width = 15)
    @ApiModelProperty(value = "供应商名称")
    private String query09;

    /**采购人*/
    @Excel(name = "采购人", width = 15)
    @ApiModelProperty(value = "采购人")
    private String query16;

    /**创建日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;

    /**主备注*/
    @Excel(name = "主备注", width = 15)
    @ApiModelProperty(value = "主备注")
    private String text01;

    // 子表信息
    /**子PO*/
    @Excel(name = "子PO", width = 15)
    @ApiModelProperty(value = "子PO")
    private String query14;

    /**商品编码*/
    @Excel(name = "商品编码", width = 15)
    @ApiModelProperty(value = "商品编码")
    private String query10;

    /**商品名称*/
    @Excel(name = "商品名称", width = 15)
    @ApiModelProperty(value = "商品名称")
    private java.lang.String query11;

    /**单位*/
    @Excel(name = "单位", width = 15)
    @ApiModelProperty(value = "单位")
    private String query12;

    /**数量*/
    @Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
    private java.lang.Double num01;

    /**单价*/
    @Excel(name = "单价", width = 15)
    @ApiModelProperty(value = "单价")
    private java.lang.Double num04;

    /**检验类型*/
    @Excel(name = "检验类型", width = 15)
    @ApiModelProperty(value = "检验类型")
    private String query15;

    /**子备注*/
    @Excel(name = "子备注", width = 15)
    @ApiModelProperty(value = "子备注")
    private String text02;

    /**含税单价*/
    @Excel(name = "含税单价", width = 15)
    @ApiModelProperty(value = "含税单价")
    private java.lang.Double num06;

    /**含税总价*/
    @Excel(name = "含税总价", width = 15)
    @ApiModelProperty(value = "含税总价")
    private java.lang.Double num07;
    /**总价*/
    @Excel(name = "总价", width = 15)
    @ApiModelProperty(value = "总价")
    private java.lang.Double num05;

    /**总金额*/
    @Excel(name = "总金额", width = 15)
    @ApiModelProperty(value = "总金额")
    private java.lang.Double num08;


    /**含税总金额*/
    @Excel(name = "含税总金额", width = 15)
    @ApiModelProperty(value = "含税总金额")
    private java.lang.Double num09;


    /**税率*/
    @Excel(name = "税率", width = 15)
    @ApiModelProperty(value = "税率")
    private java.lang.Double num10;


    /**含税尾款*/
    @Excel(name = "含税尾款", width = 15)
    @ApiModelProperty(value = "含税尾款")
    private java.lang.Double num11;
    /**未清总金额*/
    @Excel(name = "尾款", width = 15)
    @ApiModelProperty(value = "尾款")
    private java.lang.Double num12;
}
