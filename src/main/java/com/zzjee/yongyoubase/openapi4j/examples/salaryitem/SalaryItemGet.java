package com.zzjee.yongyoubase.openapi4j.examples.salaryitem;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.SalaryItemService;

/**
 * Copyright(c) 2015-2015 by yonyouup. All Rights Reserved 工资项目 
 * 
 * @author yanwuyang
 * @version <类版本> , 2015年12月9日
 * @see <相关类/方法>
 * @since <产品/模块版本>
 */
public class SalaryItemGet {

    final static Logger logger = LogManager.getLogger(SalaryItemGet.class);

    public static void main(String[] args) {
        String to_account = args[0];//提供方id
        String id = args[1];//工资项目标识
        SalaryItemService salaryItemService = new SalaryItemService();
        try {
            JSONObject record = salaryItemService.get(id, to_account);
            logger.info(record.toString());
        } catch (OpenAPIException e) {
            e.printStackTrace();
        }
    }

}
