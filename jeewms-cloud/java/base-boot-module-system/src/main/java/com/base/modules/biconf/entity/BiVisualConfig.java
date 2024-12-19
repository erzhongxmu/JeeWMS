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
 * @Description: 大屏页面配置数据
 * @Author: base-boot
 * @Date:   2020-09-09
 * @Version: V1.0
 */
@Data
@TableName("bi_visual_config")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="bi_visual_config对象", description="大屏页面配置数据")
public class BiVisualConfig implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id;
	/**可视化表主键*/
	@Excel(name = "可视化表主键", width = 15)
    @ApiModelProperty(value = "可视化表主键")
    private String visualId;
	/**配置json*/
	@Excel(name = "配置json", width = 15)
    @ApiModelProperty(value = "配置json")
    private String detail;
	/**组件json*/
	@Excel(name = "组件json", width = 15)
    @ApiModelProperty(value = "组件json")
    private String component;
}
