package com.base.modules.jeewms.entity;

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
 * @Description: 费用
 * @Author: base-boot
 * @Date:   2021-05-24
 * @Version: V1.0
 */
@Data
@TableName("wm_day_cost")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="wm_day_cost对象", description="费用")
public class WmDayCost implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人名称*/
	// @Excel(name = "创建人名称", width = 15)
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
	// @Excel(name = "更新人名称", width = 15)
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
	// @Excel(name = "所属公司", width = 15)
    @ApiModelProperty(value = "所属公司")
    private java.lang.String sysCompanyCode;
	/**客户*/
	@Excel(name = "客户", width = 15, dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "id")
	@Dict(dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "id")
    @ApiModelProperty(value = "客户")
    private java.lang.String cusCode;
	/**客户名称*/
	@Excel(name = "客户名称", width = 15)
    @ApiModelProperty(value = "客户名称")
    private java.lang.String cusName;
	/**费用*/
	@Excel(name = "费用", width = 15)
    @ApiModelProperty(value = "费用")
    private java.lang.String costCode;
	/**费用名称*/
	@Excel(name = "费用名称", width = 15)
    @ApiModelProperty(value = "费用名称")
    private java.lang.String costName;
	/**费用日期*/
	@Excel(name = "费用日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "费用日期")
    private java.util.Date costData;
	/**每日费用*/
	@Excel(name = "每日费用", width = 15)
    @ApiModelProperty(value = "每日费用")
    private java.lang.String dayCostYj;
	/**不含税价*/
	@Excel(name = "不含税价", width = 15)
    @ApiModelProperty(value = "不含税价")
    private java.lang.String dayCostBhs;
	/**税额*/
	@Excel(name = "税额", width = 15)
    @ApiModelProperty(value = "税额")
    private java.lang.String dayCostSe;
	/**含税价*/
	@Excel(name = "含税价", width = 15)
    @ApiModelProperty(value = "含税价")
    private java.lang.String dayCostHsj;
	/**费用来源*/
	@Excel(name = "费用来源", width = 15)
    @ApiModelProperty(value = "费用来源")
    private java.lang.String costOri;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String beizhu;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.String costSta;
	/**计费数量*/
	@Excel(name = "计费数量", width = 15)
    @ApiModelProperty(value = "计费数量")
    private java.lang.String costSl;
	/**数量单位*/
	@Excel(name = "数量单位", width = 15)
    @ApiModelProperty(value = "数量单位")
    private java.lang.String costUnit;
	/**是否已结算*/
	@Excel(name = "是否已结算", width = 15, dicCode = "sf_yn")
	@Dict(dicCode = "sf_yn")
    @ApiModelProperty(value = "是否已结算")
    private java.lang.String costJs;
}
