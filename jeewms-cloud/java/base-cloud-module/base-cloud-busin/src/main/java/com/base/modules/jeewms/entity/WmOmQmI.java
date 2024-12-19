package com.base.modules.jeewms.entity;

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
 * @Description: 下架任务
 * @Author: base-boot
 * @Date:   2021-05-20
 * @Version: V1.0
 */
@Data
@TableName("wm_om_qm_i")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="wm_om_qm_i对象", description="下架任务")
public class WmOmQmI implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人名称*/
	// @Excel(name = "创建人名称", width = 15)
    @ApiModelProperty(value = "创建人名称")
    private java.lang.String createName;
	/**创建人登录名称*/
    @ApiModelProperty(value = "创建人登录名称")
    private java.lang.String createBy;
	/**创建日期*/
    @Excel(name = "创建日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人名称*/
	//// @Excel(name = "更新人名称", width = 15)
    @ApiModelProperty(value = "更新人名称")
    private java.lang.String updateName;
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
	/**所属公司*/
	//// @Excel(name = "所属公司", width = 15)
    @ApiModelProperty(value = "所属公司")
    private java.lang.String sysCompanyCode;
	/**到货通知单*/
	@Excel(name = "到货通知单", width = 15)
    @ApiModelProperty(value = "到货通知单")
    private java.lang.String omNoticeId;
	/**到货通知行项目*/
	//@Excel(name = "到货通知行项目", width = 15)
    @ApiModelProperty(value = "到货通知行项目")
    private java.lang.String iomNoticeItem;
	/**商品编码*/
	//@Excel(name = "商品编码", width = 15)
    //@ApiModelProperty(value = "商品编码")
    @Excel(name = "商品编码", width = 15)
    @ApiModelProperty(value = "商品编码")
    private java.lang.String goodsId;
	/**出货数量*/
	//@Excel(name = "出货数量", width = 15)
    @ApiModelProperty(value = "出货数量")
    private java.lang.String omQuat;
	/**数量*/
	@Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
    private java.lang.String qmOkQuat;
	/**生产日期*/
	@Excel(name = "生产日期", width = 15)
    @ApiModelProperty(value = "生产日期")
    private java.lang.String proData;
	/**托盘*/
	@Excel(name = "托盘", width = 15)
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
	@Excel(name = "仓位", width = 15)
    @ApiModelProperty(value = "仓位")
    private java.lang.String binId;
	/**体积*/
	//@Excel(name = "体积", width = 15)
    @ApiModelProperty(value = "体积")
    private java.lang.String tinTj;
	/**重量*/
	//@Excel(name = "重量", width = 15)
    @ApiModelProperty(value = "重量")
    private java.lang.String tinZhl;
	/**是否已下架*/
	@Excel(name = "是否已下架", width = 15, dicCode = "sf_yn")
	@Dict(dicCode = "sf_yn")
    @ApiModelProperty(value = "是否已下架")
    private java.lang.String binSta;
	/**货主*/
	//@Excel(name = "货主", width = 15, dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
    @Excel(name = "供应商", width = 15, dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
	@Dict(dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
    @ApiModelProperty(value = "货主")
    private java.lang.String cusCode;
	/**温度*/
	//@Excel(name = "温度", width = 15)
    @ApiModelProperty(value = "温度")
    private java.lang.String recDeg;
	/**任务接收人*/
	//@Excel(name = "任务接收人", width = 15, dictTable = "t_s_base_user", dicText = "username", dicCode = "username")
	@Dict(dictTable = "sys_user", dicText = "username", dicCode = "username")
    @ApiModelProperty(value = "任务接收人")
    private java.lang.String assignTo;
	/**基本单位*/
	//@Excel(name = "基本单位", width = 15)
    @ApiModelProperty(value = "基本单位")
    private java.lang.String baseUnit;
	/**基本单位数量*/
	//@Excel(name = "基本单位数量", width = 15)
    @ApiModelProperty(value = "基本单位数量")
    private java.lang.String baseGoodscount;
	/**客户名称*/
	//@Excel(name = "客户名称", width = 15)
    @ApiModelProperty(value = "客户名称")
    private java.lang.String cusName;
	/**商品名称*/
	//@Excel(name = "商品名称", width = 15)
    //@ApiModelProperty(value = "商品名称")
    @Excel(name = "商品名称", width = 15)
    @ApiModelProperty(value = "商品名称")
    private java.lang.String goodsName;
	/**波次编号*/
	//@Excel(name = "波次编号", width = 15)
    @ApiModelProperty(value = "波次编号")
    private java.lang.String waveId;
	/**客户订单号*/
	@Excel(name = "装箱单号", width = 15)
    @ApiModelProperty(value = "装箱单号")
    private java.lang.String imCusCode;
	/**备注*/
	//@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String omBeiZhu;
    /**备注*/
    @Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String itemText;
	/**条码*/
	//@Excel(name = "条码", width = 15)
    @ApiModelProperty(value = "条码")
    private java.lang.String barcode;
	/**保质期*/
	//@Excel(name = "保质期", width = 15)
    @ApiModelProperty(value = "保质期")
    private java.lang.String baozhiqi;
	/**规格*/
	//@Excel(name = "规格", width = 15)
    @ApiModelProperty(value = "规格")
    private java.lang.String shpGuiGe;
	/**拣货换算*/
	//@Excel(name = "拣货换算", width = 15)
    @ApiModelProperty(value = "拣货换算")
    private java.lang.String pickNotice;
	/**firstRq*/
	//@Excel(name = "firstRq", width = 15)
    @ApiModelProperty(value = "firstRq")
    private java.lang.String firstRq;
	/**secondRq*/
	//@Excel(name = "secondRq", width = 15)
    @ApiModelProperty(value = "secondRq")
    private java.lang.String secondRq;

    @TableField(exist = false)
    private String goodsTypeId;
    /**租户*/
    // @Excel(name = "租户", width = 15)
    @ApiModelProperty(value = "租户")
    private Integer tenantId;

//   plqn箱号字段 private java.lang.String firstRq;

    /**体积*/
    @TableField(exist = false)
    private String tinVolume;

    /**重量*/
    @TableField(exist = false)
    private String tinWeight;

    /**商品总库存*/
    @TableField(exist = false)
    private Integer allStock;

}
