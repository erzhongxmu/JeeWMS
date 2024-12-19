package com.base.modules.jeewms.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodsRegister {
    /**
     * 是商品唯一主键
     */
    private String skuId;
    /**
     * 是商品名称
     */
    private String goodsName;

    /**
     * 是商品ID
     */
    private String goodsId;

    /**
     * 是商品料号，对应商品的SKU编码
     */
    private String materialNo;
    /**
     * 是商品10位HS编码
     */
    private String goodsNo;
    /**
     * 是商品备案序号
     */
    private Integer seqNo;
    /**
     * 是商品规格型号
     */
    private String model;
    /**
     * 是币种，
     */
    private String currency;
    /**
     * 是申报单位
     */
    private String quantityUnitCode;
    /**
     * 是法定单位
     */
    private String legalUnitCode;
    /**
     * 否第⼆法定单位
     */
    private String secondLegalUnitCode;
    /**
     * 否重量⽐例因⼦
     */
    private BigDecimal weightScaleFactor;
    /**
     * 是第⼀⽐例因⼦
     */
    private BigDecimal legalScaleFactor;
    /**
     * 否第⼆⽐例因⼦
     */
    private BigDecimal secondLegalScaleFactor;
    /**
     * 否第⼆⽐例因⼦
     */
    private String barCode;
    /**
     * 是商品原产国代码
     */
    private String originCountry;
    /**
     * 否商品品牌
     */
    private String brandName;
    /**
     * 是重量单位
     */
    private String weightUnitCode;
    /**
     * 否⽑重
     */
    private BigDecimal grossWeight;
    /**
     * 否净重
     */
    private BigDecimal netWeight;
    /**
     * 否备注
     */
    private String remark;


}