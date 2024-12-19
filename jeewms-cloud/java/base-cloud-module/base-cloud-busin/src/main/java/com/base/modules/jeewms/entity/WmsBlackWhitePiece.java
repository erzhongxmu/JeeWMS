package com.base.modules.jeewms.entity;

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
 * @Description: 黑白件
 * @Author: base-boot
 * @Date:   2021-11-27
 * @Version: V1.0
 */
@Data
@TableName("wms_black_white_piece")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="wms_black_white_piece对象", description="黑白件")
public class WmsBlackWhitePiece implements Serializable {
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
	/**黑件编码*/
	@Excel(name = "黑件编码", width = 15)
    @ApiModelProperty(value = "黑件编码")
    private String blackNo;
	/**黑件名称*/
	@Excel(name = "黑件名称", width = 15)
    @ApiModelProperty(value = "黑件名称")
    private String blackName;
	/**黑件描述*/
	@Excel(name = "黑件描述", width = 15)
    @ApiModelProperty(value = "黑件描述")
    private String blackDescribe;
	/**白件编码*/
	@Excel(name = "白件编码", width = 15)
    @ApiModelProperty(value = "白件编码")
    private String whiteNo;
	/**白件名称*/
	@Excel(name = "白件名称", width = 15)
    @ApiModelProperty(value = "白件名称")
    private String whiteName;
	/**白件描述*/
	@Excel(name = "白件描述", width = 15)
    @ApiModelProperty(value = "白件描述")
    private String whiteDescribe;
}
