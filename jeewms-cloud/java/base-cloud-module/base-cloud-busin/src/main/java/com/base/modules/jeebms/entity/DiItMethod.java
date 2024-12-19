package com.base.modules.jeebms.entity;

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
 * @Description: di_it_method
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Data
@TableName("di_it_method")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="di_it_method对象", description="di_it_method")
public class DiItMethod implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
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
	/**diItNo*/
	@Excel(name = "diItNo", width = 15)
    @ApiModelProperty(value = "diItNo")
    private Double diItNo;
	/**diItName*/
	@Excel(name = "diItName", width = 15)
    @ApiModelProperty(value = "diItName")
    private String diItName;
	/**remark*/
	@Excel(name = "remark", width = 15)
    @ApiModelProperty(value = "remark")
    private String remark;
	/**diItRuleNo*/
	@Excel(name = "diItRuleNo", width = 15)
    @ApiModelProperty(value = "diItRuleNo")
    private String diItRuleNo;
	/**diItRuleName*/
	@Excel(name = "diItRuleName", width = 15)
    @ApiModelProperty(value = "diItRuleName")
    private String diItRuleName;
	/**diDsType*/
	@Excel(name = "diDsType", width = 15)
    @ApiModelProperty(value = "diDsType")
    private String diDsType;
	/**diDatasource*/
	@Excel(name = "diDatasource", width = 15)
    @ApiModelProperty(value = "diDatasource")
    private String diDatasource;
	/**diSstatus*/
	@Excel(name = "diSstatus", width = 15)
    @ApiModelProperty(value = "diSstatus")
    private String diSstatus;
	/**diSdata*/
	@Excel(name = "diSdata", width = 15)
    @ApiModelProperty(value = "diSdata")
    private String diSdata;
	/**diSapi*/
	@Excel(name = "diSapi", width = 15)
    @ApiModelProperty(value = "diSapi")
    private String diSapi;
	/**diSsql*/
	@Excel(name = "diSsql", width = 15)
    @ApiModelProperty(value = "diSsql")
    private String diSsql;
	/**diDdsType*/
	@Excel(name = "diDdsType", width = 15)
    @ApiModelProperty(value = "diDdsType")
    private String diDdsType;
	/**diDdatasource*/
	@Excel(name = "diDdatasource", width = 15)
    @ApiModelProperty(value = "diDdatasource")
    private String diDdatasource;
	/**diDstatus*/
	@Excel(name = "diDstatus", width = 15)
    @ApiModelProperty(value = "diDstatus")
    private String diDstatus;
	/**diDdata*/
	@Excel(name = "diDdata", width = 15)
    @ApiModelProperty(value = "diDdata")
    private String diDdata;
	/**diDapi*/
	@Excel(name = "diDapi", width = 15)
    @ApiModelProperty(value = "diDapi")
    private String diDapi;
	/**diDsql*/
	@Excel(name = "diDsql", width = 15)
    @ApiModelProperty(value = "diDsql")
    private String diDsql;
}
