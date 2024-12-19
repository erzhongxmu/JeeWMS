package com.base.modules.jeewms.vo;

import com.base.common.aspect.annotation.Dict;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.base.modules.jeewms.entity.WmImNoticeI;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Description: wm_im_notice_h
 * @Author: base-boot
 * @Date:   2021-06-04
 * @Version: V1.0
 */
@Data
@ApiModel(value="wm_im_notice_hPage对象", description="wm_im_notice_h")
public class WmImNoticeHPage {

	/**主键*/
	@ApiModelProperty(value = "主键")
	private String id;
	/**创建人名称*/
	//// @Excel(name = "创建人名称", width = 15)
	@ApiModelProperty(value = "创建人名称")
	private String createName;
	/**创建人名称*/
	@ApiModelProperty(value = "创建人名称")
	private String createBy;
	/**创建日期*/
	//@Excel(name = "创建日期", width = 15, format = "yyyy-MM-dd")
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
	/**客户编码*/
	//@Excel(name = "客户编码", width = 15)
	//@ApiModelProperty(value = "客户编码")
	@Excel(name = "客户编码", width = 15 ,orderNum = "6")
	@ApiModelProperty(value = "客户编码")
	private String cusCode;

	@Excel(name = "客户名称", width = 15     ,orderNum = "5")
	@ApiModelProperty(value = "客户")
	private String cusName;
	/**预计到货时间*/
	@Excel(name = "预计到货时间", width = 15, format = "yyyy-MM-dd"      ,orderNum = "4")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "预计到货时间")
	private Date imData;
	/**客户订单号*/
	//@Excel(name = "客户订单号", width = 15)
	//@ApiModelProperty(value = "客户订单号")
//    @Excel(name = "订单号", width = 15)
//	@Excel(name = "", width = 15  ,orderNum = "8")
	@ApiModelProperty(value = "订单号")
	private String imCusCode;
	/**司机*/
//	@Excel(name = "司机", width = 15  ,orderNum = "6")
	@ApiModelProperty(value = "司机")
	private String imCarDri;
	/**司机电话*/
//	@Excel(name = "司机电话", width = 15  ,orderNum = "7")
	@ApiModelProperty(value = "司机电话")
	private String imCarMobile;
	/**车号*/
//	@Excel(name = "车号", width = 15  ,orderNum = "8")
	@ApiModelProperty(value = "车号")
	private String imCarNo;
	/**订单类型*/
	@Excel(name = "订单类型", width = 15,dicCode = "im_order_type"      ,orderNum = "3")
	@ApiModelProperty(value = "订单类型")
	private String orderType;
	/**月台*/
	//@Excel(name = "月台", width = 15)
	@ApiModelProperty(value = "月台")
	private String platformCode;
	/**备注*/
	@Excel(name = "主PO号", width = 15 ,orderNum = "2")
	@ApiModelProperty(value = "主PO号")
	private String imBeizhu;
	/**单据状态*/
	//@Excel(name = "单据状态", width = 15)
	@ApiModelProperty(value = "单据状态")
	private String imSta;
	/**进货通知单号*/
	@Excel(name = "进货通知单号", width = 15      ,orderNum = "1")
	@ApiModelProperty(value = "进货通知单号")
	private String noticeId;
	/**附件*/
	//@Excel(name = "附件", width = 15)
	@ApiModelProperty(value = "附件")
	private String fuJian;
	/**readOnly*/
	//@Excel(name = "readOnly", width = 15)
	@ApiModelProperty(value = "readOnly")
	private String readOnly;
	/**whereCon*/
	//@Excel(name = "whereCon", width = 15)
	@ApiModelProperty(value = "whereCon")
	private String whereCon;
	/**供应商编码*/
	@Excel(name = "供应商编码", width = 15)
	@ApiModelProperty(value = "供应商编码")
	private String supCode;
	/**供应商名称*/
//	@Excel(name = "供应商名称", width = 15)
	@ApiModelProperty(value = "供应商名称")
	private String supName;
	/**对接单据类型*/
	//@Excel(name = "对接单据类型", width = 15)
	@ApiModelProperty(value = "对接单据类型")
	private String piClass;
	/**账套*/
	//@Excel(name = "账套", width = 15)
	@ApiModelProperty(value = "账套")
	private String piMaster;

	/**税率*/
	//@Excel(name = "税率", width = 15)
	@ApiModelProperty(value = "税率")
	private String taxRate;



	@ApiModelProperty(value = "是否加急")
	private java.lang.String urgent="N";

	/**仓库代码*/
//	@Excel(name = "仓库代码", width = 15)
	@ApiModelProperty(value = "仓库代码")
	private java.lang.String storeCode;
	/**仓库名称*/
//	@Excel(name = "仓库名称", width = 15)
	@ApiModelProperty(value = "仓库名称")
	private java.lang.String storeName;

	private String u8Id;
	private String u8Dhcode;
	private String u8Cgcode;
	@Excel(name = "销售单号", width = 15     ,orderNum = "0")
	private String u8ReturnCode;
	private String u8Sta;

	private String orderTypes;
	private Integer tenantId;

	@ExcelCollection(name="商品",orderNum = "7")
	@ApiModelProperty(value = "商品")
	private List<WmImNoticeI> wmImNoticeIList;

	private String imclientcode;
	@Excel(name = "采购", width = 15)
	private String purchasename;
	@Excel(name = "内部发票号", width = 15)
	private String astreanum;


	/**单据备注*/
//	@Excel(name = "仓库名称", width = 15)
	@ApiModelProperty(value = "单据备注")
	private java.lang.String orderRemark;

}
