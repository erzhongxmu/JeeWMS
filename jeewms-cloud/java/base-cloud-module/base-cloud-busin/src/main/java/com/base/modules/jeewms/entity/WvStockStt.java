package com.base.modules.jeewms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

@Data
@TableName("wv_stock_stt")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="wv_stock_stt对象", description="托盘转移")
public class WvStockStt implements Serializable {
    private static final long serialVersionUID = 1L;

    /**主键*/
    @TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**createName*/
	//@Excel(name = "createName", width = 15)
    @ApiModelProperty(value = "createName")
    private java.lang.String createName;
	/**createBy*/
    @ApiModelProperty(value = "createBy")
    private java.lang.String createBy;
	/**库存类型*/
	@Excel(name = "库存类型", width = 15,orderNum = "7")
    @ApiModelProperty(value = "库存类型")
    private java.lang.String kuctype;
	/**kuWeiBianMa*/
	@Excel(name = "库位编码", width = 15,orderNum = "5")
    @ApiModelProperty(value = "储位")
    private java.lang.String kuWeiBianMa;
	/**储位*/
	@Excel(name = "托盘", width = 15,orderNum = "6")
    @ApiModelProperty(value = "托盘")
    private java.lang.String binId;
	/**货主编码*/
	@Excel(name = "供应商编码", width = 15,orderNum = "3")
    @ApiModelProperty(value = "供应商编码")
    private java.lang.String cusCode;
	/**货主*/
	@Excel(name = "供应商名称", width = 15,orderNum = "4")
    @ApiModelProperty(value = "供应商名称")
    private java.lang.String zhongWenQch;
	/**goodsCode*/
	//@Excel(name = "goodsCode", width = 15)
    @ApiModelProperty(value = "goodsCode")
    private java.lang.String goodsCode;
	/**商品编码*/
	@Excel(name = "商品编码", width = 15,orderNum = "1")
    @ApiModelProperty(value = "商品编码")
    private java.lang.String goodsId;
	/**商品名称*/
	@Excel(name = "商品名称", width = 15,orderNum = "2")
    @ApiModelProperty(value = "商品名称")
    private java.lang.String shpMingCheng;
	/**商品数量*/
	//@Excel(name = "商品数量", width = 15)
    @ApiModelProperty(value = "商品数量")
    private java.lang.Integer goodsQua;
	/**goodsProData*/
	//@Excel(name = "生产日期", width = 15)
    @ApiModelProperty(value = "生产日期")
    private java.lang.String goodsProData;
	/**保质期*/
	//@Excel(name = "保质期", width = 15)
    @ApiModelProperty(value = "保质期")
    private java.lang.String bzhiQi;
	/**yushoutianshu*/
	//@Excel(name = "允收天数", width = 15)
    @ApiModelProperty(value = "允收天数")
    private java.lang.String yushoutianshu;
	/**单位*/
	//@Excel(name = "单位", width = 15)
    @ApiModelProperty(value = "单位")
    private java.lang.String goodsUnit;
	/**状态*/
	//@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.String sttSta;
	/**moveSta*/
	//@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.String moveSta;
	/**移动日期*/
	@Excel(name = "移动日期", width = 15, format = "yyyy-MM-dd",orderNum = "8")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "移动日期")
    private java.util.Date lastMove;
    /**创建日期*/
    @ApiModelProperty(value = "创建日期")
    private java.lang.String createTime;
    @ApiModelProperty(value = "批次")
    private java.lang.String goodsBatch;

    @TableField(exist = false)
    private String areaCode;

    private String tenantId;
}
