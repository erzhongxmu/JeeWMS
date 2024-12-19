package com.base.modules.approle.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.base.common.aspect.annotation.Dict;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: APP功能
 * @Author: jeecg-boot
 * @Date:   2021-10-19
 * @Version: V1.0
 */
@Data
@TableName("mes_app_function")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="mes_app_function对象", description="APP功能")
public class MesAppFunction implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
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
	/**app模块编号*/
	@Excel(name = "app模块编号", width = 15)
    @ApiModelProperty(value = "app模块编号")
    private String appmodelCode;
	/**app模块名称*/
	@Excel(name = "app模块名称", width = 15)
    @ApiModelProperty(value = "app模块名称")
    private String appmodelName;
	/**app模块排序*/
	@Excel(name = "app模块排序", width = 15)
    @ApiModelProperty(value = "app模块排序")
    private String appmodelSort;
	/**类型*/
	@Excel(name = "类型", width = 15)
    @ApiModelProperty(value = "类型")
    private String type;
	/**路径*/
	@Excel(name = "路径", width = 15)
    @ApiModelProperty(value = "路径")
    private String route;
	/**图片*/
	@Excel(name = "图片", width = 15)
    @ApiModelProperty(value = "图片")
    private String picture;
	/**是否禁用*/
    @Excel(name = "是否禁用", width = 15, dicCode = "yn")
    @Dict(dicCode = "yn")
    @ApiModelProperty(value = "是否禁用")
    private String ifBind;
	/**备用1*/
	@Excel(name = "app多语言key", width = 15)
    @ApiModelProperty(value = "app多语言key")
    private String query1;
	/**备用2*/
//	@Excel(name = "备用2", width = 15)
    @ApiModelProperty(value = "备用2")
    private String query2;
	/**备用3*/
//	@Excel(name = "备用3", width = 15)
    @ApiModelProperty(value = "备用3")
    private String query3;
	/**备用4*/
//	@Excel(name = "备用4", width = 15)
    @ApiModelProperty(value = "备用4")
    private String query4;
	/**备用5*/
//	@Excel(name = "备用5", width = 15)
    @ApiModelProperty(value = "备用5")
    private String query5;
	/**备用6*/
//	@Excel(name = "备用6", width = 15)
    @ApiModelProperty(value = "备用6")
    private String query6;
}
