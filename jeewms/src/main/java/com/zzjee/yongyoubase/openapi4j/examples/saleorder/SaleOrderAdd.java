package com.zzjee.yongyoubase.openapi4j.examples.saleorder;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.SaleOrderService;

/**
 * Copyright(c) 2015-2015 by yonyouup. All Rights Reserved 销售订单
 * 
 * @author yanwuyang
 * @version <类版本> , 2015年12月9日
 * @see <相关类/方法>
 * @since <产品/模块版本>
 */
public class SaleOrderAdd {

    final static Logger logger = LogManager.getLogger(SaleOrderAdd.class);

    public static void main(String[] args) {
        String to_account = args[0];//提供方id
        String jsonBody = args[1];//body体数据
        SaleOrderService orderService = new SaleOrderService();
        try {
            JSONObject record = orderService.add(jsonBody, to_account);
            logger.info(record.toString());
        } catch (OpenAPIException e) {
            e.printStackTrace();
        }
    }

}
