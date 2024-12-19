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
 * @Description: wm_day_his
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
@Data
@TableName("wm_day_his")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="wm_day_his对象", description="wm_day_his")
public class WmDayHis implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "id")
    private String id;
	/**hisDate*/
	@Excel(name = "hisDate", width = 15)
    @ApiModelProperty(value = "hisDate")
    private String hisDate;
	/**hisDayIn*/
	@Excel(name = "hisDayIn", width = 15)
    @ApiModelProperty(value = "hisDayIn")
    private String hisDayIn;
	/**hisDayOut*/
	@Excel(name = "hisDayOut", width = 15)
    @ApiModelProperty(value = "hisDayOut")
    private String hisDayOut;
	/**hisDayAmount*/
	@Excel(name = "hisDayAmount", width = 15)
    @ApiModelProperty(value = "hisDayAmount")
    private String hisDayAmount;
}
