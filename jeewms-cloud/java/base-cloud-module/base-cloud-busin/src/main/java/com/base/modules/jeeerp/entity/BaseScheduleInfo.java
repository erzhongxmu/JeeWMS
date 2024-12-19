package com.base.modules.jeeerp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

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
    private String id;
	/**createBy*/
    @ApiModelProperty(value = "createBy")
    private String createBy;
	/**createTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createTime")
    private Date createTime;
	/**updateBy*/
    @ApiModelProperty(value = "updateBy")
    private String updateBy;
	/**updateTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "updateTime")
    private Date updateTime;
	/**sysOrgCode*/
    @ApiModelProperty(value = "sysOrgCode")
    private String sysOrgCode;
	/**whNo*/
	@Excel(name = "whNo", width = 15)
    @ApiModelProperty(value = "whNo")
    private String whNo;
	/**userNo*/
	@Excel(name = "userNo", width = 15)
    @ApiModelProperty(value = "userNo")
    private String userNo;
	/**userName*/
	@Excel(name = "userName", width = 15)
    @ApiModelProperty(value = "userName")
    private String userName;
	/**userSkill*/
	@Excel(name = "userSkill", width = 15)
    @ApiModelProperty(value = "userSkill")
    private String userSkill;
	/**orgCode*/
	@Excel(name = "orgCode", width = 15)
    @ApiModelProperty(value = "orgCode")
    private String orgCode;
	/**priority*/
	@Excel(name = "priority", width = 15)
    @ApiModelProperty(value = "priority")
    private String priority;
	/**groupType*/
	@Excel(name = "groupType", width = 15)
    @ApiModelProperty(value = "groupType")
    private String groupType;
	/**planDate*/
	@Excel(name = "planDate", width = 15)
    @ApiModelProperty(value = "planDate")
    private String planDate;
	/**planDateTime*/
	@Excel(name = "planDateTime", width = 15)
    @ApiModelProperty(value = "planDateTime")
    private String planDateTime;
	/**occupyTime*/
	@Excel(name = "occupyTime", width = 15)
    @ApiModelProperty(value = "occupyTime")
    private Integer occupyTime;
	/**freeTime*/
	@Excel(name = "freeTime", width = 15)
    @ApiModelProperty(value = "freeTime")
    private Integer freeTime;
	/**isRest*/
	@Excel(name = "isRest", width = 15)
    @ApiModelProperty(value = "isRest")
    private String isRest;
	/**linkId*/
	@Excel(name = "linkId", width = 15)
    @ApiModelProperty(value = "linkId")
    private String linkId;
	/**lineItemNo*/
	@Excel(name = "lineItemNo", width = 15)
    @ApiModelProperty(value = "lineItemNo")
    private String lineItemNo;
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
	/**workClass*/
	@Excel(name = "workClass", width = 15)
    @ApiModelProperty(value = "workClass")
    private String workClass;
	/**orgCodeName*/
	@Excel(name = "orgCodeName", width = 15)
    @ApiModelProperty(value = "orgCodeName")
    private String orgCodeName;
	/**planDay*/
	@Excel(name = "planDay", width = 15)
    @ApiModelProperty(value = "planDay")
    private Integer planDay;
	/**tenantId*/
	@Excel(name = "tenantId", width = 15)
    @ApiModelProperty(value = "tenantId")
    private String tenantId;
	/**pbType*/
	@Excel(name = "pbType", width = 15)
    @ApiModelProperty(value = "pbType")
    private String pbType;
}
