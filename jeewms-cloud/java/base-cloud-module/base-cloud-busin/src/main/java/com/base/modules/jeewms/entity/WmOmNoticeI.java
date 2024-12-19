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

/**
 * @Description: 出货详情
 * @Author: base-boot
 * @Date:   2021-06-04
 * @Version: V1.0
 */
@ApiModel(value="wm_om_notice_h对象", description="出库管理")
@Data
@TableName("wm_om_notice_i")
public class WmOmNoticeI implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_UUID)
	@ApiModelProperty(value = "主键")
	private java.lang.String id;
	/**创建人名称*/
	//// @Excel(name = "创建人名称", width = 15)
	@ApiModelProperty(value = "创建人名称")
	private java.lang.String createName;
	/**创建人登录名称*/
	@ApiModelProperty(value = "创建人登录名称")
	private java.lang.String createBy;
	/**创建日期*/
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
	//@Excel(name = "所属公司", width = 15, dictTable = "ba_com", dicText = "com_zh_aname", dicCode = "com_code")
	@Dict(dictTable = "ba_com", dicText = "com_zh_aname", dicCode = "com_code")
	@ApiModelProperty(value = "所属公司")
	private java.lang.String sysCompanyCode;
	/**出货通知ID*/
//	@ApiModelProperty(value = "出货通知ID")
	@Excel(name = "出货通知单号", width = 15,orderNum = "5")
	private java.lang.String omNoticeId;
	/**出货商品*/
	@Excel(name = "商品编码", width = 15,orderNum = "5")
	@ApiModelProperty(value = "出货商品")
	private java.lang.String goodsId;
	/**出货数量*/
	@Excel(name = "出货数量", width = 15,orderNum = "7")
	@ApiModelProperty(value = "出货数量")
	private java.lang.String goodsQua;
	/**出货单位*/
	@Excel(name = "单位", width = 15,orderNum = "8")
	@ApiModelProperty(value = "出货单位")
	private java.lang.String goodsUnit;
	/**生产日期*/
//	@Excel(name = "生产日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "生产日期")
	private java.util.Date goodsProData;
	/**批次*/
	@Excel(name = "批次", width = 15)
	@ApiModelProperty(value = "批次")
	private java.lang.String goodsBatch;
	/**出货仓位*/
//	@Excel(name = "出货仓位", width = 15)
	@ApiModelProperty(value = "出货仓位")
	private java.lang.String binOm;
	/**已出货数量*/
	//@Excel(name = "已出货数量", width = 15)
	@ApiModelProperty(value = "已出货数量")
	private java.lang.String goodsQuaok;
	/**预约出货时间*/
//	@Excel(name = "预约出货时间", width = 15,orderNum = "3")
	@ApiModelProperty(value = "预约出货时间")
	private java.lang.String delvData;
	/**客户*/
