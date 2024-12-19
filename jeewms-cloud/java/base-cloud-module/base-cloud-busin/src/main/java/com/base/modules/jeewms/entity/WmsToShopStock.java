package com.base.modules.jeewms.entity;

import lombok.Data;

/**
 * @Author JiWang
 * @PROJECT zdwms
 * @Description
 * @Date 2021/11/9 19:27
 **/
@Data
public class WmsToShopStock {

    /**
     * 库存类型
     */
    private String stockType;

    /**
     * 库存数量
     */
    private Integer stock;

    /**
     * 供应商编码
     */
    private String cusCode;

    /**
     * 商品编码
     */
    private String skuId;

    /**
     * 商品名
     */
    private String goodsName;

    /**
     * 库位编码
     */
    private String stockCode;

    /**
     * 良次品类型
     */
    private String goodsType;

    /**
     * 最小库存预警
     */
    private String minWarn;

    /**
     * 最大库存预警
     */
    private String maxWarn;

}
