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
 * @Description: wm_import_data
 * @Author: base-boot
 * @Date:   2022-10-20
 * @Version: V1.0
 */
@Data
@TableName("wm_import_data")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="wm_import_data对象", description="wm_import_data")
public class WmImportData implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人名称*/
//	@Excel(name = "创建人名称", width = 15)
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
//	@Excel(name = "更新人名称", width = 15)
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
//	@Excel(name = "所属公司", width = 15)
    @ApiModelProperty(value = "所属公司")
    private String sysCompanyCode;
	/**query01*/
    /**query11*/
    @Excel(name = "仓库", width = 15)
    @ApiModelProperty(value = "仓库")
    private String query11;
	@Excel(name = "出入库类型", width = 15)
    @ApiModelProperty(value = "出入库类型")
    private String query01;
	/**query02*/
	@Excel(name = "商品编码", width = 15)
    @ApiModelProperty(value = "商品编码")
    private String query02;
	/**query03*/
	@Excel(name = "商品名称", width = 15)
    @ApiModelProperty(value = "商品名称")
    private String query03;
	/**query04*/
	@Excel(name = "客户名称", width = 15)
    @ApiModelProperty(value = "客户名称")
    private String query04;
	/**query05*/
	@Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
    private String query05;
	/**query06*/
	@Excel(name = "单位", width = 15)
    @ApiModelProperty(value = "单位")
    private String query06;
	/**query07*/
	@Excel(name = "箱码", width = 15)
    @ApiModelProperty(value = "箱码")
    private String query07;
	/**query08*/
	@Excel(name = "PO号", width = 15)
    @ApiModelProperty(value = "PO号")
    private String query08;
	/**query09*/
	@Excel(name = "体积", width = 15)
    @ApiModelProperty(value = "体积")
    private String query09;
	/**query10*/
	@Excel(name = "重量", width = 15)
    @ApiModelProperty(value = "重量")
    private String query10;

	/**query12*/
//	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "query12")
    private String query12;
	/**query13*/
//	@Excel(name = "query13", width = 15)
    @ApiModelProperty(value = "query13")
    private String query13;
	/**query14*/
//	@Excel(name = "query14", width = 15)
    @ApiModelProperty(value = "query14")
    private String query14;
	/**query15*/
//	@Excel(name = "query15", width = 15)
    @ApiModelProperty(value = "query15")
    private String query15;
	/**query16*/
//	@Excel(name = "query16", width = 15)
    @ApiModelProperty(value = "query16")
    private String query16;
	/**query17*/
//	@Excel(name = "query17", width = 15)
    @ApiModelProperty(value = "query17")
    private String query17;
	/**query18*/
//	@Excel(name = "query18", width = 15)
    @ApiModelProperty(value = "query18")
    private String query18;
	/**query19*/
//	@Excel(name = "query19", width = 15)
    @ApiModelProperty(value = "query19")
    private String query19;
	/**query20*/
//	@Excel(name = "query20", width = 15)
    @ApiModelProperty(value = "query20")
    private String query20;
	/**租户ID*/
//	@Excel(name = "租户ID", width = 15)
    @ApiModelProperty(value = "租户ID")
    private Integer tenantId;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String remark;
	/**备注*/
//	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String remarks;
}
