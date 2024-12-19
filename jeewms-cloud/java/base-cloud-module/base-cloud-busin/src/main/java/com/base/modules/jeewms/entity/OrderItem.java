package com.base.modules.jeewms.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderItem implements Serializable {
    private String              subOrderSn              ; //子订单号,不传WMS

    private String 				sku_id					;//(不超过64个字符)   SkuId
    private String 				product_no				;//(不超过100个字符   海关备案料号
    private int			        qty						;//数量
    private BigDecimal			declare_qty		        ;//申报数量
    private BigDecimal			total_price		        ;//表体单项总价
    private int					weight					;//重量 单位：克
    private BigDecimal					unit_price				;//单价 单位：分
    private BigDecimal					sku_unit_price				;//单价 单位：分 SKU真实单价,用于线下门店POS
    private int					unit_price_aliases		;//单价别名 单位：分
    private String 				code_ts					;//(不超过60个字符)	行邮税号
    private String 				origin_country			;//(不超过255个字符)	原产国代码
    private String 				hs_code					;//(不超过128个字符)	海关HS编码
    private String 				goods_name				;//(不超过100个字符)	商品名称
    private String 				goods_name_aliases		;//(不超过100个字符)	商品别名
    private String 				goods_english_name		;//(不超过500个字符)	商品英文名称
    private String 				goods_model				;//(不超过200个字符)	商品规格、型号
    private String 				declare_goods_unit		;//(不超过100个字符)	申报计量单位编码
    private BigDecimal declare_goods_count		;//申报数量
    private String 				first_unit				;//(不超过255个字符)	第一单位编码
    private BigDecimal			first_count				;//第一数量
    private String 				second_unit				;//(不超过255个字符)	第二单位编码
    private BigDecimal			second_count			;//第二数量
    private int					goods_order				;//商品在订单中的顺序
    private String 				unit					;//(不超过100个字符)	否	商品的申报计量单位
    private String 				qty_grade				;//(不超过2个字符)	否	质量等级 A：次品 B：耳机残次 C：一级残次 D：临期品 E：良品 F：退货品
    private BigDecimal			goods_real_time_cost	;//实时成本 RSA加密 币种：人民币 单位：分
    private List<BatchDetail> batch_detail			;//出入库批次明细
    private String 				bar_code				;//商品编码
    private String              batch_no                ;//库存批次
}
