package com.base.modules.jeewms.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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

/**
 * @Description: 进货管理——退货上架
 * @Author: base-boot
 * @Date:   2021-06-16
 * @Version: V1.0
 */
@Data
@TableName("wm_in_qm_i")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="wm_in_qm_i对象", description="进货管理——退货上架")
public class WmInQmIVo implements Serializable {
    private static final long serialVersionUID = -8110282690795504642L;

    /**主键*/
    @TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "主键")
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
    /**到货通知单*/
    @Excel(name = "到货通知单", width = 15)
    @ApiModelProperty(value = "到货通知单")
    private java.lang.String imNoticeId;
    /**到货通知行项目*/
    @Excel(name = "到货通知行项目", width = 15)
    @ApiModelProperty(value = "到货通知行项目")
    private java.lang.String imNoticeItem;
    /**商品编码*/
    //@Excel(name = "商品编码", width = 15)
    //@ApiModelProperty(value = "商品编码")
    @Excel(name = "商品编码", width = 15)
    @ApiModelProperty(value = "商品编码")
    private java.lang.String goodsId;
    /**到货数量*/
    @Excel(name = "到货数量", width = 15)
    @ApiModelProperty(value = "到货数量")
    private java.lang.String imQuat;
    /**收货数量*/
    @Excel(name = "收货数量", width = 15)
    @ApiModelProperty(value = "收货数量")
    private java.lang.String qmOkQuat;
    /**生产日期*/
    @Excel(name = "生产日期", width = 15)
    @ApiModelProperty(value = "生产日期")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date proData;
    /**托盘*/
    //@Excel(name = "托盘", width = 15)
    @ApiModelProperty(value = "托盘")
    private java.lang.String tinId;
    /**单位*/
    //@Excel(name = "单位", width = 15)
    @ApiModelProperty(value = "单位")
    private java.lang.String goodsUnit;
    /**批次*/
    //@Excel(name = "批次", width = 15)
    @ApiModelProperty(value = "批次")
    private java.lang.String goodsBatch;
    /**仓位*/
    //@Excel(name = "仓位", width = 15)
    //@ApiModelProperty(value = "仓位")
    @Excel(name = "库位", width = 15)
    @ApiModelProperty(value = "库位")
    private java.lang.String binId;
    /**体积*/
    //@Excel(name = "体积", width = 15)
    @ApiModelProperty(value = "体积")
    private java.lang.String tinTj;
    /**重量*/
    //@Excel(name = "重量", width = 15)
    @ApiModelProperty(value = "重量")
    private java.lang.String tinZhl;
    /**是否已上架*/
    @Excel(name = "是否已上架", width = 15)
    @ApiModelProperty(value = "是否已上架")
    private java.lang.String binSta;
    /**货主*/
    //@Excel(name = "货主", width = 15)
    @ApiModelProperty(value = "货主")
    private java.lang.String cusCode;
    /**温度*/
    //@Excel(name = "温度", width = 15)
    @ApiModelProperty(value = "温度")
    private java.lang.String recDeg;
    /**基本单位*/
    //@Excel(name = "基本单位", width = 15)
    @ApiModelProperty(value = "基本单位")
    private java.lang.String baseUnit;
    /**基本单位数量*/
    //@Excel(name = "基本单位数量", width = 15)
    @ApiModelProperty(value = "基本单位数量")
    private java.lang.String baseGoodscount;
    /**基本单位收货数量*/
    //@Excel(name = "基本单位收货数量", width = 15)
    @ApiModelProperty(value = "基本单位收货数量")
    private java.lang.String baseQmcount;
    /**cusName*/
    //@Excel(name = "客户名称", width = 15)
    //@ApiModelProperty(value = "客户名称")
    @Excel(name = "供应商名称", width = 15)
    @ApiModelProperty(value = "供应商名称")
    private java.lang.String cusName;
    /**goodsName*/
    //@Excel(name = "商品名称", width = 15)
    @ApiModelProperty(value = "商品名称")
    private java.lang.String goodsName;
    /**入库单号*/
    //@Excel(name = "入库单号", width = 15)
    @ApiModelProperty(value = "入库单号")
    private java.lang.String imCusCode;
    /**备注*/
    @Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String itemText;

    @TableField(exist = false)
    private String areaCode;

    @TableField(exist = false)
    private String kwName;
}
