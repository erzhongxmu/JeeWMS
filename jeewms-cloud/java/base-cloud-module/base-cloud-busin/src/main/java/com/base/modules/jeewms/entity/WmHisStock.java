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
 * @Description: wm_his_stock
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
@Data
@TableName("wm_his_stock")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="wm_his_stock对象", description="wm_his_stock")
public class WmHisStock implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**结余日期*/
	@Excel(name = "结余日期", width = 15)
    @ApiModelProperty(value = "结余日期")
    private String hisDate;
	/**货主*/
	@Excel(name = "货主", width = 15)
    @ApiModelProperty(value = "货主")
    private String cusCode;
	/**储位*/
	@Excel(name = "储位", width = 15)
    @ApiModelProperty(value = "储位")
    private String kuWeiBianMa;
	/**托盘*/
	@Excel(name = "托盘", width = 15)
    @ApiModelProperty(value = "托盘")
    private String binId;
	/**商品*/
	@Excel(name = "商品", width = 15)
    @ApiModelProperty(value = "商品")
    private String goodsId;
	/**数量*/
	@Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
    private String count;
	/**单位*/
	@Excel(name = "单位", width = 15)
    @ApiModelProperty(value = "单位")
    private String baseUnit;

    /**租户*/
    // @Excel(name = "租户", width = 15)
    @ApiModelProperty(value = "租户")
    private Integer tenantId;
}
