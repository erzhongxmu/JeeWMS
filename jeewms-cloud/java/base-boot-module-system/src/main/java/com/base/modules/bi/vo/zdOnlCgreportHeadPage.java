package com.base.modules.bi.vo;

import java.util.List;
import com.base.modules.bi.entity.zdOnlCgreportItem;
import com.base.modules.bi.entity.zdOnlCgreportParam;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: onl_cgreport_head
 * @Author: wms-cloud
 * @Date:   2020-12-09
 * @Version: V1.0
 */
@Data
@ApiModel(value="onl_cgreport_headPage对象", description="onl_cgreport_head")
public class zdOnlCgreportHeadPage {

	/**id*/
	@ApiModelProperty(value = "id")
	private String id;
	/**报表编码*/
	@Excel(name = "报表编码", width = 15)
	@ApiModelProperty(value = "报表编码")
	private String code;
	/**报表名字*/
	@Excel(name = "报表名字", width = 15)
	@ApiModelProperty(value = "报表名字")
	private String name;
	/**报表SQL*/
	@Excel(name = "报表SQL", width = 15)
	@ApiModelProperty(value = "报表SQL")
	private String cgrSql;
	/**返回值字段*/
	@Excel(name = "返回值字段", width = 15)
	@ApiModelProperty(value = "返回值字段")
	private String returnValField;
	/**返回文本字段*/
	@Excel(name = "返回文本字段", width = 15)
	@ApiModelProperty(value = "返回文本字段")
	private String returnTxtField;
	/**返回类型，单选或多选*/
	@Excel(name = "返回类型，单选或多选", width = 15)
	@ApiModelProperty(value = "返回类型，单选或多选")
	private String returnType;
	/**动态数据源*/
	@Excel(name = "动态数据源", width = 15)
	@ApiModelProperty(value = "动态数据源")
	private String dbSource;
	/**描述*/
	@Excel(name = "描述", width = 15)
	@ApiModelProperty(value = "描述")
	private String content;
	/**修改时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "修改时间")
	private Date updateTime;
	/**修改人id*/
	@ApiModelProperty(value = "修改人id")
	private String updateBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**创建人id*/
	@ApiModelProperty(value = "创建人id")
	private String createBy;
	/**仓库*/
	@Excel(name = "仓库", width = 15)
	@ApiModelProperty(value = "仓库")
	private String repository;

	@ExcelCollection(name="onl_cgreport_param")
	@ApiModelProperty(value = "onl_cgreport_param")
	private List<zdOnlCgreportParam> zdOnlCgreportParamList;
	@ExcelCollection(name="onl_cgreport_item")
	@ApiModelProperty(value = "onl_cgreport_item")
	private List<zdOnlCgreportItem> zdOnlCgreportItemList;

}
