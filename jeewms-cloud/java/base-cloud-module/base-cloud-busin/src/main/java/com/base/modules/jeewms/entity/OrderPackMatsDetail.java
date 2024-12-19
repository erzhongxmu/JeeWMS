package com.base.modules.jeewms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 *  WMS: 订单推送包材推荐对象模型(OrderPackMatsDetail)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPackMatsDetail implements Serializable {
    private String						box_pn				;	//纸箱型号
    private String						box_name			;	//纸箱名称
    private List<OrderPackGoodsInfo>    pack_goods_infos	;	//包材对应的商品详情
}
