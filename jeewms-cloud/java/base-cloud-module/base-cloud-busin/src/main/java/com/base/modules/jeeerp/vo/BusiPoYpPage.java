package com.base.modules.jeeerp.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.base.modules.jeeerp.entity.BusiPoItem;
import com.base.modules.util.CustomDoubleSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Description: busi_po
 * @Author: base-boot
 * @Date:   2022-11-23
 * @Version: V1.0
 */
@Data
@ApiModel(value="busi_poPage对象", description="busi_po")
public class BusiPoYpPage {

	/**主键*/
	@ApiModelProperty(value = "主键")
    private String id;
	/**创建人名称*/
	@Excel(name = "创建人名称", width = 15)
	@ApiModelProperty(value = "创建人名称")
    private String createName;
	/**创建人登录名称*/
	@ApiModelProperty(value = "创建人登录名称")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人名称*/
	@Excel(name = "更新人名称", width = 15)
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
	/**单据类型*/
	@Excel(name = "单据类型", width = 15)
	@ApiModelProperty(value = "单据类型")
    private String query01;
	/**单据状态*/
	@Excel(name = "单据状态", width = 15)
	@ApiModelProperty(value = "单据状态")
    private String query02;
	/**打样进度*/
	@Excel(name = "打样进度", width = 15)
	@ApiModelProperty(value = "打样进度")
	private String query0201;
	/**单据日期*/
	@Excel(name = "单据日期", width = 15)
	@ApiModelProperty(value = "单据日期")
    private String query03;
	/**单号*/
	@Excel(name = "单号", width = 15)
	@ApiModelProperty(value = "单号")
    private String query04;
	/**公司*/
	@Excel(name = "公司", width = 15)
	@ApiModelProperty(value = "公司")
    private String query05;
	/**工厂*/
	@Excel(name = "工厂", width = 15)
	@ApiModelProperty(value = "工厂")
    private String query06;
	/**库存地点*/
	@Excel(name = "库存地点", width = 15)
	@ApiModelProperty(value = "库存地点")
    private String query07;
	/**供应商编码*/
	@Excel(name = "供应商编码", width = 15)
	@ApiModelProperty(value = "供应商编码")
    private String query08;
	/**供应商名称*/
	@Excel(name = "供应商名称", width = 15)
	@ApiModelProperty(value = "供应商名称")
    private String query09;
	/**商品编码*/
	@Excel(name = "商品编码", width = 15)
	@ApiModelProperty(value = "商品编码")
    private String query10;
	/**商品名称*/
	@Excel(name = "商品名称", width = 15)
	@ApiModelProperty(value = "商品名称")
    private String query11;
	/**单位*/
	@Excel(name = "单位", width = 15)
	@ApiModelProperty(value = "单位")
    private String query12;
	/**主批次*/
	@Excel(name = "主批次", width = 15)
	@ApiModelProperty(value = "主批次")
    private String query13;
	/**子批次*/
	@Excel(name = "子批次", width = 15)
	@ApiModelProperty(value = "子批次")
    private String query14;
	/**检验类型*/
	@Excel(name = "检验类型", width = 15)
	@ApiModelProperty(value = "检验类型")
    private String query15;
	/**采购人*/
	@Excel(name = "采购人", width = 15)
	@ApiModelProperty(value = "采购人")
    private String query16;
	/**query17*/
	@Excel(name = "query17", width = 15)
	@ApiModelProperty(value = "query17")
    private String query17;
	/**query18*/
	@Excel(name = "query18", width = 15)
	@ApiModelProperty(value = "query18")
    private String query18;
	/**query19*/
	@Excel(name = "query19", width = 15)
	@ApiModelProperty(value = "query19")
    private String query19;
	/**query20*/
	@Excel(name = "query20", width = 15)
	@ApiModelProperty(value = "query20")
    private String query20;
	/**预计到货时间*/
	@Excel(name = "预计到货时间", width = 15)
	@ApiModelProperty(value = "预计到货时间")
    private String query21;
	/**query22*/
	@Excel(name = "query22", width = 15)
	@ApiModelProperty(value = "query22")
    private String query22;
	/**单号-行项目号*/
	@Excel(name = "单号-行项目号", width = 15)
	@ApiModelProperty(value = "单号-行项目号")
    private String query23;
	/**客户*/
	@Excel(name = "客户", width = 15)
	@ApiModelProperty(value = "客户")
    private String query24;
	/**query25*/
	@Excel(name = "query25", width = 15)
	@ApiModelProperty(value = "query25")
    private String query25;
	/**query26*/
	@Excel(name = "query26", width = 15)
	@ApiModelProperty(value = "query26")
    private String query26;
	/**query27*/
	@Excel(name = "query27", width = 15)
	@ApiModelProperty(value = "query27")
    private String query27;
	/**query28*/
	@Excel(name = "query28", width = 15)
	@ApiModelProperty(value = "query28")
    private String query28;
	/**query29*/
	@Excel(name = "query29", width = 15)
	@ApiModelProperty(value = "query29")
    private String query29;
	/**query30*/
	@Excel(name = "业务员", width = 15)
	@ApiModelProperty(value = "业务员")
    private String query30;
	@Excel(name = "业务类型", width = 15)
	@ApiModelProperty(value = "业务类型")
	private String query31;

