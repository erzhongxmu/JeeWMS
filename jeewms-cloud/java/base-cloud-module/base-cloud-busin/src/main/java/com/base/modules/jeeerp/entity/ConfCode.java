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
 * @Description: conf_code
 * @Author: base-boot
 * @Date:   2022-04-06
 * @Version: V1.0
 */
@Data
@TableName("conf_code")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="conf_code对象", description="conf_code")
public class ConfCode implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
   //	 @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    //	@ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**逻辑删除*/
	//@Excel(name = "逻辑删除", width = 15)
   //	 @ApiModelProperty(value = "逻辑删除")
    private Integer isDel;
	/**备用1*/
//	//	@Excel(name = "备用1", width = 15)
//   //	@ApiModelProperty(value = "备用1")
    private String attr1;
	/**备用2*/
	//	@Excel(name = "备用2", width = 15)
   //	@ApiModelProperty(value = "备用2")
    private String attr2;
	/**备用3*/
	//	@Excel(name = "备用3", width = 15)
   //	@ApiModelProperty(value = "备用3")
    private String attr3;
	/**备用4*/
	//	@Excel(name = "备用4", width = 15)
   //	@ApiModelProperty(value = "备用4")
    private String attr4;
	/**备用5*/
	//	@Excel(name = "备用5", width = 15)
   //	@ApiModelProperty(value = "备用5")
    private String attr5;
	/**租户字段*/
	//	 //@Excel(name = "租户字段", width = 15))
    //	 @ApiModelProperty(value = "租户字段")
    private String tenantId;
    /**表名*/
    @Excel(name = "表名", width = 15)
    @ApiModelProperty(value = "表名")
    private String tableName;
	/**编码类型*/
	@Excel(name = "编码类型", width = 15)
    @ApiModelProperty(value = "编码类型")
    private String codeType;
	/**编码前缀*/
	@Excel(name = "编码前缀", width = 15)
    @ApiModelProperty(value = "编码前缀")
    private String codePer;
	/**时间格式*/
	@Excel(name = "时间格式", width = 15)
    @ApiModelProperty(value = "时间格式")
    private String timeForm;
	/**编码后缀*/
	@Excel(name = "编码后缀", width = 15)
    @ApiModelProperty(value = "编码后缀")
    private String codeLast;
}
