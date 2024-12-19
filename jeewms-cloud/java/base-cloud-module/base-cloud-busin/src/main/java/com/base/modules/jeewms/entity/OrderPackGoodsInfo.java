package com.base.modules.jeewms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *  wms:订单推送包材中的商品对象模型(OrderPackGoodsInfo)
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPackGoodsInfo implements Serializable {


   private String sku_id;   // (不超过64个字符)	必填	SkuId
   private int    qty	;	//必填	商品数量
}
