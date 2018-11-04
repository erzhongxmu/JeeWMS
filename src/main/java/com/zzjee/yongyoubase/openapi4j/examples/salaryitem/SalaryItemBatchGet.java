package com.zzjee.yongyoubase.openapi4j.examples.salaryitem;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.SalaryItemService;

/**
 * Copyright(c) 2015-2015 by yonyouup. All Rights Reserved 工资项目 批量查询
 * 
 * @author yanwuyang
 * @version <类版本> , 2015年12月9日
 * @see <相关类/方法>
 * @since <产品/模块版本>
 */
public class SalaryItemBatchGet {

    final static Logger logger = LogManager.getLogger(SalaryItemBatchGet.class);

    public static void main(String[] args) {
        String to_account = args[0];// 提供方id
        String page_index = args[1];// 页号
        String rows_per_page = args[2];// 每页行数
        String code_begin = args[3];// 起始编码
        String code_end = args[4];// 结束编码
        String name = args[5];// 名称关键字

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("to_account", to_account);
        paramMap.put("page_index", page_index);
        paramMap.put("rows_per_page", rows_per_page);
        paramMap.put("code_begin", code_begin);
        paramMap.put("code_end", code_end);
        paramMap.put("name", name);

        SalaryItemService salaryItemService = new SalaryItemService();
        try {
            JSONObject record = salaryItemService.batchGet(paramMap);
            logger.info(record.toString());
        } catch (OpenAPIException e) {
            e.printStackTrace();
        }
    }

}
