package com.base.common.base.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItem {

    /**
     * 序号
     */
    private String	goods_order;

    /**
     * 账册备案料号
     */
    private String	product_no;

    /**
     * 企业商品货号
     */
    private String	sku_id;

    /**
     * 商品编码
     */
    private String	hs_code;

    /**
     * 商品名称
     */
    private String	goods_name;

    /**
     * 商品规格型号
     */
    private String	goods_model;

    /**
     * 条码
     */
    private String	bar_code;

    /**
     * 原产国（地区）
     */
    private String	origin_country;

    /**
     * 数量
     */
    private String	qty;

    /**
     * 法定数量
     */
    private String	first_count;

    /**
     * 第二数量
     * 非必须
     */
    private String	second_count;

    /**
     * 计量单位
     */
    private String	unit;

    /**
     * 法定计量单位
     */
    private String	first_unit;

    /**
     * 第二计量单位
     * 非必须
     */
    private String	second_unit;

    /**
     * 毛重
     */
    private String	weight;

    /**
     * 单价
     */
    private String	unit_price;

    /**
     * 备注
     * 非必须
     */
    private String	note;
}
