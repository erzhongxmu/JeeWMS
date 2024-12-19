package com.base.modules.jeebms.vo;


import com.base.modules.jeebms.entity.BmsBillI;
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
 * @Description: 账单详情主
 * @Author: base-boot
 * @Date:   2022-04-05
 * @Version: V1.0
 */
@Data
@ApiModel(value="bms_bill_hPage对象", description="账单详情主")
public class BmsBillHPage {

	/**主键*/
	@ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
	@ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
	//	@ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**逻辑删除*/
	//@Excel(name = "逻辑删除", width = 15)
	@ApiModelProperty(value = "逻辑删除")
    private Integer isDel;
	/**备用1*/
	//	@Excel(name = "备用1", width = 15)
	@ApiModelProperty(value = "备用1")
    private String attr1;
	/**备用2*/
	//	@Excel(name = "备用2", width = 15)
	@ApiModelProperty(value = "备用2")
    private String attr2;
	/**备用3*/
	//	@Excel(name = "备用3", width = 15)
	@ApiModelProperty(value = "备用3")
    private String attr3;
	/**备用4*/
	//	@Excel(name = "备用4", width = 15)
	@ApiModelProperty(value = "备用4")
    private String attr4;
	/**备用5*/
	//	@Excel(name = "备用5", width = 15)
	@ApiModelProperty(value = "备用5")
    private String attr5;
	/**租户字段*/
	//	 //@Excel(name = "租户字段", width = 15))
	//	 @ApiModelProperty(value = "租户字段")
    private String tenantId;
	/**账单编号*/
	@Excel(name = "账单编号", width = 15)
	@ApiModelProperty(value = "账单编号")
    private String billNo;
	/**账单类型*/
	@Excel(name = "账单类型", width = 15)
	@ApiModelProperty(value = "账单类型")
    private String billType;
	/**期间*/
	@Excel(name = "期间", width = 15)
	@ApiModelProperty(value = "期间")
    private String billPeriod;
	/**计费对象类型*/
	@Excel(name = "计费对象类型", width = 15)
	@ApiModelProperty(value = "计费对象类型")
    private String costObjType;
	/**计费对象编号*/
	@Excel(name = "计费对象编号", width = 15)
	@ApiModelProperty(value = "计费对象编号")
    private String costObjNo;
	/**计费对象名称*/
	@Excel(name = "计费对象名称", width = 15)
	@ApiModelProperty(value = "计费对象名称")
    private String costObjName;
	/**账单金额*/
	@Excel(name = "账单金额", width = 15)
	@ApiModelProperty(value = "账单金额")
    private java.math.BigDecimal billSum;
	/**货币*/
	@Excel(name = "货币", width = 15)
	@ApiModelProperty(value = "货币")
    private String costHb;
	/**备注*/
	@Excel(name = "备注", width = 15)
	@ApiModelProperty(value = "备注")
    private String remark;
	/**状态*/
	@Excel(name = "状态", width = 15)
	@ApiModelProperty(value = "状态")
    private String status;

	@ExcelCollection(name="账单详情子")
	@ApiModelProperty(value = "账单详情子")
	private List<BmsBillI> bmsBillIList;

}
