package com.base.modules.system.entity;

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
 * @Description: base_schedule_info
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
@Data
@TableName("base_schedule_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="base_schedule_info对象", description="base_schedule_info")
public class BaseScheduleInfo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**createBy*/
    @ApiModelProperty(value = "createBy")
    private java.lang.String createBy;
	/**createTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "createTime")
    private java.util.Date createTime;
	/**updateBy*/
    @ApiModelProperty(value = "updateBy")
    private java.lang.String updateBy;
	/**updateTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "updateTime")
    private java.util.Date updateTime;
	/**sysOrgCode*/
    @ApiModelProperty(value = "sysOrgCode")
    private java.lang.String sysOrgCode;
	/**whNo*/
	@Excel(name = "whNo", width = 15)
    @ApiModelProperty(value = "whNo")
    private java.lang.String whNo;
	/**userNo*/
	@Excel(name = "userNo", width = 15)
    @ApiModelProperty(value = "userNo")
    private java.lang.String userNo;
	/**userName*/
	@Excel(name = "userName", width = 15)
    @ApiModelProperty(value = "userName")
    private java.lang.String userName;
	/**userSkill*/
	@Excel(name = "userSkill", width = 15)
    @ApiModelProperty(value = "userSkill")
    private java.lang.String userSkill;
	/**orgCode*/
	@Excel(name = "orgCode", width = 15)
    @ApiModelProperty(value = "orgCode")
    private java.lang.String orgCode;
	/**priority*/
	@Excel(name = "priority", width = 15)
    @ApiModelProperty(value = "priority")
    private java.lang.String priority;
	/**groupType*/
	@Excel(name = "groupType", width = 15)
    @ApiModelProperty(value = "groupType")
    private java.lang.String groupType;
	/**planDate*/
	@Excel(name = "planDate", width = 15)
    @ApiModelProperty(value = "planDate")
    private java.lang.String planDate;
	/**planDateTime*/
	@Excel(name = "planDateTime", width = 15)
    @ApiModelProperty(value = "planDateTime")
    private java.lang.String planDateTime;
	/**occupyTime*/
	@Excel(name = "occupyTime", width = 15)
    @ApiModelProperty(value = "occupyTime")
    private java.lang.Integer occupyTime;
	/**freeTime*/
	@Excel(name = "freeTime", width = 15)
    @ApiModelProperty(value = "freeTime")
    private java.lang.Integer freeTime;
	/**isRest*/
	@Excel(name = "isRest", width = 15)
    @ApiModelProperty(value = "isRest")
    private java.lang.String isRest;
	/**linkId*/
	@Excel(name = "linkId", width = 15)
    @ApiModelProperty(value = "linkId")
    private java.lang.String linkId;
	/**lineItemNo*/
	@Excel(name = "lineItemNo", width = 15)
    @ApiModelProperty(value = "lineItemNo")
    private java.lang.String lineItemNo;
	/**attr1*/
	@Excel(name = "attr1", width = 15)
    @ApiModelProperty(value = "attr1")
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
	/**workClass*/
	@Excel(name = "workClass", width = 15)
    @ApiModelProperty(value = "workClass")
    private java.lang.String workClass;
	/**orgCodeName*/
	@Excel(name = "orgCodeName", width = 15)
    @ApiModelProperty(value = "orgCodeName")
    private java.lang.String orgCodeName;
	/**planDay*/
	@Excel(name = "planDay", width = 15)
    @ApiModelProperty(value = "planDay")
    private java.lang.Integer planDay;
	/**tenantId*/
	@Excel(name = "tenantId", width = 15)
    @ApiModelProperty(value = "tenantId")
    private java.lang.String tenantId;
	/**pbType*/
	@Excel(name = "pbType", width = 15)
    @ApiModelProperty(value = "pbType")
    private java.lang.String pbType;
}