	@Excel(name = "分类编码", width = 15)
	@ApiModelProperty(value = "分类编码")
	private String query32;
	@Excel(name = "分类名称", width = 15)
	@ApiModelProperty(value = "分类名称")
	private String query33;
	@Excel(name = "打样子PO", width = 15)
	@ApiModelProperty(value = "打样子PO")
	private String query34;
	@Excel(name = "打样总金额", width = 15)
	@ApiModelProperty(value = "打样总金额")
	private String query35;
	@Excel(name = "query36", width = 15)
	@ApiModelProperty(value = "query36")
	private String query36;

	@Excel(name = "是否确认", width = 15)
	@ApiModelProperty(value = "是否确认")
	private String query37;

	@Excel(name = "Google Folder", width = 15)
	@ApiModelProperty(value = "Google Folder")
	private String query38;

	@Excel(name = "WMS下架操作人", width = 15)
	@ApiModelProperty(value = "WMS下架操作人")
	private java.lang.String query39;
	@Excel(name = "完成原因", width = 15)
	@ApiModelProperty(value = "完成原因")
	private java.lang.String query40;

	@Excel(name = "供应商编码2", width = 15)
	@ApiModelProperty(value = "供应商编码2")
	private java.lang.String query41;
	@Excel(name = "供应商名称2", width = 15)
	@ApiModelProperty(value = "供应商名称2")
	private java.lang.String query42;
	@Excel(name = "确认日期", width = 15)
	@ApiModelProperty(value = "确认日期")
	private java.lang.String query43;
	/**数量*/
	@Excel(name = "数量", width = 15)
	@ApiModelProperty(value = "数量")
    private Double num01;
	/**未清数量*/
	@Excel(name = "未清数量", width = 15)
	@ApiModelProperty(value = "未清数量")
    private Double num02;
	/**已完成数量*/
	@Excel(name = "已完成数量", width = 15)
	@ApiModelProperty(value = "已完成数量")
    private Double num03;
	/**num04*/
	@Excel(name = "num04", width = 15)
	@ApiModelProperty(value = "num04")
//	@JsonSerialize(using = CustomDoubleSerializer3.class)
    private Double num04;
	/**num05*/
	@Excel(name = "num05", width = 15)
	@ApiModelProperty(value = "num05")
	@JsonSerialize(using = CustomDoubleSerializer.class)
    private Double num05;
	/**含税单价*/
	@Excel(name = "含税单价", width = 15)
	@ApiModelProperty(value = "含税单价")
//	@JsonSerialize(using = CustomDoubleSerializer3.class)
	private Double num06;
	/**含税总价*/
	@Excel(name = "含税总价", width = 15)
	@ApiModelProperty(value = "含税总价")
	@JsonSerialize(using = CustomDoubleSerializer.class)
	private Double num07;
	/**总金额*/
	@Excel(name = "总金额", width = 15)
	@ApiModelProperty(value = "总金额")
	@JsonSerialize(using = CustomDoubleSerializer.class)
	private Double num08;
	/**含税总金额*/
	@Excel(name = "含税总金额", width = 15)
	@ApiModelProperty(value = "含税总金额")
	@JsonSerialize(using = CustomDoubleSerializer.class)
	private Double num09;
	/**税率*/
	@Excel(name = "税率", width = 15)
	@ApiModelProperty(value = "税率")
	@JsonSerialize(using = CustomDoubleSerializer.class)
	private Double num10;
	/**未清含税总金额*/
	@Excel(name = "未清含税总金额", width = 15)
	@ApiModelProperty(value = "未清含税总金额")
	@JsonSerialize(using = CustomDoubleSerializer.class)
	private Double num11;
	/**未清总金额*/
	@Excel(name = "未清总金额", width = 15)
	@ApiModelProperty(value = "未清总金额")
	@JsonSerialize(using = CustomDoubleSerializer.class)
	private Double num12;
	/**num13*/
	@Excel(name = "num13", width = 15)
	@ApiModelProperty(value = "num13")
	@JsonSerialize(using = CustomDoubleSerializer.class)
	private Double num13;
	/**num14*/
	@Excel(name = "num14", width = 15)
	@ApiModelProperty(value = "num14")
	@JsonSerialize(using = CustomDoubleSerializer.class)
	private Double num14;
	/**num15*/
	@Excel(name = "num15", width = 15)
	@ApiModelProperty(value = "num15")
	@JsonSerialize(using = CustomDoubleSerializer.class)
	private Double num15;
	/**样品可退金额*/
	@Excel(name = "num15", width = 15)
	@ApiModelProperty(value = "num16")
	@JsonSerialize(using = CustomDoubleSerializer.class)
	private Double num16;
	/**num17*/
	@Excel(name = "num17", width = 15)
	@ApiModelProperty(value = "num17")
	@JsonSerialize(using = CustomDoubleSerializer.class)
	private Double num17;

