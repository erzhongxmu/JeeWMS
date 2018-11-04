package com.zzjee.yongyoubase.openapi4j.examples.purchaseorder;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.service.PurchaseOrderService;

/**
 * Copyright(c) 2015-2015 by yonyouup. All Rights Reserved 采购订单 批量查询
 * 
 * @author yanwuyang
 * @version <类版本> , 2015年12月9日
 * @see <相关类/方法>
 * @since <产品/模块版本>
 */
public class PurchaseOrderBatchGet {

    final static Logger logger = LogManager.getLogger(PurchaseOrderBatchGet.class);

    public static void main(String[] args) {
        String to_account = args[0];// 提供方id
        String page_index = args[1];// 页号
        String rows_per_page = args[2];// 每页行数
        String code_begin = args[3];// 起始订单编号
        String code_end = args[4];// 结束订单编号
        String state = args[5];// 订单状态
        String date_begin = args[6];// 起始订单日期，格式:yyyy-MM-dd
        String date_end = args[7];// 结束订单日期，格式:yyyy-MM-dd

        String vendorcode = args[8];// 供应商编码，可以通过 api/vendor 获取
        String vendorname = args[9];// 供应商名称关键字，可以通过 api/vendor 获取
        String personcode = args[10];// 业务员编码，可以通过 api/person 获取
        String personname = args[11];// 业务员名称关键字，可以通过 api/person 获取
        String deptcode = args[12];// 部门编码，可以通过 api/department 获取
        String deptname = args[13];// 部门名称关键字，可以通过 api/department 获取
        String remark = args[14];// 备注关键字
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("to_account", to_account);
        paramMap.put("page_index", page_index);
        paramMap.put("rows_per_page", rows_per_page);
        paramMap.put("code_begin", code_begin);
        paramMap.put("code_end", code_end);
        paramMap.put("date_begin", date_begin);
        paramMap.put("date_end", date_end);
        paramMap.put("state", state);
        paramMap.put("vendorcode", vendorcode);
        paramMap.put("vendorname", vendorname);
        paramMap.put("personcode", personcode);
        paramMap.put("personname", personname);
        paramMap.put("deptcode", deptcode);
        paramMap.put("deptname", deptname);
        paramMap.put("remark", remark);
        PurchaseOrderService purchaseOrderService = new PurchaseOrderService();
        try {
            JSONObject record = purchaseOrderService.batchGet(paramMap);
            logger.info(record.toString());
        } catch (OpenAPIException e) {
            e.printStackTrace();
        }
    }

}
