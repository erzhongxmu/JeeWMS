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
 * @Description: wm_tuopan
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
@Data
@TableName("wm_tuopan")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="wm_tuopan对象", description="wm_tuopan")
public class WmTuopan implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "id")
    private String id;
	/**创建人名称*/
	// @Excel(name = "创建人名称", width = 15)
    @ApiModelProperty(value = "创建人名称")
    private String createName;
	/**创建人登录名称*/
    @ApiModelProperty(value = "创建人登录名称")
    private String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
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
	@Excel(name = "更新日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**所属公司*/
	// @Excel(name = "所属公司", width = 15)
    @ApiModelProperty(value = "所属公司")
    private String sysCompanyCode;
	/**流程状态*/
	@Excel(name = "流程状态", width = 15)
    @ApiModelProperty(value = "流程状态")
    private String bpmStatus;
	/**托盘号*/
	@Excel(name = "托盘号", width = 15)
    @ApiModelProperty(value = "托盘号")
    private String tinId;
	/**托盘顺序*/
	@Excel(name = "托盘顺序", width = 15)
    @ApiModelProperty(value = "托盘顺序")
    private String tinSort;
	/**储位*/
	@Excel(name = "储位", width = 15)
    @ApiModelProperty(value = "储位")
    private String binId;
	/**托盘状态*/
	@Excel(name = "托盘状态", width = 15)
    @ApiModelProperty(value = "托盘状态")
    private String tinStatus;

    @ApiModelProperty(value = "商品编码 + 入库单号")
    private String remark;


    /**长*/
    @Excel(name = "长", width = 15)
    @ApiModelProperty(value = "长")
    private String tinLength;

    /**宽*/
    @Excel(name = "宽", width = 15)
    @ApiModelProperty(value = "宽")
    private String tinWidth;


    /**高*/
    @Excel(name = "高", width = 15)
    @ApiModelProperty(value = "高")
    private String tinHigh;

    /**体积*/
    @Excel(name = "体积", width = 15)
    @ApiModelProperty(value = "体积")
    private String tinVolume;

    /**重量*/
    @Excel(name = "重量", width = 15)
    @ApiModelProperty(value = "重量")
    private String tinWeight;

    /**箱唛*/
    @Excel(name = "箱唛", width = 15)
    @ApiModelProperty(value = "箱唛")
    private String boxmark;

    @ApiModelProperty(value = "是否下架")
    private String isfails;


    @ApiModelProperty(value = "是否上架")
    private String isupfails;
}