	@Excel(name = "num18", width = 15)
	@ApiModelProperty(value = "num18")
	@JsonSerialize(using = CustomDoubleSerializer.class)
	private Double num18;
	/**num19*/
	@Excel(name = "出库未清数量", width = 15)
	@ApiModelProperty(value = "出库未清数量")
	@JsonSerialize(using = CustomDoubleSerializer.class)
	private Double num19;
	/**num20*/
	@Excel(name = "出库已清数量", width = 15)
	@ApiModelProperty(value = "出库已清数量")
	@JsonSerialize(using = CustomDoubleSerializer.class)
	private Double num20;
	/**关联单据类型*/
	@Excel(name = "关联单据类型", width = 15)
	@ApiModelProperty(value = "关联单据类型")
    private String link01;
	/**关联单号*/
	@Excel(name = "关联单号", width = 15)
	@ApiModelProperty(value = "关联单号")
    private String link02;
	/**销售单号*/
	@Excel(name = "销售单号", width = 15)
	@ApiModelProperty(value = "销售单号")
    private String link03;
	/**link04*/
	@Excel(name = "link04", width = 15)
	@ApiModelProperty(value = "link04")
    private String link04;
	/**link05*/
	@Excel(name = "link05", width = 15)
	@ApiModelProperty(value = "link05")
    private String link05;
	/**备注1*/
	@Excel(name = "备注1", width = 15)
	@ApiModelProperty(value = "备注1")
    private String text01;
	/**备注2*/
	@Excel(name = "备注2", width = 15)
	@ApiModelProperty(value = "备注2")
    private String text02;
	/**完成原因*/
	@Excel(name = "完成原因", width = 15)
	@ApiModelProperty(value = "完成原因")
    private String text03;
	/**text04*/
	@Excel(name = "text04", width = 15)
	@ApiModelProperty(value = "text04")
    private String text04;
	/**text05*/
	@Excel(name = "text05", width = 15)
	@ApiModelProperty(value = "text05")
    private String text05;
	/**单据附件*/
	@Excel(name = "单据附件", width = 15)
	@ApiModelProperty(value = "单据附件")
    private String attr1;
	/**attr2*/
	@Excel(name = "attr2", width = 15)
	@ApiModelProperty(value = "attr2")
    private String attr2;
	/**attr3*/
	@Excel(name = "attr3", width = 15)
	@ApiModelProperty(value = "attr3")
    private String attr3;
	/**attr4*/
	@Excel(name = "attr4", width = 15)
	@ApiModelProperty(value = "attr4")
    private String attr4;
	/**attr5*/
	@Excel(name = "attr5", width = 15)
	@ApiModelProperty(value = "attr5")
    private String attr5;

	@ExcelCollection(name="busi_po_item")
	@ApiModelProperty(value = "busi_po_item")
	private List<BusiPoItem> busiPoItemList;


	@TableField(exist = false)
	private String XingYeFenLei;

	@TableField(exist = false)
	private String remainingSum;
}
