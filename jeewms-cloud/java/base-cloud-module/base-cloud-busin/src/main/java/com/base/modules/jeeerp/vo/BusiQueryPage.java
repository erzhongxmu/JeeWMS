package com.base.modules.jeeerp.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.base.modules.jeeerp.entity.BusiPoItem;
import com.base.modules.util.CustomDoubleSerializer;
import com.base.modules.util.CustomDoubleSerializer2;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Description: busi_po
 * @Author: base-boot
 * @Date:   2022-11-23
 * @Version: V1.0
 */
@Data
@ApiModel(value="BusiQueryPage对象", description="busi_po")
public class BusiQueryPage {
	private String query01;
	private String query02;
	private String query03;
	private String query04;
	private String query05;
	private String query06;
	private String query07;
	private String query08;
	private String query09;
	private String query10;
	private String query11;
	private String query12;
	private String query13;
	private String query14;
	private String query15;
	private String query16;
	private String query17;
	private String query18;
	private String query19;
	private String query20;
	private String type;
}
