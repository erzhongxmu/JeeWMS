package com.base.modules.jeeerp.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import com.base.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: busi_prd_ord
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
@ApiModel(value="busi_prd_ord对象", description="busi_prd_ord")
@Data
@TableName("busi_prd_ord")
public class BusiPrdOrd implements Serializable {
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
	/**query08*/
	@Excel(name = "query08", width = 15)
    @ApiModelProperty(value = "query08")
    private java.lang.String query08;
	/**query09*/
	@Excel(name = "query09", width = 15)
    @ApiModelProperty(value = "query09")
    private java.lang.String query09;
	/**商品编码/成品*/
	@Excel(name = "商品编码/成品", width = 15)
    @ApiModelProperty(value = "商品编码/成品")
    private java.lang.String query10;
	/**商品名称/成品*/
	@Excel(name = "商品名称/成品", width = 15)
    @ApiModelProperty(value = "商品名称/成品")
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
	/**query16*/
	@Excel(name = "query16", width = 15)
    @ApiModelProperty(value = "query16")
    private java.lang.String query16;
	/**query17*/
	@Excel(name = "query17", width = 15)
    @ApiModelProperty(value = "query17")
    private java.lang.String query17;
	/**query18*/
	@Excel(name = "query18", width = 15)
    @ApiModelProperty(value = "query18")
    private java.lang.String query18;
	/**query19*/
	@Excel(name = "query19", width = 15)
    @ApiModelProperty(value = "query19")
    private java.lang.String query19;
	/**query20*/
	@Excel(name = "query20", width = 15)
    @ApiModelProperty(value = "query20")
    private java.lang.String query20;
	/**期限*/
    @Excel(name = "期限", width = 15)
    @ApiModelProperty(value = "期限")
    private java.lang.String query21;
    /**完成时间*/
    @Excel(name = "完成时间", width = 15)
    @ApiModelProperty(value = "完成时间")
    private java.lang.String query22;
	/**单号-行项目号*/
	@Excel(name = "单号-行项目号", width = 15)
    @ApiModelProperty(value = "单号-行项目号")
    private java.lang.String query23;
    /**客户编码*/
    @Dict(dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
	@Excel(name = "客户", width = 15)
    @ApiModelProperty(value = "客户")
    private java.lang.String query24;
	/**质检人*/
	@Excel(name = "质检人", width = 15)
    @ApiModelProperty(value = "质检人")
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
    /**query31*/
    @Excel(name = "工时", width = 15)
    @ApiModelProperty(value = "工时")
    private java.lang.String query31;
    /**加工费*/
    @Excel(name = "加工费", width = 15)
    @ApiModelProperty(value = "加工费")
    private java.lang.String query32;

    @Excel(name = "单价(含加工费)", width = 15)
    @ApiModelProperty(value = "单价(含加工费)")
    private String query33;

    @Excel(name = "验货数量", width = 15)
    @ApiModelProperty(value = "验货数量")
    private String query34;
    @Excel(name = "税率", width = 15)
    @ApiModelProperty(value = "税率")
    private String query35;


    @Excel(name = "供应商编码2", width = 15)
    @ApiModelProperty(value = "供应商编码2")
    private java.lang.String query41;
    @Excel(name = "供应商名称2", width = 15)
    @ApiModelProperty(value = "供应商名称2")
    private java.lang.String query42;
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
	@Excel(name = "num04", width = 15)
    @ApiModelProperty(value = "num04")
    private java.lang.Double num04;
	/**num05*/
	@Excel(name = "num05", width = 15)
    @ApiModelProperty(value = "num05")
    private java.lang.Double num05;
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
	/**text03*/
	@Excel(name = "text03", width = 15)
    @ApiModelProperty(value = "text03")
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
	/**attr2*/
	@Excel(name = "attr2", width = 15)
    @ApiModelProperty(value = "attr2")
    private java.lang.String attr2;
	/**attr3*/
	@Excel(name = "attr3", width = 15)
    @ApiModelProperty(value = "attr3")
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
}