//	@Excel(name = "供应商编码", width = 15,orderNum = "1")
	@Dict(dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
	@ApiModelProperty(value = "客户")
	private java.lang.String cusCode;
	/**客户名称*/
//	@Excel(name = "供应商名称", width = 15,orderNum = "2")
	@ApiModelProperty(value = "客户名称")
	private java.lang.String cusName;
	/**商品名称*/
	//@Excel(name = "商品名称", width = 15)
	@ApiModelProperty(value = "商品名称")
	private java.lang.String goodsText;
	/**波次号*/
	//@Excel(name = "波次号", width = 15)
	@ApiModelProperty(value = "波次号")
	private java.lang.String waveId;
	/**状态*/
	//@Excel(name = "状态", width = 15)
	@ApiModelProperty(value = "状态")
	private java.lang.String omSta;
	/**基本单位*/
	@Excel(name = "基本单位", width = 15)
	@ApiModelProperty(value = "基本单位")
	private java.lang.String baseUnit;
	/**基本单位数量*/
//	@Excel(name = "基本单位数量", width = 15)
	@ApiModelProperty(value = "基本单位数量")
	private java.lang.String baseGoodscount;
	/**下架计划生成*/
	@Excel(name = "下架任务是否生成", width = 15)
	@ApiModelProperty(value = "下架计划生成")
	private java.lang.String planSta;
	/**商品名称*/
	@Excel(name = "商品名称", width = 15,orderNum = "6")
	@ApiModelProperty(value = "商品名称")
	private java.lang.String goodsName;
	/**其他系统ID*/
	//@Excel(name = "其他系统ID", width = 15)
	@ApiModelProperty(value = "其他系统ID")
	private java.lang.String otherId;
	/**binId*/
//	@Excel(name = "库位", width = 15)
	@ApiModelProperty(value = "binId")
	private java.lang.String binId;
	/**客户订单号*/
//	@Excel(name = "客户订单号", width = 15,orderNum = "4")
	@ApiModelProperty(value = "客户订单号")
	private java.lang.String imCusCode;
	/**备注*/
	@Excel(name = "备注", width = 15)
	@ApiModelProperty(value = "备注")
	private java.lang.String omBeiZhu;
	/**bzhiQi*/
	//@Excel(name = "bzhiQi", width = 15)
	@ApiModelProperty(value = "bzhiQi")
	private java.lang.String bzhiQi;
	/**产品属性*/
	//@Excel(name = "产品属性", width = 15)
	@ApiModelProperty(value = "产品属性")
	private java.lang.String chpShuXing;
	/**商品条码*/
//	@Excel(name = "商品条码", width = 15)
	@ApiModelProperty(value = "商品条码")
	private java.lang.String barcode;

	@ApiModelProperty(value = "挂单标识")
	private java.lang.String flag;

	@ApiModelProperty(value = "待出库数量")
	private java.lang.String waitQua;

	@ApiModelProperty(value = "商品单价")
	private BigDecimal goodsUnitPrice;

	/**仓库代码*/
//	@Excel(name = "仓库代码", width = 15)
	@ApiModelProperty(value = "仓库代码")
	private java.lang.String storeCode;
	/**仓库名称*/
//	@Excel(name = "仓库名称", width = 15)
	@ApiModelProperty(value = "仓库名称")
	private java.lang.String storeName;

	//@Excel(name = "托盘", width = 15)
	@ApiModelProperty(value = "托盘")
	private String tinId;

	private String u8Djid1;
	private String u8Djid2;
	/**租户*/
	// @Excel(name = "租户", width = 15)
	@ApiModelProperty(value = "租户")
	private Integer tenantId;



	//国声字段
	//生产订单编号
	@ApiModelProperty(value = "生产订单编号")
	private String procode;
	//领料日期
	@ApiModelProperty(value = "领料日期")
	private String pickingdate;
	//单据创建人
	@ApiModelProperty(value = "单据创建人")
	private String ucreatename;
	//领料人
	@ApiModelProperty(value = "领料人")
	private String pickername;
	//仓管员
	@ApiModelProperty(value = "仓管员")
	private String warehousepername;
	//发货部门
	@ApiModelProperty(value = "发货部门编码")
	private String shipdepcode;
	@ApiModelProperty(value = "发货部门名称")
	private String shipdepname;
	//发货组织
	@ApiModelProperty(value = "发货组织编码")
	private String shiporgcode;
	@ApiModelProperty(value = "发货组织名称")
	private String shiporgname;
	//审核
	@Excel(name = "是否报关", width = 15)
	@ApiModelProperty(value = "审核")
	private String checkname;

	//字段
	//物料英文名称
	//审核
	/*@ApiModelProperty(value = "是否报关  否：0 报关：1")
	private String checkname;*/

	@Excel(name = "商品中文名称", width = 15)
	@ApiModelProperty(value = "商品英文名称")
	private String ywMingCheng;
	//物料成品标识
	private String stickehstatus;
	//托盘
	private String stickehno;
	//品名简称
	private String goodsjianchen;
	//箱卖
	private String shippingmark;
	//每箱数量
	private String qtypercarton;
	//总箱数
	private String totalqtypercarton;
	//采购单号
	private String qbno;
	//内部发票号
	private String astreinv;
	//规格
	private String goodsguige;
	//产品属性英文
	private String goodsTypeEnname;
	//产品属性编码
	private String goodsTypeCode;
	//产品属性名称
	private String goodsTypeName;
	//商品颜色
	private String shpYanEnse;
	//纸箱长
	private String ctnsizel;
	//纸箱宽
	private String ctnsizew;
	//纸箱高
	private String ctnsizeh;
	//重量/箱
	private String weightctn;
	//体积/箱
	private String wolctn;
	//总体积
	private String totalvol;
	//总重量
	private String totalweight;
	//是否退税
	private String taxrefund;
	//开票品名
	private String billingproductname;
	//开票单位
	private String billingproductunit;
	//卖价-报关价格
	private String salespricewvat;
	//报关价格币种
	private String salespricecur;
	//总金额
	private String totalamount;
	//提单品名
	private String ladingname;
	//品牌
	private String logo;
	//海关商品编码
	private String hscode;
	//用途
	private String usagess;
	//申报要素
	private String declarationelements;
	//客户
	private String imclientcode;
	//销售发票号
	private String u8ReturnCode;

	@TableField(exist = false)
	private java.lang.String classification;

	@TableField(exist = false)
	private java.lang.String xianghao;
	@TableField(exist = false)
	private String contractlno;

	@TableField(exist = false)
	private String astreanum;

	@TableField(exist = false)
	private java.lang.String firstRq;
}
