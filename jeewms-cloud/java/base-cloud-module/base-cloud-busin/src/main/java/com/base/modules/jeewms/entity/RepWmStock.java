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
 * @Description: rep_wm_stock
 * @Author: base-boot
 * @Date:   2022-05-09
 * @Version: V1.0
 */
@Data
@TableName("rep_wm_stock")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="rep_wm_stock对象", description="rep_wm_stock")
public class RepWmStock implements Serializable {
    private static final long serialVersionUID = 1L;

	/**库存类型*/
	@Excel(name = "库存类型", width = 15)
    @ApiModelProperty(value = "库存类型")
    private String kuctype;
	/**数量*/
	@Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
    private Integer baseGoodscount;
	/**单位*/
	@Excel(name = "单位", width = 15)
    @ApiModelProperty(value = "单位")
    private String baseUnit;
	/**库位*/
	@Excel(name = "库位", width = 15)
    @ApiModelProperty(value = "库位")
    private String kuWeiBianMa;
	/**托盘*/
	@Excel(name = "托盘", width = 15)
    @ApiModelProperty(value = "托盘")
    private String binId;
	/**货主*/
	@Excel(name = "货主", width = 15)
    @ApiModelProperty(value = "货主")
    private String cusCode;
	/**中文全称*/
	@Excel(name = "中文全称", width = 15)
    @ApiModelProperty(value = "中文全称")
    private String cusName;
	/**商品编码*/
	@Excel(name = "商品编码", width = 15)
    @ApiModelProperty(value = "商品编码")
    private String goodsId;
	/**商品名称*/
	@Excel(name = "商品名称", width = 15)
    @ApiModelProperty(value = "商品名称")
    private String shpMingCheng;
	/**生产日期*/
	@Excel(name = "生产日期", width = 15)
    @ApiModelProperty(value = "生产日期")
    private String goodsProData;
	/**商品规格*/
	@Excel(name = "商品规格", width = 15)
    @ApiModelProperty(value = "商品规格")
    private String shpGuiGe;
	/**库位类型*/
	@Excel(name = "库位类型", width = 15)
    @ApiModelProperty(value = "库位类型")
    private String kuWeiLeiXing;
	/**租户*/
	// @Excel(name = "租户", width = 15)
    @ApiModelProperty(value = "租户")
    private Integer tenantId;
}
