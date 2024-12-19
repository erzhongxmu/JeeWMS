package com.base.modules.jeebms.vo;

import java.util.List;
import com.base.modules.jeebms.entity.BmsCostRuleH;
import com.base.modules.jeebms.entity.BmsCostRuleI;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import com.base.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: bms_cost_rule_h
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Data
@ApiModel(value="bms_cost_rule_hPage对象", description="bms_cost_rule_h")
public class BmsCostRuleHPage {

	/**id*/
	@ApiModelProperty(value = "id")
    private String id;
	/**createBy*/
	@ApiModelProperty(value = "createBy")
    private String createBy;
	/**createTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "createTime")
    private Date createTime;
	/**updateBy*/
	@ApiModelProperty(value = "updateBy")
    private String updateBy;
	/**updateTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "updateTime")
    private Date updateTime;
	/**sysOrgCode*/
	@ApiModelProperty(value = "sysOrgCode")
    private String sysOrgCode;
	/**isDel*/
	@Excel(name = "isDel", width = 15)
	@ApiModelProperty(value = "isDel")
    private Integer isDel;
	/**attr1*/
	@Excel(name = "attr1", width = 15)
	@ApiModelProperty(value = "attr1")
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
	/**tenantId*/
	@Excel(name = "tenantId", width = 15)
	@ApiModelProperty(value = "tenantId")
    private String tenantId;
	/**contNo*/
	@Excel(name = "contNo", width = 15)
	@ApiModelProperty(value = "contNo")
    private String contNo;
	/**contName*/
	@Excel(name = "contName", width = 15)
	@ApiModelProperty(value = "contName")
    private String contName;
	/**costRuleNo*/
	@Excel(name = "costRuleNo", width = 15)
	@ApiModelProperty(value = "costRuleNo")
    private String costRuleNo;
	/**costRuleName*/
	@Excel(name = "costRuleName", width = 15)
	@ApiModelProperty(value = "costRuleName")
    private String costRuleName;
	/**costNo*/
	@Excel(name = "costNo", width = 15)
	@ApiModelProperty(value = "costNo")
    private String costNo;
	/**costName*/
	@Excel(name = "costName", width = 15)
	@ApiModelProperty(value = "costName")
    private String costName;
	/**costSNo*/
	@Excel(name = "costSNo", width = 15)
	@ApiModelProperty(value = "costSNo")
    private String costSNo;
	/**costSName*/
	@Excel(name = "costSName", width = 15)
	@ApiModelProperty(value = "costSName")
    private String costSName;
	/**costObjType*/
	@Excel(name = "costObjType", width = 15)
	@ApiModelProperty(value = "costObjType")
    private String costObjType;
	/**costObjNo*/
	@Excel(name = "costObjNo", width = 15)
	@ApiModelProperty(value = "costObjNo")
    private String costObjNo;
	/**costObjName*/
	@Excel(name = "costObjName", width = 15)
	@ApiModelProperty(value = "costObjName")
    private String costObjName;
	/**costMin*/
	@Excel(name = "costMin", width = 15)
	@ApiModelProperty(value = "costMin")
    private Double costMin;
	/**costMax*/
	@Excel(name = "costMax", width = 15)
	@ApiModelProperty(value = "costMax")
    private Double costMax;
	/**costPeriod*/
	@Excel(name = "costPeriod", width = 15)
	@ApiModelProperty(value = "costPeriod")
    private String costPeriod;
	/**costValidBegin*/
	@Excel(name = "costValidBegin", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "costValidBegin")
    private Date costValidBegin;
	/**costValidEnd*/
	@Excel(name = "costValidEnd", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "costValidEnd")
    private Date costValidEnd;

	@ExcelCollection(name="bms_cost_rule_i")
	@ApiModelProperty(value = "bms_cost_rule_i")
	private List<BmsCostRuleI> bmsCostRuleIList;

}
