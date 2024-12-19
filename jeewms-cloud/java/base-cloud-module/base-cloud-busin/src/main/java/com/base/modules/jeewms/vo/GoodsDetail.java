package com.base.modules.jeewms.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: TODO
 * @author: scott
 * @date: 2022年02月10日 9:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDetail {
    //出入库实际条码
    private String goodsBarcode;
    //最小包装条码
    private String smallGoodsBarcode;
    //进货出货包装件数
    private String goodsItemCnt;
    //商品数量、盘点时为实盘库存数量
    private String goodsCnt;
    //商品最小包装数量
    private String goodsUnitCnt;
    //出入库类型 0 入库，1 出库
    private String type;
    //生产日期
    private String productionDate;
    //保质日期
    private String expirationDate;
    //库位编号
    private String stockNumber;
    //批次号
    private String batchNumber;
}
