package com.base.modules.jeewms.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.base.common.aspect.annotation.Dict;
import com.base.modules.jeewms.entity.WMSUserOrderPushData;
import com.base.modules.jeewms.entity.WmOmNoticeI;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description: 出库管理
 * @Author: base-boot
 * @Date:   2021-06-04
 * @Version: V1.0
 */
@Data
@ApiModel(value="wm_om_notice_hPage对象", description="出库管理")
public class WmOmNoticeHPage {

	/**主键*/
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
	/**客户*/
	@Excel(name = "客户", width = 15, dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
	@Dict(dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
	@ApiModelProperty(value = "客户")
	private java.lang.String cusCode;
	/**要求交货时间*/
	@Excel(name = "要求交货时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
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
//	@Excel(name = "承运人", width = 15)
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
	//@Excel(name = "发货月台", width = 15)
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
	@Excel(name = "出货单号", width = 15)
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
	@Excel(name = "订单类型", width = 15)
	@ApiModelProperty(value = "订单类型")
	private java.lang.String orderTypeCode;
	/**三方客户*/
	//@Excel(name = "三方客户", width = 15)
	@ApiModelProperty(value = "三方客户")
	private java.lang.String ocusCode;
	/**三方客户名称*/
	//@Excel(name = "三方客户名称", width = 15)
	@ApiModelProperty(value = "三方客户名称")
	private java.lang.String ocusName;
	/**客户订单号*/
	//@Excel(name = "客户订单号", width = 15)
	//@ApiModelProperty(value = "客户订单号")
	@Excel(name = "订单号", width = 15)
	@ApiModelProperty(value = "订单号")
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



	private java.lang.String flag;

	@ApiModelProperty(value = "订单支付金额")
	private BigDecimal orderPayAmount;

	@ApiModelProperty(value = "报关数据")
	@TableField(exist = false)
	private WMSUserOrderPushData wmsUserOrderPushData;

	/**仓库代码*/
	@Excel(name = "仓库代码", width = 15)
	@ApiModelProperty(value = "仓库代码")
	private java.lang.String storeCode;
	/**仓库名称*/
	@Excel(name = "仓库名称", width = 15)
	@ApiModelProperty(value = "仓库名称")
	private java.lang.String storeName;

	@Excel(name = "采购单号", width = 15)
	@ApiModelProperty(value = "采购单号")
	private String u8ReturnCode;
	private String orderTypes;
	private String u8Id;

	@Excel(name = "装箱单号", width = 15)
	@ApiModelProperty(value = "装箱单号")
	private String u8Djcode1;
	private String u8Djcode2;
	private Integer tenantId;


	//出库主op号
	@Excel(name = "主op号", width = 15)
	@ApiModelProperty(value = "主op号")
	private String remarks;

	private String packingDate;
	private String stopDate;


	//出货方式
	@Excel(name = "出货方式", width = 15)
	@ApiModelProperty(value = "出货方式")
	private String shipmentWay;

	//出货地址
	@Excel(name = "出货地址", width = 15)
	@ApiModelProperty(value = "出货地址")
	private String shipmentAddress;

	@ExcelCollection(name="出货详情")
	@ApiModelProperty(value = "出货详情")
	private List<WmOmNoticeI> wmOmNoticeIList;
}
