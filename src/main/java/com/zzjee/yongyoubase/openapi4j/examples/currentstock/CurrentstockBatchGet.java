package com.zzjee.yongyoubase.openapi4j.examples.currentstock;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.CurrentstockService;

/**
 * Copyright(c) 2015-2015 by yonyouup. All Rights Reserved 现存量查询
 * 
 * @author yanwuyang
 * @version <类版本> , 2015年12月9日
 * @see <相关类/方法>
 * @since <产品/模块版本>
 */
public class CurrentstockBatchGet {

    final static Logger logger = LogManager.getLogger(CurrentstockBatchGet.class);

    public static void main(String[] args) {
        String to_account = args[0];// 提供方id
        String page_index = args[1];// 页号
        String rows_per_page = args[2];// 每页行数
        String whcode_begin = args[3];// 起始仓库编码
        String whcode_end = args[4];// 结束仓库编码
        String whname = args[5];// 仓库名称关键字
        String invcode_begin = args[6];// 起始存货编码
        String invcode_end = args[7];// 结束存货编码
        String invname = args[8];// 存货名称关键字
        String batch = args[9];// 批号

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("to_account", to_account);
        paramMap.put("page_index", page_index);
        paramMap.put("rows_per_page", rows_per_page);
        paramMap.put("whcode_begin", whcode_begin);
        paramMap.put("whcode_end", whcode_end);
        paramMap.put("invcode_begin", invcode_begin);
        paramMap.put("invcode_end", invcode_end);
        paramMap.put("whname", whname);
        paramMap.put("invname", invname);
        paramMap.put("batch", batch);
        CurrentstockService currentstockService = new CurrentstockService();
        try {
            JSONObject record = currentstockService.batchGet(paramMap);
            logger.info(record.toString());
        } catch (OpenAPIException e) {
            e.printStackTrace();
        }
    }

}
