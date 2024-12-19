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
 * @Description: wv_stock_stt_query
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Data
@TableName("wv_stock_stt_query")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="wv_stock_stt_query对象", description="wv_stock_stt_query")
public class WvStockSttQuery implements Serializable {
    private static final long serialVersionUID = 1L;

	/**createTime*/
    @ApiModelProperty(value = "createTime")
    private String createTime;
	/**createName*/
	@Excel(name = "createName", width = 15)
    @ApiModelProperty(value = "createName")
    private String createName;
	/**createBy*/
    @ApiModelProperty(value = "createBy")
    private String createBy;
	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
	/**kuctype*/
	@Excel(name = "kuctype", width = 15)
    @ApiModelProperty(value = "kuctype")
    private String kuctype;
	/**kuWeiBianMa*/
	@Excel(name = "kuWeiBianMa", width = 15)
    @ApiModelProperty(value = "kuWeiBianMa")
    private String kuWeiBianMa;
	/**binId*/
	@Excel(name = "binId", width = 15)
    @ApiModelProperty(value = "binId")
    private String binId;
	/**cusCode*/
	@Excel(name = "cusCode", width = 15)
    @ApiModelProperty(value = "cusCode")
    private String cusCode;
	/**zhongWenQch*/
	@Excel(name = "zhongWenQch", width = 15)
    @ApiModelProperty(value = "zhongWenQch")
    private String zhongWenQch;
	/**商品编码*/
	@Excel(name = "商品编码", width = 15)
    @ApiModelProperty(value = "商品编码")
    private String goodsCode;
	/**goodsId*/
	@Excel(name = "goodsId", width = 15)
    @ApiModelProperty(value = "goodsId")
    private String goodsId;
	/**goodsQua*/
	@Excel(name = "goodsQua", width = 15)
    @ApiModelProperty(value = "goodsQua")
    private Integer goodsQua;
	/**商品名称*/
	@Excel(name = "商品名称", width = 15)
    @ApiModelProperty(value = "商品名称")
    private String shpMingCheng;
	/**goodsProData*/
	@Excel(name = "goodsProData", width = 15)
    @ApiModelProperty(value = "goodsProData")
    private String goodsProData;
	/**goodsBatch*/
	@Excel(name = "goodsBatch", width = 15)
    @ApiModelProperty(value = "goodsBatch")
    private String goodsBatch;
	/**保质期*/
	@Excel(name = "保质期", width = 15)
    @ApiModelProperty(value = "保质期")
    private String bzhiQi;
	/**毛重*/
	@Excel(name = "毛重", width = 15)
    @ApiModelProperty(value = "毛重")
    private String yushoutianshu;
	/**goodsUnit*/
	@Excel(name = "goodsUnit", width = 15)
    @ApiModelProperty(value = "goodsUnit")
    private String goodsUnit;
	/**tenantId*/
	@Excel(name = "tenantId", width = 15)
    @ApiModelProperty(value = "tenantId")
    private Integer tenantId;
}
