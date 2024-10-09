package com.zzjee.yongyoubase.openapi4j.examples.attendance;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.AttendanceService;

/**
 * Copyright(c) 2015-2015 by yonyouup. All Rights Reserved 考勤信息
 * 
 * @author yanwuyang
 * @version <类版本> , 2015年12月9日
 * @see <相关类/方法>
 * @since <产品/模块版本>
 */
public class AttendanceBatchGet {

    final static Logger logger = LogManager.getLogger(AttendanceBatchGet.class);

    public static void main(String[] args) {
        String to_account = args[0];// 提供方id
        String page_index = args[1];// 页号
        String rows_per_page = args[2];// 每页行数
        String person_code = args[3];// 员工编码，可以通过 api/person 获取
        String dept_name = args[4];// 员工所在部门的名称关键字，可以通过 api/department 获取
        String dept_code = args[5];// 员工所在部门的编码，可以通过 api/department 获取
        String date_begin = args[6];// 起始日期，格式：yyyy-MM-dd
        String date_end = args[7];// 结束日期，格式：yyyy-MM-dd
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("to_account", to_account);
        paramMap.put("dept_code", dept_code);
        paramMap.put("dept_name", dept_name);
        paramMap.put("page_index", page_index);
        paramMap.put("rows_per_page", rows_per_page);
        paramMap.put("person_code", person_code);
        paramMap.put("date_begin", date_begin);
        paramMap.put("date_end", date_end);
        AttendanceService attendanceService = new AttendanceService();
        try {
            JSONObject record = attendanceService.batchGet(paramMap);
            logger.info(record.toString());
        } catch (OpenAPIException e) {
            e.printStackTrace();
        }
    }

}
