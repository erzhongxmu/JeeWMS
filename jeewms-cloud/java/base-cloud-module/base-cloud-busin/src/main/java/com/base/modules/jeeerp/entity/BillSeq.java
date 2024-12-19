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
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: bill_seq
 * @Author: base-boot
 * @Date:   2022-11-11
 * @Version: V1.0
 */
@Data
@TableName("bill_seq")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="bill_seq对象", description="bill_seq")
public class BillSeq implements Serializable {
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
	/**id1*/
	@Excel(name = "id1", width = 15)
    @ApiModelProperty(value = "id1")
    private String id1;
	/**id2*/
	@Excel(name = "id2", width = 15)
    @ApiModelProperty(value = "id2")
    private String id2;
    /**id2*/
    @Excel(name = "id3", width = 15)
    @ApiModelProperty(value = "id3")
    private String id3;
	/**seq*/
	@Excel(name = "seq", width = 15)
    @ApiModelProperty(value = "seq")
    private BigDecimal seq;
}
