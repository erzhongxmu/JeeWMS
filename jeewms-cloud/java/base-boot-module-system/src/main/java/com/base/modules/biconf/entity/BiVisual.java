package com.base.modules.biconf.entity;

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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 大屏列表
 * @Author: base-boot
 * @Date:   2020-09-09
 * @Version: V1.0
 */
@Data
@TableName("bi_visual")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="bi_visual对象", description="大屏列表")
public class BiVisual implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id;
	/**大屏标题*/
	@Excel(name = "大屏标题", width = 15)
    @ApiModelProperty(value = "大屏标题")
    private String title;
	/**大屏背景*/
	@Excel(name = "大屏背景", width = 15)
    @ApiModelProperty(value = "大屏背景")
    private String backgroundUrl;
	/**大屏类型*/
	@Excel(name = "大屏类型", width = 15)
    @ApiModelProperty(value = "大屏类型")
    private Integer category;
	/**发布密码*/
	@Excel(name = "发布密码", width = 15)
    @ApiModelProperty(value = "发布密码")
    private String password;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建部门*/
    @ApiModelProperty(value = "创建部门")
    private String sysOrgCode;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**修改人*/
    @ApiModelProperty(value = "修改人")
    private String updateBy;
	/**修改时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private Integer status;
	/**是否已删除*/
	@Excel(name = "是否已删除", width = 15)
    @ApiModelProperty(value = "是否已删除")
    private Integer isDeleted;
}
