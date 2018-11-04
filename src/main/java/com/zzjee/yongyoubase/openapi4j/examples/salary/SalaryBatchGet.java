package com.zzjee.yongyoubase.openapi4j.examples.salary;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.SalaryService;

/**
 * Copyright(c) 2015-2015 by yonyouup. All Rights Reserved  工资记录批量查询
 * 
 * @author yanwuyang
 * @version <类版本> , 2015年12月9日
 * @see <相关类/方法>
 * @since <产品/模块版本>
 */
public class SalaryBatchGet {

    final static Logger logger = LogManager.getLogger(SalaryBatchGet.class);

    public static void main(String[] args) {
        String to_account = args[0];// 提供方id 
        String code_begin = args[3];//起始编码
        String code_end = args[4];// 结束编码 
        String departmentno = args[5];//员工所在部门的编码，可以通过 api/department 获取
        String month = args[6];// 月份 
        String name = args[7];// 名称关键字 
        String year = args[8];// 年份 
        
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("to_account", to_account);
        paramMap.put("code_begin", code_begin);
        paramMap.put("code_end", code_end);
        paramMap.put("departmentno", departmentno);
        paramMap.put("month", month);
        paramMap.put("name", name);
        paramMap.put("year", year);
        
        SalaryService salaryService = new SalaryService();
        try {
            JSONObject record = salaryService.batchGet(paramMap);
            logger.info(record.toString());
        } catch (OpenAPIException e) {
            e.printStackTrace();
        }
    }

}
