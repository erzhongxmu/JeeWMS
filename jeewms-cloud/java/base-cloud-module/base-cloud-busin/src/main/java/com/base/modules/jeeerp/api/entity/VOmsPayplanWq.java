package com.base.modules.jeeerp.api.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import com.base.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: v_oms_payplan_wq
 * @Author: base-boot
 * @Date:   2023-04-14
 * @Version: V1.0
 */
@Data
@TableName("v_oms_payplan_wq")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="v_oms_payplan_wq对象", description="v_oms_payplan_wq")
public class VOmsPayplanWq implements Serializable {
    private static final long serialVersionUID = 1L;

	/**num17*/
	@Excel(name = "num17", width = 15)
    @ApiModelProperty(value = "num17")
    private Double num17;
	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
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
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
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
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
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
	/**query16*/
	@Excel(name = "query16", width = 15)
    @ApiModelProperty(value = "query16")
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
	/**query24*/
    @Dict(dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
	@Excel(name = "query24", width = 15)
    @ApiModelProperty(value = "query24")
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
	@Excel(name = "query30", width = 15)
    @ApiModelProperty(value = "query30")
    private String query30;
    /**query31*/
    @Excel(name = "query31", width = 15)
    @ApiModelProperty(value = "query31")
    private String query31;
    /**query32*/
    @Excel(name = "query32", width = 15)
    @ApiModelProperty(value = "query32")
    private String query32;
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
    private Double num04;
	/**num05*/
	@Excel(name = "num05", width = 15)
    @ApiModelProperty(value = "num05")
    private Double num05;
	/**关联单据类型*/
	@Excel(name = "关联单据类型", width = 15)
    @ApiModelProperty(value = "关联单据类型")
    private String link01;
	/**关联单号*/
	@Excel(name = "关联单号", width = 15)
    @ApiModelProperty(value = "关联单号")
    private String link02;
	/**link03*/
	@Excel(name = "link03", width = 15)
    @ApiModelProperty(value = "link03")
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
	/**text03*/
	@Excel(name = "text03", width = 15)
    @ApiModelProperty(value = "text03")
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
	/**含税单价*/
	@Excel(name = "含税单价", width = 15)
    @ApiModelProperty(value = "含税单价")
    private Double num06;
	/**含税总价*/
	@Excel(name = "含税总价", width = 15)
    @ApiModelProperty(value = "含税总价")
    private Double num07;
	/**总金额*/
	@Excel(name = "总金额", width = 15)
    @ApiModelProperty(value = "总金额")
    private Double num08;
	/**含税总金额*/
	@Excel(name = "含税总金额", width = 15)
    @ApiModelProperty(value = "含税总金额")
    private Double num09;
	/**税率*/
	@Excel(name = "税率", width = 15)
    @ApiModelProperty(value = "税率")
    private Double num10;
	/**未清总金额*/
	@Excel(name = "未清总金额", width = 15)
    @ApiModelProperty(value = "未清总金额")
    private Double num11;
	/**未清含税总金额*/
	@Excel(name = "未清含税总金额", width = 15)
    @ApiModelProperty(value = "未清含税总金额")
    private Double num12;
	/**num13*/
	@Excel(name = "num13", width = 15)
    @ApiModelProperty(value = "num13")
    private Double num13;
	/**num14*/
	@Excel(name = "num14", width = 15)
    @ApiModelProperty(value = "num14")
    private Double num14;
	/**num15*/
	@Excel(name = "num15", width = 15)
    @ApiModelProperty(value = "num15")
    private Double num15;
	/**样品可退金额*/
	@Excel(name = "样品可退金额", width = 15)
    @ApiModelProperty(value = "样品可退金额")
    private Double num16;
}
