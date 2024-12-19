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
import com.base.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: 下架明细-下架调整导出
 * @Author: zly
 * @Date: 2021-06-16
 * @Version: V1.0
 */
@Data
@TableName("wm_to_down_goods")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "wm_to_down_goods对象", description = "下架调整导出")
public class WmToDownGoodsVo implements Serializable {
    private static final long serialVersionUID = 8260656121551297075L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
    /**
     * 创建人名称
     */
    //// @Excel(name = "创建人名称", width = 15)
    @ApiModelProperty(value = "创建人名称")
    private java.lang.String createName;
    /**
     * 创建人登录名称
     */
    @ApiModelProperty(value = "创建人登录名称")
    private java.lang.String createBy;
    /**
     * 创建日期
     */
    //@Excel(name = "创建日期", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
    /**
     * 更新人名称
     */
    //// @Excel(name = "更新人名称", width = 15)
    @ApiModelProperty(value = "更新人名称")
    private java.lang.String updateName;
    /**
     * 更新人登录名称
     */
    @ApiModelProperty(value = "更新人登录名称")
    private java.lang.String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
    /**
     * 所属公司
     */
    //// @Excel(name = "所属公司", width = 15)
    @ApiModelProperty(value = "所属公司")
    private java.lang.String sysCompanyCode;
    /**
     * 商品编码
     */
    //@Excel(name = "商品编码", width = 15)
    //@ApiModelProperty(value = "商品编码")
    @Excel(name = "商品编码", width = 15)
    @ApiModelProperty(value = "商品编码")
    private java.lang.String goodsId;
    /**
     * 商品名称
     */
    //@Excel(name = "商品名称", width = 15)
    //@ApiModelProperty(value = "商品名称")
    @Excel(name = "商品名称", width = 15)
    @ApiModelProperty(value = "商品名称")
    private java.lang.String goodsName;
    /**
     * 数量
     */
    @Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
    private java.lang.String goodsQua;
    /**
     * 完成数量
     */
    @Excel(name = "完成数量", width = 15)
    @ApiModelProperty(value = "完成数量")
    private java.lang.String goodsQuaok;
    /**
     * 生产日期
     */
    //@Excel(name = "生产日期", width = 15)
    @ApiModelProperty(value = "生产日期")
    private java.lang.String goodsProData;
    /**
     * 库位编码
     */
    //@Excel(name = "库位编码", width = 15)
    @ApiModelProperty(value = "库位编码")
    private java.lang.String kuWeiBianMa;
    /**
     * 源托盘码
     */
    @Excel(name = "源托盘码", width = 15)
    @ApiModelProperty(value = "源托盘码")
    private java.lang.String binIdFrom;
    /**
     * 基本单位
     */
    //@Excel(name = "基本单位", width = 15)
    @ApiModelProperty(value = "基本单位")
    private java.lang.String baseUnit;
    /**
     * 原始单据编码
     */
    //@Excel(name = "原始单据编码", width = 15)
    @ApiModelProperty(value = "原始单据编码")
    private java.lang.String orderId;
    /**
     * 货主
     */
    @Excel(name = "供应商名称", width = 15, dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
    @Dict(dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
    @ApiModelProperty(value = "货主")
    private java.lang.String cusCode;

    /**
     * 状态
     */
    @Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.String downSta;
    /**
     * 基本单位数量
     */
    //@Excel(name = "基本单位数量", width = 15)
    @ApiModelProperty(value = "基本单位数量")
    private java.lang.String baseGoodscount;
    /**
     * 订单号
     */
    //@Excel(name = "订单号", width = 15)
    @ApiModelProperty(value = "订单号")
    private java.lang.String imCusCode;
    /**
     * 目标托盘
     */
    //@Excel(name = "目标托盘", width = 15)
    @ApiModelProperty(value = "目标托盘")
    private java.lang.String binIdTo;
    /**
     * 原始单据行项目
     */
    //@Excel(name = "原始单据行项目", width = 15)
    @ApiModelProperty(value = "原始单据行项目")
    private java.lang.String orderIdI;
    /**
     * 原始单据类型
     */
    //@Excel(name = "原始单据类型", width = 15)
    @ApiModelProperty(value = "原始单据类型")
    private java.lang.String orderType;
    /**
     * 单位
     */
    //@Excel(name = "单位", width = 15)
    @ApiModelProperty(value = "单位")
    private java.lang.String goodsUnit;
    /**
     * 批次
     */
    //@Excel(name = "批次", width = 15)
    @ApiModelProperty(value = "批次")
    private java.lang.String goodsBatch;
    /**
     * 作业类型
     */
    //@Excel(name = "作业类型", width = 15)
    @ApiModelProperty(value = "作业类型")
    private java.lang.String actTypeCode;
    /**
     * 备注
     */
    //@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String omBeiZhu;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "确认时间")
    private java.util.Date confirmationDate;

    @TableField(exist = false)
    private String areaCode;

    @TableField(exist = false)
    private String kwName;

}
