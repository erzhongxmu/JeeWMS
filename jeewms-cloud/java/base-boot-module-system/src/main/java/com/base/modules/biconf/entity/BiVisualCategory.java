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
 * @Description: 大屏分类
 * @Author: base-boot
 * @Date:   2020-09-09
 * @Version: V1.0
 */
@Data
@TableName("bi_visual_category")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="bi_visual_category对象", description="大屏分类")
public class BiVisualCategory implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id;
	/**分类键值*/
	@Excel(name = "分类键值", width = 15)
    @ApiModelProperty(value = "分类键值")
    private String categoryKey;
	/**分类名称*/
	@Excel(name = "分类名称", width = 15)
    @ApiModelProperty(value = "分类名称")
    private String categoryValue;
	/**是否已删除*/
	@Excel(name = "是否已删除", width = 15)
    @ApiModelProperty(value = "是否已删除")
    private Integer isDeleted;
}
