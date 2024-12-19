package com.base.modules.jeebms.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.base.common.aspect.annotation.Dict;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;

/**
 * @Description: 合同项目
 * @Author: base-boot
 * @Date:   2021-12-25
 * @Version: V1.0
 */
@Data
@TableName("wm_cus_cost_i")
@ApiModel(value="wm_cus_cost_i对象", description="合同项目")
public class WmCusCostI implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
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
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人名称*/
	// @Excel(name = "更新人名称", width = 15)
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
	/**所属公司*/
	// @Excel(name = "所属公司", width = 15)
    @ApiModelProperty(value = "所属公司")
    private String sysCompanyCode;
	/**费用名称*/
	@Excel(name = "费用名称", width = 15)
    @Dict(dicCode = "cost_code",dicText = "cost_name",dictTable = "ba_cost")
    @ApiModelProperty(value = "费用名称")
    private String costCode;
	/**价格RMB*/
	@Excel(name = "价格RMB", width = 15)
    @ApiModelProperty(value = "价格RMB")
    private String costJg;
	/**税率*/
	@Excel(name = "税率", width = 15)
    @ApiModelProperty(value = "税率")
    private String costSl;
	/**折扣*/
	@Excel(name = "折扣", width = 15)
    @ApiModelProperty(value = "折扣")
    private String costZk;
	/**不含税价RMB*/
	@Excel(name = "不含税价RMB", width = 15)
    @ApiModelProperty(value = "不含税价RMB")
    private String costBhs;
	/**含税价RMB*/
	@Excel(name = "含税价RMB", width = 15)
    @ApiModelProperty(value = "含税价RMB")
    private String costHs;
	/**费用ID*/
    @ApiModelProperty(value = "费用ID")
    private String cusCostId;
}
