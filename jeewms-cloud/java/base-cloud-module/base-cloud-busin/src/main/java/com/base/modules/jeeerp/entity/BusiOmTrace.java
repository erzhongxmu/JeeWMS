package com.base.modules.jeeerp.entity;

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
 * @Description: busi_om_trace
 * @Author: base-boot
 * @Date:   2024-03-12
 * @Version: V1.0
 */
@Data
@TableName("busi_om_trace")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="busi_om_trace对象", description="busi_om_trace")
public class BusiOmTrace implements Serializable {
    private static final long serialVersionUID = 1L;

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
	/**装箱单号*/
	@Excel(name = "装箱单号", width = 15)
    @ApiModelProperty(value = "装箱单号")
    private String query01;
	/**出库日期*/
	@Excel(name = "出库日期", width = 15)
    @ApiModelProperty(value = "出库日期")
    private String query02;
	/**公司属性*/
	@Excel(name = "公司属性", width = 15)
    @ApiModelProperty(value = "公司属性")
    private String query03;
	/**客户名*/
	@Excel(name = "客户名", width = 15)
    @ApiModelProperty(value = "客户名")
    private String query04;
	/**目的地*/
	@Excel(name = "目的地", width = 15)
    @ApiModelProperty(value = "目的地")
    private String query05;
	/**类型*/
	@Excel(name = "类型", width = 15)
    @ApiModelProperty(value = "类型")
    private String query06;
	/**产品清单*/
	@Excel(name = "产品清单", width = 15)
    @ApiModelProperty(value = "产品清单")
    private String query07;
	/**出货人*/
	@Excel(name = "出货人", width = 15)
    @ApiModelProperty(value = "出货人")
    private String query08;
	/**出货方式*/
	@Excel(name = "出货方式", width = 15)
    @ApiModelProperty(value = "出货方式")
    private String query09;
	/**贸易条款*/
	@Excel(name = "贸易条款", width = 15)
    @ApiModelProperty(value = "贸易条款")
    private String query10;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private String query11;
	/**离开日期*/
	@Excel(name = "离开日期", width = 15)
    @ApiModelProperty(value = "离开日期")
    private String query12;
	/**到达日期*/
	@Excel(name = "到达日期", width = 15)
    @ApiModelProperty(value = "到达日期")
    private String query13;
	/**货代*/
	@Excel(name = "货代", width = 15)
    @ApiModelProperty(value = "货代")
    private String query14;
	/**追踪单号*/
	@Excel(name = "追踪单号", width = 15)
    @ApiModelProperty(value = "追踪单号")
    private String query15;
	/**追踪链接*/
	@Excel(name = "追踪链接", width = 15)
    @ApiModelProperty(value = "追踪链接")
    private String query16;
	/**目的地地址*/
	@Excel(name = "目的地地址", width = 15)
    @ApiModelProperty(value = "目的地地址")
    private String query17;
	/**总体积*/
	@Excel(name = "总体积", width = 15)
    @ApiModelProperty(value = "总体积")
    private String query18;
	/**总重量*/
	@Excel(name = "总重量", width = 15)
    @ApiModelProperty(value = "总重量")
    private String query19;
	/**总箱数*/
	@Excel(name = "总箱数", width = 15)
    @ApiModelProperty(value = "总箱数")
    private String query20;
	/**总费用*/
	@Excel(name = "总费用", width = 15)
    @ApiModelProperty(value = "总费用")
    private String query21;
	/**Google Folder*/
	@Excel(name = "Google Folder", width = 15)
    @ApiModelProperty(value = "Google Folder")
    private String query22;
	/**是否含税*/
	@Excel(name = "是否含税", width = 15)
    @ApiModelProperty(value = "是否含税")
    private String query23;
	/**报价金额*/
	@Excel(name = "报价金额", width = 15)
    @ApiModelProperty(value = "报价金额")
    private String query24;
	/**账单金额*/
	@Excel(name = "账单金额", width = 15)
    @ApiModelProperty(value = "账单金额")
    private String query25;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String query26;
}
