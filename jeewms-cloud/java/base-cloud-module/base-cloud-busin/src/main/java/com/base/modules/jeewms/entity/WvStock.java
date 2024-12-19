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
 * @Description: wv_stock
 * @Author: base-boot
 * @Date:   2021-05-28
 * @Version: V1.0
 */
@Data
@TableName("wv_stock")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="wv_stock对象", description="wv_stock")
public class WvStock implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**createDate*/
	@Excel(name = "create_time", width = 15)
    @ApiModelProperty(value = "create_time")
    private java.lang.String createTime;
	/**createName*/
	@Excel(name = "createName", width = 15)
    @ApiModelProperty(value = "createName")
    private java.lang.String createName;
	/**createBy*/
    @ApiModelProperty(value = "createBy")
    private java.lang.String createBy;
	/**kuctype*/
	@Excel(name = "kuctype", width = 15)
    @ApiModelProperty(value = "kuctype")
    private java.lang.String kuctype;
	/**goodsQua*/
	@Excel(name = "goodsQua", width = 15)
    @ApiModelProperty(value = "goodsQua")
    private java.lang.Integer goodsQua;
	/**goodsUnit*/
	@Excel(name = "goodsUnit", width = 15)
    @ApiModelProperty(value = "goodsUnit")
    private java.lang.String goodsUnit;
	/**baseGoodscount*/
	@Excel(name = "baseGoodscount", width = 15)
    @ApiModelProperty(value = "baseGoodscount")
    private java.lang.Integer baseGoodscount;
	/**baseUnit*/
	@Excel(name = "baseUnit", width = 15)
    @ApiModelProperty(value = "baseUnit")
    private java.lang.String baseUnit;
	/**kuWeiBianMa*/
	@Excel(name = "kuWeiBianMa", width = 15)
    @ApiModelProperty(value = "kuWeiBianMa")
    private java.lang.String kuWeiBianMa;
	/**binId*/
	@Excel(name = "binId", width = 15)
    @ApiModelProperty(value = "binId")
    private java.lang.String binId;
	/**cusCode*/
	@Excel(name = "cusCode", width = 15)
    @ApiModelProperty(value = "cusCode")
    private java.lang.String cusCode;
	/**zhongWenQch*/
	@Excel(name = "zhongWenQch", width = 15)
    @ApiModelProperty(value = "zhongWenQch")
    private java.lang.String zhongWenQch;
	/**goodsId*/
	@Excel(name = "goodsId", width = 15)
    @ApiModelProperty(value = "goodsId")
    private java.lang.String goodsId;
	/**goodsCode*/
	@Excel(name = "goodsCode", width = 15)
    @ApiModelProperty(value = "goodsCode")
    private java.lang.String goodsCode;
	/**shpMingCheng*/
	@Excel(name = "shpMingCheng", width = 15)
    @ApiModelProperty(value = "shpMingCheng")
    private java.lang.String shpMingCheng;
	/**shlDanWei*/
	@Excel(name = "shlDanWei", width = 15)
    @ApiModelProperty(value = "shlDanWei")
    private java.lang.String shlDanWei;
	/**chlShl*/
	@Excel(name = "chlShl", width = 15)
    @ApiModelProperty(value = "chlShl")
    private java.lang.String chlShl;
	/**goodsProData*/
	@Excel(name = "goodsProData", width = 15)
    @ApiModelProperty(value = "goodsProData")
    private java.lang.String goodsProData;
	/**bzhiQi*/
	@Excel(name = "bzhiQi", width = 15)
    @ApiModelProperty(value = "bzhiQi")
    private java.lang.String bzhiQi;
	/**shpGuiGe*/
	@Excel(name = "shpGuiGe", width = 15)
    @ApiModelProperty(value = "shpGuiGe")
    private java.lang.String shpGuiGe;
	/**dqr*/
	@Excel(name = "dqr", width = 15)
    @ApiModelProperty(value = "dqr")
    private java.lang.String dqr;
	/**库位类型*/
	@Excel(name = "库位类型", width = 15)
    @ApiModelProperty(value = "库位类型")
    private java.lang.String kuWeiLeiXing;
	/**取货次序*/
	@Excel(name = "取货次序", width = 15)
    @ApiModelProperty(value = "取货次序")
    private java.lang.String quHuoCiXu;
	/**上架次序*/
	@Excel(name = "上架次序", width = 15)
    @ApiModelProperty(value = "上架次序")
    private java.lang.String shangJiaCiXu;
	/**仓库*/
	@Excel(name = "仓库", width = 15)
    @ApiModelProperty(value = "仓库")
    private java.lang.String binStore;
	/**tiJiCm*/
	@Excel(name = "tiJiCm", width = 15)
    @ApiModelProperty(value = "tiJiCm")
    private java.lang.String tiJiCm;
	/**体积单位*/
	@Excel(name = "体积单位", width = 15)
    @ApiModelProperty(value = "体积单位")
    private java.lang.String tiJiDanWei;
	/**重量单位*/
	@Excel(name = "重量单位", width = 15)
    @ApiModelProperty(value = "重量单位")
    private java.lang.String zhongLiangDanWei;
	/**面积单位*/
	@Excel(name = "面积单位", width = 15)
    @ApiModelProperty(value = "面积单位")
    private java.lang.String mianJiDanWei;
	/**最大体积*/
	@Excel(name = "最大体积", width = 15)
    @ApiModelProperty(value = "最大体积")
    private java.lang.String zuiDaTiJi;
	/**最大面积*/
	@Excel(name = "最大面积", width = 15)
    @ApiModelProperty(value = "最大面积")
    private java.lang.String zuiDaMianJi;
}
