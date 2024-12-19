package com.base.modules.jeewms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Description: wave_to_down
 * @Author: base-boot
 * @Date:   2021-10-20
 * @Version: V1.0
 */
@Data
@TableName("wave_to_down")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="wave_to_down对象", description="wave_to_down")
public class WaveToDown implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人登录名称*/
    @ApiModelProperty(value = "创建人登录名称")
    private java.lang.String createBy;
	/**创建人名称*/
	// @Excel(name = "创建人名称", width = 15)
    @ApiModelProperty(value = "创建人名称")
    private java.lang.String createName;
	/**货主*/
	@Excel(name = "货主", width = 15)
    @ApiModelProperty(value = "货主")
    private java.lang.String cusCode;
	/**客户名称*/
	@Excel(name = "客户名称", width = 15)
    @ApiModelProperty(value = "客户名称")
    private java.lang.String cusName;
	/**波次号*/
	@Excel(name = "波次号", width = 15)
    @ApiModelProperty(value = "波次号")
    private java.lang.String waveId;
	/**商品编码*/
	@Excel(name = "商品编码", width = 15)
    @ApiModelProperty(value = "商品编码")
    private java.lang.String goodsId;
	/**商品名称*/
	@Excel(name = "商品名称", width = 15)
    @ApiModelProperty(value = "商品名称")
    private java.lang.String goodsName;
	/**单号*/
	@Excel(name = "单号", width = 15)
    @ApiModelProperty(value = "单号")
    private java.lang.String imCusCode;
	/**仓位*/
	@Excel(name = "仓位", width = 15)
    @ApiModelProperty(value = "仓位")
    private java.lang.String binId;
	/**托盘*/
	@Excel(name = "托盘", width = 15)
    @ApiModelProperty(value = "托盘")
    private java.lang.String tinId;
	/**生产日期*/
	@Excel(name = "生产日期", width = 15)
    @ApiModelProperty(value = "生产日期")
    private java.lang.String proData;
	/**数量*/
	@Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
    private java.lang.Double baseGoodscount;
	/**出库备注*/
	@Excel(name = "出库备注", width = 15)
    @ApiModelProperty(value = "出库备注")
    private java.math.BigDecimal omBeiZhu;
	/**基本单位*/
	@Excel(name = "基本单位", width = 15)
    @ApiModelProperty(value = "基本单位")
    private java.lang.String baseUnit;
	/**一次下架容器*/
	@Excel(name = "一次下架容器", width = 15)
    @ApiModelProperty(value = "一次下架容器")
    private java.lang.String firstRq;
	/**分拣容器*/
	@Excel(name = "分拣容器", width = 15)
    @ApiModelProperty(value = "分拣容器")
    private java.lang.String secondRq;
	/**by1*/
	@Excel(name = "by1", width = 15)
    @ApiModelProperty(value = "by1")
    private java.lang.String by1;
	/**by2*/
	@Excel(name = "by2", width = 15)
    @ApiModelProperty(value = "by2")
    private java.lang.String by2;
	/**by3*/
	@Excel(name = "by3", width = 15)
    @ApiModelProperty(value = "by3")
    private java.lang.String by3;
	/**by4*/
	@Excel(name = "by4", width = 15)
    @ApiModelProperty(value = "by4")
    private java.lang.String by4;
	/**by5*/
	@Excel(name = "by5", width = 15)
    @ApiModelProperty(value = "by5")
    private java.lang.String by5;
	/**商品条码*/
	@Excel(name = "商品条码", width = 15)
    @ApiModelProperty(value = "商品条码")
    private java.lang.String shpTiaoMa;
}
