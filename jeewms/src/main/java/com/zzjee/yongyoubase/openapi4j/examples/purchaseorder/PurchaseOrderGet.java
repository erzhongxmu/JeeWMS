package com.zzjee.yongyoubase.openapi4j.examples.purchaseorder;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.PurchaseOrderService;

/**
 * Copyright(c) 2015-2015 by yonyouup. All Rights Reserved 采购订单查询
 * 
 * @author yanwuyang
 * @version <类版本> , 2015年12月9日
 * @see <相关类/方法>
 * @since <产品/模块版本>
 */
public class PurchaseOrderGet {

    final static Logger logger = LogManager.getLogger(PurchaseOrderGet.class);

    public static void main(String[] args) {
        String to_account = args[0];//提供方id
        String id = args[1];//采购订单号
        PurchaseOrderService purchaseOrderService = new PurchaseOrderService();
        try {
            JSONObject record = purchaseOrderService.get(id, to_account);
            logger.info(record.toString());
        } catch (OpenAPIException e) {
            e.printStackTrace();
        }
    }

}
