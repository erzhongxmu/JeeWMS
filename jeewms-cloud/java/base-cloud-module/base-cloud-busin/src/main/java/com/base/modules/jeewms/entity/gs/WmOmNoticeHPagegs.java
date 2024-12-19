package com.base.modules.jeewms.entity.gs;

import com.baomidou.mybatisplus.annotation.TableField;
import com.base.modules.jeewms.entity.WMSUserOrderPushData;
import com.base.modules.jeewms.entity.WmOmNoticeI;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
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
public class WmOmNoticeHPagegs {

	/**主键*/
	@ApiModelProperty(value = "主键")
	private String id;
	/**创建人名称*/
	// @Excel(name = "创建人名称", width = 15)
	@ApiModelProperty(value = "创建人名称")
	private String createName;
	/**创建人登录名称*/
	@ApiModelProperty(value = "创建人登录名称")
	private String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
	private Date createTime;
	/**更新人名称*/
	//// @Excel(name = "更新人名称", width = 15)
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
	/**所属公司*/
	//// @Excel(name = "所属公司", width = 15)
	@ApiModelProperty(value = "所属公司")
	private String sysCompanyCode;
	/**客户*/
	//@Excel(name = "客户", width = 15)
	//@ApiModelProperty(value = "客户")
//	@Excel(name = "供应商名称", width = 15)
	@ApiModelProperty(value = "供应商")
	private String cusCode;
	/**要求交货时间*/
	@Excel(name = "要求交货时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "要求交货时间")
	private Date delvData;
	/**收货人*/
	@Excel(name = "收货人", width = 15)
	@ApiModelProperty(value = "收货人")
	private String delvMember;
	/**收货人电话*/
	@Excel(name = "收货人电话", width = 15)
	@ApiModelProperty(value = "收货人电话")
	private String delvMobile;
	/**收货人地址*/
	@Excel(name = "收货人地址", width = 15)
	@ApiModelProperty(value = "收货人地址")
	private String delvAddr;
	/**承运人*/
	@Excel(name = "承运人", width = 15)
	@ApiModelProperty(value = "承运人")
	private String reMember;
	/**承运人电话*/
	//@Excel(name = "承运人电话", width = 15)
	@ApiModelProperty(value = "承运人电话")
	private String reMobile;
	/**承运人车号*/
	//@Excel(name = "承运人车号", width = 15)
	@ApiModelProperty(value = "承运人车号")
	private String reCarno;
	/**发货月台*/
	//@Excel(name = "发货月台", width = 15)
	@ApiModelProperty(value = "发货月台")
	private String omPlatNo;
	/**状态*/
	@Excel(name = "状态", width = 15)
	@ApiModelProperty(value = "状态")
	private String omSta;
	/**出货单号*/
	@Excel(name = "出货单号", width = 15)
	@ApiModelProperty(value = "出货单号")
	private String omNoticeId;
	/**附件*/
	//@Excel(name = "附件", width = 15)
	@ApiModelProperty(value = "附件")
	private String fuJian;
	/**所属部门*/
	//@Excel(name = "所属部门", width = 15)
	@ApiModelProperty(value = "所属部门")
	private String readOnly;
	/**whereCon*/
	//@Excel(name = "whereCon", width = 15)
	@ApiModelProperty(value = "whereCon")
	private String whereCon;
	/**订单类型*/
	@Excel(name = "订单类型", width = 15)
	@ApiModelProperty(value = "订单类型")
	private String orderTypeCode;
	/**三方客户*/
	//@Excel(name = "三方客户", width = 15)
	@ApiModelProperty(value = "三方客户")
	private String ocusCode;
	/**三方客户名称*/
	//@Excel(name = "三方客户名称", width = 15)
	@ApiModelProperty(value = "三方客户名称")
	private String ocusName;
	/**客户订单号*/
	//@Excel(name = "客户订单号", width = 15)
	//@ApiModelProperty(value = "客户订单号")
//	@Excel(name = "订单号", width = 15)
	@ApiModelProperty(value = "订单号")
	private String imCusCode;
	/**打印状态*/
//	@Excel(name = "打印状态", width = 15)
	@ApiModelProperty(value = "打印状态")
	private String printStatus;
	/**对接单据类型*/
	//@Excel(name = "对接单据类型", width = 15)
	@ApiModelProperty(value = "对接单据类型")
	private String piClass;
	/**账套*/
	//@Excel(name = "账套", width = 15)
	@ApiModelProperty(value = "账套")
	private String piMaster;

	//@ExcelCollection(name="出货详情")
	@ApiModelProperty(value = "出货详情")
	private List<WmOmNoticeIgs> wmOmNoticeIList;
	/**备注*/
//	@Excel(name = "备注", width = 15)
	@ApiModelProperty(value = "备注")
	private String omBeizhu;

	private String flag;

	@ApiModelProperty(value = "订单支付金额")
	private BigDecimal orderPayAmount;

	@ApiModelProperty(value = "报关数据")
	@TableField(exist = false)
	private WMSUserOrderPushData wmsUserOrderPushData;

	/**仓库代码*/
//	@Excel(name = "仓库代码", width = 15)
	@ApiModelProperty(value = "仓库代码")
	private String storeCode;
	/**仓库名称*/
//	@Excel(name = "仓库名称", width = 15)
	@ApiModelProperty(value = "仓库名称")
	private String storeName;

	@Excel(name = "ERP单号", width = 15)
	private String u8ReturnCode;
	private String orderTypes;
	private String u8Id;
	private String u8Djcode1;
	private String u8Djcode2;
	private Integer tenantId;

	private String packingDate;
	private String stopDate;
}
