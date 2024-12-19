package com.base.modules.jeeerp.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.base.modules.util.CustomDoubleSerializer;
import com.base.modules.util.CustomDoubleSerializer2;
import com.base.modules.util.CustomDoubleSerializer3;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import com.base.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: busi_po
 * @Author: base-boot
 * @Date:   2022-11-23
 * @Version: V1.0
 */
@ApiModel(value="busi_po对象", description="busi_po")
@Data
@TableName("busi_po")
public class BusiPo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人名称*/
	@Excel(name = "创建人名称", width = 15)
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
	@Excel(name = "更新人名称", width = 15)
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
	/**单据类型*/
	@Excel(name = "单据类型", width = 15)
    @ApiModelProperty(value = "单据类型")
    private java.lang.String query01;
	/**单据状态*/
	@Excel(name = "单据状态", width = 15)
    @ApiModelProperty(value = "单据状态")
    private java.lang.String query02;
	/**单据日期*/
	@Excel(name = "单据日期", width = 15)
    @ApiModelProperty(value = "单据日期")
    private java.lang.String query03;
	/**单号*/
	@Excel(name = "单号", width = 15)
    @ApiModelProperty(value = "单号")
    private java.lang.String query04;
	/**公司*/
	@Excel(name = "公司", width = 15)
    @ApiModelProperty(value = "公司")
    private java.lang.String query05;
	/**工厂*/
	@Excel(name = "工厂", width = 15)
    @ApiModelProperty(value = "工厂")
    private java.lang.String query06;
	/**库存地点*/
	@Excel(name = "库存地点", width = 15)
    @ApiModelProperty(value = "库存地点")
    private java.lang.String query07;
	/**供应商编码*/
	@Excel(name = "供应商编码", width = 15)
    @ApiModelProperty(value = "供应商编码")
    private java.lang.String query08;
	/**供应商名称*/
	@Excel(name = "供应商名称", width = 15)
    @ApiModelProperty(value = "供应商名称")
    private java.lang.String query09;
	/**商品编码*/
	@Excel(name = "商品编码", width = 15)
    @ApiModelProperty(value = "商品编码")
    private java.lang.String query10;
	/**商品名称*/
	@Excel(name = "商品名称", width = 15)
    @ApiModelProperty(value = "商品名称")
    private java.lang.String query11;
	/**单位*/
	@Excel(name = "单位", width = 15)
    @ApiModelProperty(value = "单位")
    private java.lang.String query12;
	/**主批次*/
	@Excel(name = "主批次", width = 15)
    @ApiModelProperty(value = "主批次")
    private java.lang.String query13;
	/**子批次*/
	@Excel(name = "子批次", width = 15)
    @ApiModelProperty(value = "子批次")
    private java.lang.String query14;
	/**检验类型*/
	@Excel(name = "检验类型", width = 15)
    @ApiModelProperty(value = "检验类型")
    private java.lang.String query15;
	/**采购人*/
	@Excel(name = "采购人", width = 15)
    @ApiModelProperty(value = "采购人")
    private java.lang.String query16;
	/**query17*/
	@Excel(name = "query17", width = 15)
    @ApiModelProperty(value = "query17")
    private java.lang.String query17;
	/**query18*/
	@Excel(name = "验货期限", width = 15)
    @ApiModelProperty(value = "验货期限")
    private java.lang.String query18;
	/**query19*/
	@Excel(name = "验货人", width = 15)
    @ApiModelProperty(value = "验货人")
    private java.lang.String query19;
	/**query20*/
	@Excel(name = "query20", width = 15)
    @ApiModelProperty(value = "query20")
    private java.lang.String query20;
	/**预计到货时间*/
	@Excel(name = "预计到货时间", width = 15)
    @ApiModelProperty(value = "预计到货时间")
    private java.lang.String query21;
	/**币种*/
	@Excel(name = "币种", width = 15)
    @ApiModelProperty(value = "币种")
    private java.lang.String query22;
	/**单号-行项目号*/
	@Excel(name = "单号-行项目号", width = 15)
    @ApiModelProperty(value = "单号-行项目号")
    private java.lang.String query23;
	/**客户编码*/
    @Dict(dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
	@Excel(name = "客户编码", width = 15)
    @ApiModelProperty(value = "客户编码")
    private java.lang.String query24;
	/**是否含税*/
	@Excel(name = "是否含税", width = 15)
    @ApiModelProperty(value = "是否含税")
    private java.lang.String query25;
	/**query26*/
	@Excel(name = "query26", width = 15)
    @ApiModelProperty(value = "query26")
    private java.lang.String query26;
	/**query27*/
	@Excel(name = "query27", width = 15)
    @ApiModelProperty(value = "query27")
    private java.lang.String query27;
	/**query28*/
	@Excel(name = "query28", width = 15)
    @ApiModelProperty(value = "query28")
    private java.lang.String query28;
	/**query29*/
	@Excel(name = "query29", width = 15)
    @ApiModelProperty(value = "query29")
    private java.lang.String query29;
	/**query30*/
	@Excel(name = "query30", width = 15)
    @ApiModelProperty(value = "query30")
    private java.lang.String query30;
    @Excel(name = "业务类型", width = 15)
    @ApiModelProperty(value = "业务类型")
    private java.lang.String query31;
    @Excel(name = "分类编码", width = 15)
    @ApiModelProperty(value = "分类编码")
    private java.lang.String query32;
    @Excel(name = "分类名称", width = 15)
    @ApiModelProperty(value = "分类名称")
    private java.lang.String query33;
    @Excel(name = "打样子PO", width = 15)
    @ApiModelProperty(value = "打样子PO")
    private java.lang.String query34;
    @Excel(name = "打样总金额", width = 15)
    @ApiModelProperty(value = "打样总金额")
    private java.lang.String query35;

    @Excel(name = "query36", width = 15)
    @ApiModelProperty(value = "query36")
    private java.lang.String query36;

    @Excel(name = "是否确认", width = 15)
    @ApiModelProperty(value = "是否确认")
    private java.lang.String query37;

    @Excel(name = "Google Folder", width = 15)
    @ApiModelProperty(value = "Google Folder")
    private java.lang.String query38;

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
    private java.lang.Double num01;
	/**未清数量*/
	@Excel(name = "未清数量", width = 15)
    @ApiModelProperty(value = "未清数量")
    private java.lang.Double num02;
	/**已完成数量*/
	@Excel(name = "已完成数量", width = 15)
    @ApiModelProperty(value = "已完成数量")
    private java.lang.Double num03;
	/**num04*/
	@Excel(name = "单价", width = 15)
    @ApiModelProperty(value = "单价")
//    @JsonSerialize(using = CustomDoubleSerializer3.class)
    private java.lang.Double num04;
	/**总价*/
	@Excel(name = "总价", width = 15)
    @ApiModelProperty(value = "总价")
    @JsonSerialize(using = CustomDoubleSerializer.class)
    private java.lang.Double num05;
    /**含税单价*/
    @Excel(name = "含税单价", width = 15)
    @ApiModelProperty(value = "含税单价")
//    @JsonSerialize(using = CustomDoubleSerializer3.class)
    private java.lang.Double num06;
    /**含税总价*/
    @Excel(name = "含税总价", width = 15)
    @ApiModelProperty(value = "含税总价")
    @JsonSerialize(using = CustomDoubleSerializer.class)
    private java.lang.Double num07;
    /**总金额*/
    @Excel(name = "总金额", width = 15)
    @ApiModelProperty(value = "总金额")
    @JsonSerialize(using = CustomDoubleSerializer.class)
    private java.lang.Double num08;
    /**含税总金额*/
    @Excel(name = "含税总金额", width = 15)
    @ApiModelProperty(value = "含税总金额")
    @JsonSerialize(using = CustomDoubleSerializer.class)
    private java.lang.Double num09;
    /**税率*/
    @Excel(name = "税率", width = 15)
    @ApiModelProperty(value = "税率")
    private java.lang.Double num10;
    /**未清含税总金额*/
    @Excel(name = "未清含税总金额", width = 15)
    @ApiModelProperty(value = "未清含税总金额")
    @JsonSerialize(using = CustomDoubleSerializer.class)
    private java.lang.Double num11;
    /**未清总金额*/
    @Excel(name = "未清总金额", width = 15)
    @ApiModelProperty(value = "未清总金额")
    @JsonSerialize(using = CustomDoubleSerializer.class)
    private java.lang.Double num12;
    /**num13*/
    @Excel(name = "num13", width = 15)
    @ApiModelProperty(value = "num13")
    @JsonSerialize(using = CustomDoubleSerializer.class)
    private java.lang.Double num13;
    /**num14*/
    @Excel(name = "num14", width = 15)
    @ApiModelProperty(value = "num14")
    @JsonSerialize(using = CustomDoubleSerializer.class)
    private java.lang.Double num14;
    /**num15*/
    @Excel(name = "num15", width = 15)
    @ApiModelProperty(value = "num15")
    @JsonSerialize(using = CustomDoubleSerializer.class)
    private java.lang.Double num15;
    /**样品可退金额*/
    @Excel(name = "num15", width = 15)
    @ApiModelProperty(value = "num16")
    @JsonSerialize(using = CustomDoubleSerializer.class)
    private java.lang.Double num16;
    /**num17*/
    @Excel(name = "num17", width = 15)
    @ApiModelProperty(value = "num17")
    @JsonSerialize(using = CustomDoubleSerializer.class)
    private java.lang.Double num17;
    /**num17*/
    @Excel(name = "num18", width = 15)
    @ApiModelProperty(value = "num18")
    @JsonSerialize(using = CustomDoubleSerializer.class)
    private java.lang.Double num18;
    /**num19*/
    @Excel(name = "出库未清数量", width = 15)
    @ApiModelProperty(value = "出库未清数量")
    @JsonSerialize(using = CustomDoubleSerializer.class)
    private java.lang.Double num19;
    /**num20*/
    @Excel(name = "出库已清数量", width = 15)
    @ApiModelProperty(value = "出库已清数量")
    @JsonSerialize(using = CustomDoubleSerializer.class)
    private java.lang.Double num20;
	/**关联单据类型*/
	@Excel(name = "关联单据类型", width = 15)
    @ApiModelProperty(value = "关联单据类型")
    private java.lang.String link01;
	/**关联单号*/
	@Excel(name = "关联单号", width = 15)
    @ApiModelProperty(value = "关联单号")
    private java.lang.String link02;
	/**link03*/
	@Excel(name = "link03", width = 15)
    @ApiModelProperty(value = "link03")
    private java.lang.String link03;
	/**link04*/
	@Excel(name = "link04", width = 15)
    @ApiModelProperty(value = "link04")
    private java.lang.String link04;
	/**link05*/
	@Excel(name = "link05", width = 15)
    @ApiModelProperty(value = "link05")
    private java.lang.String link05;
	/**备注1*/
	@Excel(name = "备注1", width = 15)
    @ApiModelProperty(value = "备注1")
    private java.lang.String text01;
	/**备注2*/
	@Excel(name = "备注2", width = 15)
    @ApiModelProperty(value = "备注2")
    private java.lang.String text02;
	/**备注3*/
	@Excel(name = "备注3", width = 15)
    @ApiModelProperty(value = "备注3")
    private java.lang.String text03;
	/**text04*/
	@Excel(name = "text04", width = 15)
    @ApiModelProperty(value = "text04")
    private java.lang.String text04;
	/**text05*/
	@Excel(name = "text05", width = 15)
    @ApiModelProperty(value = "text05")
    private java.lang.String text05;
	/**单据附件*/
	@Excel(name = "单据附件", width = 15)
    @ApiModelProperty(value = "单据附件")
    private java.lang.String attr1;
	/**质检凭证*/
	@Excel(name = "attr2", width = 15)
    @ApiModelProperty(value = "质检凭证")
    private java.lang.String attr2;
	/**发票*/
	@Excel(name = "发票", width = 15)
    @ApiModelProperty(value = "发票")
    private java.lang.String attr3;
	/**attr4*/
	@Excel(name = "attr4", width = 15)
    @ApiModelProperty(value = "attr4")
    private java.lang.String attr4;
	/**attr5*/
	@Excel(name = "attr5", width = 15)
    @ApiModelProperty(value = "attr5")
    private java.lang.String attr5;


    @TableField(exist = false)
    private String XingYeFenLei;
    @TableField(exist = false)
    private String remainingSum;
}
