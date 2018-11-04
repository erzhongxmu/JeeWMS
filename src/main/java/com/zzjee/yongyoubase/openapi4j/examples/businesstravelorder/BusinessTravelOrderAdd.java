package com.zzjee.yongyoubase.openapi4j.examples.businesstravelorder;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.BusinessTravelOrderService;

/**
 * Copyright(c) 2015-2015 by yonyouup. All Rights Reserved 
 * 商旅订单新增
 * @author yanwuyang
 * @version <类版本> , 2015年12月9日
 * @see <相关类/方法>
 * @since <产品/模块版本>
 */
public class BusinessTravelOrderAdd {

    final static Logger logger = LogManager.getLogger(BusinessTravelOrderAdd.class);

    public static void main(String[] args) {
        String to_account = args[0];// 提供方id 
        String jsonBody = args[1];//body体数据
        BusinessTravelOrderService businessTravelOrderService = new BusinessTravelOrderService();
        try {
            JSONObject record = businessTravelOrderService.add(jsonBody, to_account);
            logger.info(record.toString());
        } catch (OpenAPIException e) {
            e.printStackTrace();
        }
    }

}
