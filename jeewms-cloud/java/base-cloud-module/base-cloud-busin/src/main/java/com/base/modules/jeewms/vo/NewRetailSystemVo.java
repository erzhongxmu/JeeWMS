package com.base.modules.jeewms.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description: TODO
 * @author: scott
 * @date: 2022年02月10日 9:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewRetailSystemVo {
    //仓储系统仓库编号
    private String storageMerchantCode;
    //仓储系统单据单号
    private String storageRecordNo;
    //新零售系统单据单号
    private String recordNo;
    //单据类型 配送出库 SEND_OUT_GOODS, 入库 STOCK_IN,
    // 盘点 INVENTORY, 门店/仓库退货 RECORD_REFUND, 配送差异入库DELIVERY_DIFF_REFUND
    private String recordType;
    //商品明细，需转为json，具体属性见商品明细属性表
    private String goodsDetails;
    //操作时间
    private String operationTime;
}
