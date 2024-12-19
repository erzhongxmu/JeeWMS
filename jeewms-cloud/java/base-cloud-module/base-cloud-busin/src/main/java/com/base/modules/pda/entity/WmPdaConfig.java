package com.base.modules.pda.entity;

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
 * @Description: pda配置表
 * @Author: base-boot
 * @Date:   2022-04-27
 * @Version: V1.0
 */
@Data
@TableName("wm_pda_config")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="wm_pda_config对象", description="pda配置表")
public class WmPdaConfig implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
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
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**query01*/
	@Excel(name = "query01", width = 15)
    @ApiModelProperty(value = "query01")
    private String query01;
	/**query02*/
	@Excel(name = "query02", width = 15)
    @ApiModelProperty(value = "query02")
    private String query02;
	/**query03*/
	@Excel(name = "query03", width = 15)
    @ApiModelProperty(value = "query03")
    private String query03;
	/**query04*/
	@Excel(name = "query04", width = 15)
    @ApiModelProperty(value = "query04")
    private String query04;
	/**query05*/
	@Excel(name = "query05", width = 15)
    @ApiModelProperty(value = "query05")
    private String query05;
	/**query06*/
	@Excel(name = "query06", width = 15)
    @ApiModelProperty(value = "query06")
    private String query06;
	/**query07*/
	@Excel(name = "query07", width = 15)
    @ApiModelProperty(value = "query07")
    private String query07;
	/**query08*/
	@Excel(name = "query08", width = 15)
    @ApiModelProperty(value = "query08")
    private String query08;
	/**query09*/
	@Excel(name = "query09", width = 15)
    @ApiModelProperty(value = "query09")
    private String query09;
	/**query10*/
	@Excel(name = "query10", width = 15)
    @ApiModelProperty(value = "query10")
    private String query10;
}
