package com.base.modules.jeeerp.vo;

import com.base.common.aspect.annotation.Dict;
import com.base.modules.jeeerp.entity.BusiOrdCraft;
import com.base.modules.jeeerp.entity.BusiPrdOrdItem;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Description: busi_prd_ord
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
@Data
@ApiModel(value="busi_prd_ordPage对象", description="质检订单")
public class BusiPrdOrdsPage {
	@Excel(name = "计划期限", width = 15)
	@ApiModelProperty(value = "计划期限")
	private String query21;

	@Excel(name = "子PO", width = 15)
	@ApiModelProperty(value = "子PO")
	private String query14;

	@Excel(name = "客户", width = 15)
	@ApiModelProperty(value = "客户")
	@Dict(dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
	private String query24;

	/**客户编码*/
	@Excel(name = "企业属性", width = 15)
	@ApiModelProperty(value = "企业属性")
	private String XingYeFenLei;

	@Excel(name = "SKU", width = 15)
	@ApiModelProperty(value = "SKU")
	private String query10;

	@Excel(name = "品名", width = 15)
	@ApiModelProperty(value = "品名")
	private String query11;

	@Excel(name = "订单数量", width = 15)
	@ApiModelProperty(value = "订单数量")
	private Double num01;

	@Excel(name = "单位", width = 15)
	@ApiModelProperty(value = "单位")
	private String query12;


	@Excel(name = "单价", width = 15)
	@ApiModelProperty(value = "单价")
	private String query32;

	@Excel(name = "税率", width = 15)
	@ApiModelProperty(value = "税率")
	private String query35;

	@Excel(name = "人工费", width = 15)
	@ApiModelProperty(value = "人工费")
	private String query19;

	@Excel(name = "工时", width = 15)
	@ApiModelProperty(value = "工时")
	private String query31;

	@Excel(name = "单价(含加工费)", width = 15)
	@ApiModelProperty(value = "单价(含加工费)")
	private String query33;


	@Excel(name = "验货数量", width = 15)
	@ApiModelProperty(value = "验货数量")
	private String query34;


	@Excel(name = "检验类型", width = 15)
	@ApiModelProperty(value = "检验类型")
	private String query15;

	@Excel(name = "出货日期", width = 15)
	@ApiModelProperty(value = "出货日期")
	private String query18;

	@Excel(name = "跟单员", width = 15)
	@ApiModelProperty(value = "跟单员")
	private String query28;

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

	@Excel(name = "创建人", width = 15)
	@ApiModelProperty(value = "创建人")
	private String createName;

	@Excel(name = "创建日期", width = 15)
	@ApiModelProperty(value = "创建日期")
	private String createTime;

	@Excel(name = "完成时间", width = 15)
	@ApiModelProperty(value = "完成时间")
	private String query22;

	@Excel(name = "备注", width = 15)
	@ApiModelProperty(value = "备注")
	private String text01;
//
//	/**主键*/
//	@ApiModelProperty(value = "主键")
//	private String id;
//	/**创建人名称*/
////	@Excel(name = "创建人名称", width = 15)
//	@ApiModelProperty(value = "创建人名称")
//	private String createName;
//	/**创建人登录名称*/
//	@ApiModelProperty(value = "创建人登录名称")
//	private String createBy;
//	/**创建日期*/
//	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
//	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
//	@ApiModelProperty(value = "创建日期")
//	private Date createTime;
//	/**更新人名称*/
//	//@Excel(name = "更新人名称", width = 15)
//	@ApiModelProperty(value = "更新人名称")
//	private String updateName;
//	/**更新人登录名称*/
//	@ApiModelProperty(value = "更新人登录名称")
//	private String updateBy;
//	/**更新日期*/
//	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
//	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
//	@ApiModelProperty(value = "更新日期")
//	private Date updateTime;
//	/**所属部门*/
//	@ApiModelProperty(value = "所属部门")
//	private String sysOrgCode;
//	/**单据类型*/
//	//@Excel(name = "单据类型", width = 15)
//	@ApiModelProperty(value = "单据类型")
//	private String query01;
//	/**单据状态*/
//	@Excel(name = "单据状态", width = 15)
//	@ApiModelProperty(value = "单据状态")
//	private String query02;
//	/**单据日期*/
//	//@Excel(name = "单据日期", width = 15)
//	@ApiModelProperty(value = "单据日期")
//	private String query03;
//	/**单号*/
//	@Excel(name = "单号", width = 15)
//	@ApiModelProperty(value = "单号")
//	private String query04;
//	/**公司*/
//	//@Excel(name = "公司", width = 15)
//	@ApiModelProperty(value = "公司")
//	private String query05;
//	/**工厂*/
//	//@Excel(name = "工厂", width = 15)
//	@ApiModelProperty(value = "工厂")
//	private String query06;
//	/**库存地点*/
//	//@Excel(name = "库存地点", width = 15)
//	@ApiModelProperty(value = "库存地点")
//	private String query07;
//	/**query08*/
//	//@Excel(name = "query08", width = 15)
//	@ApiModelProperty(value = "query08")
//	private String query08;
//	/**query09*/
//	//@Excel(name = "query09", width = 15)
//	@ApiModelProperty(value = "query09")
//	private String query09;
//	/**商品编码/成品*/
//	//@Excel(name = "商品编码/成品", width = 15)
//	@ApiModelProperty(value = "商品编码/成品")
//	private String query10;
//	/**商品名称/成品*/
//	//@Excel(name = "商品名称/成品", width = 15)
//	@ApiModelProperty(value = "商品名称/成品")
//	private String query11;
//	/**单位*/
//	//@Excel(name = "单位", width = 15)
//	@ApiModelProperty(value = "单位")
//	private String query12;
//	/**主PO号*/
//	@Excel(name = "主PO号", width = 15)
//	@ApiModelProperty(value = "主PO号")
//	private String query13;
//	/**子批次*/
//	//@Excel(name = "子批次", width = 15)
//	@ApiModelProperty(value = "子批次")
//	private String query14;
//	/**检验类型*/
//	@Excel(name = "检验类型", width = 15)
//	@ApiModelProperty(value = "检验类型")
//	private String query15;
//	/**采购人*/
//	@Excel(name = "采购人", width = 15)
//	@ApiModelProperty(value = "采购人")
//	private String query16;
//	/**query17*/
//	//@Excel(name = "query17", width = 15)
//	@ApiModelProperty(value = "query17")
//	private String query17;
//	/**query18*/
//	//@Excel(name = "query18", width = 15)
//	@ApiModelProperty(value = "query18")
//	private String query18;
//	/**query19*/
//	//@Excel(name = "query19", width = 15)
//	@ApiModelProperty(value = "query19")
//	private String query19;
//	/**query20*/
//	//@Excel(name = "query20", width = 15)
//	@ApiModelProperty(value = "query20")
//	private String query20;
//	/**期限*/
//	@Excel(name = "期限", width = 15)
//	@ApiModelProperty(value = "期限")
//	private String query21;
//	/**query20*/
//	@Excel(name = "完成时间", width = 15)
//	@ApiModelProperty(value = "完成时间")
//	private String query22;
//	/**单号-行项目号*/
//	//@Excel(name = "单号-行项目号", width = 15)
//	@ApiModelProperty(value = "单号-行项目号")
//	private String query23;
//	/**query24*/
//	//@Excel(name = "query24", width = 15)
//	@ApiModelProperty(value = "query24")
//	private String query24;
//	/**query25*/
//	//@Excel(name = "query25", width = 15)
//	@ApiModelProperty(value = "query25")
//	private String query25;
//	/**query26*/
//	//@Excel(name = "query26", width = 15)
//	@ApiModelProperty(value = "query26")
//	private String query26;
//	/**query27*/
//	//@Excel(name = "query27", width = 15)
//	@ApiModelProperty(value = "query27")
//	private String query27;
//	/**query28*/
//	//@Excel(name = "query28", width = 15)
//	@ApiModelProperty(value = "query28")
//	private String query28;
//	/**query29*/
//	//@Excel(name = "query29", width = 15)
//	@ApiModelProperty(value = "query29")
//	private String query29;
//	/**query30*/
//	//@Excel(name = "query30", width = 15)
//	@ApiModelProperty(value = "query30")
//	private String query30;
//	/**query31*/
//	//@Excel(name = "query31", width = 15)
//	@ApiModelProperty(value = "query31")
//	private String query31;
//	/**数量*/
//	//@Excel(name = "数量", width = 15)
//	@ApiModelProperty(value = "数量")
//	private Double num01;
//	/**未清数量*/
//	//@Excel(name = "未清数量", width = 15)
//	@ApiModelProperty(value = "未清数量")
//	private Double num02;
//	/**已完成数量*/
//	//@Excel(name = "已完成数量", width = 15)
//	@ApiModelProperty(value = "已完成数量")
//	private Double num03;
//	/**单价*/
//	@Excel(name = "单价", width = 15)
//	@ApiModelProperty(value = "单价")
//	private Double num04;
//	/**num05*/
//	//@Excel(name = "num05", width = 15)
//	@ApiModelProperty(value = "num05")
//	private Double num05;
//	/**含税单价*/
//	//@Excel(name = "含税单价", width = 15)
//	@ApiModelProperty(value = "含税单价")
//	private Double num06;
//	/**含税总价*/
//	//@Excel(name = "含税总价", width = 15)
//	@ApiModelProperty(value = "含税总价")
//	private Double num07;
//	/**总金额*/
//	//@Excel(name = "总金额", width = 15)
//	@ApiModelProperty(value = "总金额")
//	private Double num08;
//	/**含税总金额*/
//	//@Excel(name = "含税总金额", width = 15)
//	@ApiModelProperty(value = "含税总金额")
//	private Double num09;
//	/**税率*/
//	//@Excel(name = "税率", width = 15)
//	@ApiModelProperty(value = "税率")
//	private Double num10;
//	/**未清含税总金额*/
//	//@Excel(name = "未清含税总金额", width = 15)
//	@ApiModelProperty(value = "未清含税总金额")
//	private Double num11;
//	/**未清总金额*/
//	//@Excel(name = "未清总金额", width = 15)
//	@ApiModelProperty(value = "未清总金额")
//	private Double num12;
//	/**num13*/
//	//@Excel(name = "num13", width = 15)
//	@ApiModelProperty(value = "num13")
//	private Double num13;
//	/**num14*/
//	//@Excel(name = "num14", width = 15)
//	@ApiModelProperty(value = "num14")
//	private Double num14;
//	/**num15*/
//	//@Excel(name = "num15", width = 15)
//	@ApiModelProperty(value = "num15")
//	private Double num15;
//	/**关联单据类型*/
//	//@Excel(name = "关联单据类型", width = 15)
//	@ApiModelProperty(value = "关联单据类型")
//	private String link01;
//	/**关联单号*/
//	//@Excel(name = "关联单号", width = 15)
//	@ApiModelProperty(value = "关联单号")
//	private String link02;
//	/**link03*/
//	//@Excel(name = "link03", width = 15)
//	@ApiModelProperty(value = "link03")
//	private String link03;
//	/**link04*/
//	//@Excel(name = "link04", width = 15)
//	@ApiModelProperty(value = "link04")
//	private String link04;
//	/**link05*/
//	//@Excel(name = "link05", width = 15)
//	@ApiModelProperty(value = "link05")
//	private String link05;
//	/**备注1*/
//	@Excel(name = "备注", width = 15)
//	@ApiModelProperty(value = "备注")
//	private String text01;
//	/**备注2*/
//	//@Excel(name = "备注2", width = 15)
//	@ApiModelProperty(value = "备注2")
//	private String text02;
//	/**text03*/
//	//@Excel(name = "text03", width = 15)
//	@ApiModelProperty(value = "text03")
//	private String text03;
//	/**text04*/
//	//@Excel(name = "text04", width = 15)
//	@ApiModelProperty(value = "text04")
//	private String text04;
//	/**text05*/
//	//@Excel(name = "text05", width = 15)
//	@ApiModelProperty(value = "text05")
//	private String text05;
//	/**单据附件*/
//	//@Excel(name = "单据附件", width = 15)
//	@ApiModelProperty(value = "单据附件")
//	private String attr1;
//	/**attr2*/
//	//@Excel(name = "attr2", width = 15)
//	@ApiModelProperty(value = "attr2")
//	private String attr2;
//	/**attr3*/
//	//@Excel(name = "attr3", width = 15)
//	@ApiModelProperty(value = "attr3")
//	private String attr3;
//	/**attr4*/
//	//@Excel(name = "attr4", width = 15)
//	@ApiModelProperty(value = "attr4")
//	private String attr4;
//	/**attr5*/
//	//@Excel(name = "attr5", width = 15)
//	@ApiModelProperty(value = "attr5")
//	private String attr5;
//
	@ExcelCollection(name="busi_prd_ord_item")
	@ApiModelProperty(value = "busi_prd_ord_item")
	private List<BusiPrdOrdItem> busiPrdOrdItemList;
//	/*@ExcelCollection(name="busi_ord_craft")
//	@ApiModelProperty(value = "busi_ord_craft")
//	private List<BusiOrdCraft> busiOrdCraftList;*/



//	@Excel(name = "attr5", width = 15)
//	@ApiModelProperty(value = "attr5")
//	private String attr5;

















}
