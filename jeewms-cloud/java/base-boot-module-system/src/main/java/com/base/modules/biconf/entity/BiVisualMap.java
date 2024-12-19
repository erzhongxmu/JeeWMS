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
 * @Description: 地图主数据
 * @Author: base-boot
 * @Date:   2020-09-09
 * @Version: V1.0
 */
@Data
@TableName("bi_visual_map")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="bi_visual_map对象", description="地图主数据")
public class BiVisualMap implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id;
	/**地图名称*/
	@Excel(name = "地图名称", width = 15)
    @ApiModelProperty(value = "地图名称")
    private String name;
	/**地图数据*/
	@Excel(name = "地图数据", width = 15)
    @ApiModelProperty(value = "地图数据")
    private String data;
}
