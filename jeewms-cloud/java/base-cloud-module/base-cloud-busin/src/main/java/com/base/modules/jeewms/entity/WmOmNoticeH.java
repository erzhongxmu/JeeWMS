package com.base.modules.jeewms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.base.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description: 出库管理
 * @Author: base-boot
 * @Date:   2021-06-04
 * @Version: V1.0
 */
@ApiModel(value="wm_om_notice_h对象", description="出库管理")
@Data
@TableName("wm_om_notice_h")
public class WmOmNoticeH implements Serializable {
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
    @Excel(name = "创建人登录名称", width = 15)
    @ApiModelProperty(value = "创建人登录名称")
    private java.lang.String createBy;
	/**创建日期*/
    @Excel(name = "创建日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人名称*/
	// @Excel(name = "更新人名称", width = 15)
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
    @Dict(dictTable = "sys_depart", dicText = "depart_name", dicCode = "org_code")
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**所属公司*/
//	@Excel(name = "所属公司", width = 15, dictTable = "ba_com", dicText = "com_zh_aname", dicCode = "com_code")
    @Dict(dictTable = "ba_com", dicText = "com_zh_aname", dicCode = "com_code")
    @ApiModelProperty(value = "所属公司")
    private java.lang.String sysCompanyCode;
	/**客户*/
	@Excel(name = "客户", width = 15, dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
    @Dict(dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
    @ApiModelProperty(value = "客户")
    private java.lang.String cusCode;
	/**要求交货时间*/
	@Excel(name = "要求交货时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "要求交货时间")
    private java.util.Date delvData;
	/**收货人*/
//	@Excel(name = "收货人", width = 15)
    @ApiModelProperty(value = "收货人")
    private java.lang.String delvMember;
	/**收货人电话*/
//	@Excel(name = "收货人电话", width = 15)
    @ApiModelProperty(value = "收货人电话")
    private java.lang.String delvMobile;
	/**收货人地址*/
//	@Excel(name = "收货人地址", width = 15)
    @ApiModelProperty(value = "收货人地址")
    private java.lang.String delvAddr;
	/**承运人*/
//	@Excel(name = "承运人", width = 15, dicCode = "tms_kd")
    @Dict(dicCode = "tms_kd")
    @ApiModelProperty(value = "承运人")
    private java.lang.String reMember;
	/**承运人电话*/
	//@Excel(name = "承运人电话", width = 15)
    @ApiModelProperty(value = "承运人电话")
    private java.lang.String reMobile;
	/**承运人车号*/
	//@Excel(name = "承运人车号", width = 15)
    @ApiModelProperty(value = "承运人车号")
    private java.lang.String reCarno;
	/**发货月台*/
	//@Excel(name = "发货月台", width = 15, dictTable = "ba_platform", dicText = "platform_name", dicCode = "platform_code")
    @Dict(dictTable = "ba_platform", dicText = "platform_name", dicCode = "platform_code")
    @ApiModelProperty(value = "发货月台")
    private java.lang.String omPlatNo;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String omBeizhu;
	/**状态*/
//	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.String omSta;
	/**出货单号*/
	//@Excel(name = "出货单号", width = 15)
    @ApiModelProperty(value = "出货单号")
    private java.lang.String omNoticeId;
	/**附件*/
	//@Excel(name = "附件", width = 15)
    @ApiModelProperty(value = "附件")
    private java.lang.String fuJian;
	/**所属部门*/
	//@Excel(name = "所属部门", width = 15)
    @ApiModelProperty(value = "所属部门")
    private java.lang.String readOnly;
	/**whereCon*/
	//@Excel(name = "whereCon", width = 15)
    @ApiModelProperty(value = "whereCon")
    private java.lang.String whereCon;
	/**订单类型*/
//    @Excel(name = "订单类型", width = 15, dictTable = "ba_order_type", dicText = "order_type_name", dicCode = "order_type_code")
    @Dict(dictTable = "ba_order_type", dicText = "order_type_name", dicCode = "order_type_code")
    @ApiModelProperty(value = "订单类型")
    private java.lang.String orderTypeCode;
	/**三方客户*/
	//@Excel(name = "三方客户", width = 15, dictTable = "md_cus_other", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
    @Dict(dictTable = "md_cus_other", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
    @ApiModelProperty(value = "三方客户")
    private java.lang.String ocusCode;
	/**三方客户名称*/
	//@Excel(name = "三方客户名称", width = 15)
    @ApiModelProperty(value = "三方客户名称")
    private java.lang.String ocusName;
	/**客户订单号*/
//	@Excel(name = "客户订单号", width = 15)
    @ApiModelProperty(value = "客户订单号")
    private java.lang.String imCusCode;
	/**打印状态*/
//	@Excel(name = "打印状态", width = 15)
    @ApiModelProperty(value = "打印状态")
    private java.lang.String printStatus;
	/**对接单据类型*/
	//@Excel(name = "对接单据类型", width = 15)
    @ApiModelProperty(value = "对接单据类型")
    private java.lang.String piClass;
	/**账套*/
	//@Excel(name = "账套", width = 15)
    @ApiModelProperty(value = "账套")
    private java.lang.String piMaster;

    @ApiModelProperty(value = "是否加急")
//    private java.lang.String urgent="N";
    private java.lang.String urgent;

    private String cusName;

    @ApiModelProperty(value = "挂单标记")
    private java.lang.String flag;

    @ApiModelProperty(value = "订单支付金额")
    private BigDecimal orderPayAmount;

    @TableField(exist = false)
    @ApiModelProperty(value = "报关数据")
    private WMSUserOrderPushData wmsUserOrderPushData;
    @ApiModelProperty(value = "缺货标记")
    private java.lang.String qhSta;

    /**仓库代码*/
//    @Excel(name = "仓库代码", width = 15)
    @ApiModelProperty(value = "仓库代码")
    private java.lang.String storeCode;
    /**仓库名称*/
//    @Excel(name = "仓库名称", width = 15)
    @ApiModelProperty(value = "仓库名称")
    private java.lang.String storeName;

    @TableField(exist = false)
    List<WmOmNoticeI> wmOmNoticeIList;

    //门店退货入库、配送差异入库
//    @Excel(name = "入库类型", width = 15)
    @ApiModelProperty(value = "入库类型")
    private String orderTypes;


    private String u8Id;

    @Excel(name = "装箱单号", width = 15)
    @ApiModelProperty(value = "装箱单号")
    private String u8Djcode1;
    private String u8Djcode2;
    //新零售返回单号
    @Excel(name = "采购单号", width = 15)
    @ApiModelProperty(value = "采购单号")
    private String u8ReturnCode;
    /**租户*/
    // @Excel(name = "租户", width = 15)
    @ApiModelProperty(value = "租户")
    private Integer tenantId;

    //出库主op号
    @Excel(name = "主op号", width = 15)
    @ApiModelProperty(value = "主op号")
    private String remarks;

    //关联单号类型
    @Excel(name = "关联单号类型", width = 15)
    @ApiModelProperty(value = "关联单号类型")
    private String relevanceNoType;

    //关联单号类型
    @Excel(name = "成品商品编码", width = 15)
    @ApiModelProperty(value = "成品商品编码")
    private String finishedGoodsCode;


    //采购包装日期
    private String packingDate;

    //截止日期
    private String stopDate;

    @TableField(exist = false)
    private String goodsName;
    @TableField(exist = false)
    private String goodsId;
    @TableField(exist = false)
    private java.lang.String classification;
    @TableField(exist = false)
    private String zuixiaoshul;
    @TableField(exist = false)
    private String shpmiaoshu;


    //出货方式
    @Excel(name = "出货方式", width = 15)
    @ApiModelProperty(value = "出货方式")
    private String shipmentWay;

    //出货地址
    @Excel(name = "出货地址", width = 15)
    @ApiModelProperty(value = "出货地址")
    private String shipmentAddress;


//    装箱单号    private String u8Djcode1;

}
