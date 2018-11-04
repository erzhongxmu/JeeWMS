package com.zzjee.yongyoubase.openapi4j.service;

import com.alibaba.fastjson.JSONObject;
import com.zzjee.yongyoubase.openapi4j.commons.TokenManager;
import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.model.Record;
import com.zzjee.yongyoubase.openapi4j.platform.TradeService;
import com.zzjee.yongyoubase.openapi4j.util.HttpUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright(c) 2015-2015 by yonyouup. All Rights Reserved 其它入库单
 * 
 * @author yanwuyang
 * @version <类版本> , 2015年12月8日
 * @see <相关类/方法>
 * @since <产品/模块版本>
 */
@SuppressWarnings("all")
public class OtherInService extends BaseService {

    private static final long serialVersionUID = 8697005112465513332L;

    final static Logger logger = LogManager.getLogger(OtherInService.class);

    public OtherInService() {
        this.access_token = TokenManager.getToKenId();
    }

    public OtherInService(String token) {
        this.access_token = token;
    }

    /**
     * 
     * 获取单张其它出库单 
     * @param id 订单编号
     * @param to_account 提供方id
     * @return
     * @throws OpenAPIException
     */
    public JSONObject get(String id, String to_account) throws OpenAPIException {
        JSONObject record;
        try {
            Map<String, String> paramMap = new HashMap();
            paramMap.put("to_account", to_account);
            paramMap.put("id", id);
            String url = this.createURL("otherin/get", paramMap);
            logger.debug(url);
            record = JSONObject.parseObject(HttpUtil.get(url));
        } catch (Exception e) {
            throw new OpenAPIException(e.getMessage(), e);
        }
        return record;
    }

    /**
     * 
     * 获取其它出库单列表信息
     * @param paramMap 参数
     * @return
     * @throws OpenAPIException
     */
    public JSONObject batchGet(Map<String, String> paramMap) throws OpenAPIException {
        JSONObject record;
        try {
            String url = this.createURL("otherinlist/batch_get", paramMap);
            logger.debug(url);
            record = JSONObject.parseObject(HttpUtil.get(url));
        } catch (Exception e) {
            throw new OpenAPIException(e.getMessage(), e);
        }
        return record;
    }

    /**
     * 
     * 新增一张其它出库单 
     * @param jsonBody 请求体
     * @param to_account 提供方id
     * @return
     * @throws OpenAPIException
     */
    public JSONObject add(String jsonBody, String to_account) throws OpenAPIException {
        JSONObject record;
        try {
            String tradeId = TradeService.getTradeId();
            Map<String, String> paramMap = new HashMap();
            paramMap.put("to_account", to_account);
            paramMap.put("tradeid", tradeId);
            String url = this.createURL("otherin/add", paramMap);
            logger.debug(url);
            String resultStr = HttpUtil.post(url, jsonBody);
            logger.debug(resultStr);
            JSONObject resultRecord = Record.parseObject(resultStr);
            Thread.sleep(3000);
            record = Record.parseObject(HttpUtil.get(resultRecord.getString("url")));
        } catch (Exception e) {
            throw new OpenAPIException(e.getMessage(), e);
        }
        return record;
    }

    /**
     * 
     * 新增一张其它出库单 
     * @param bizId 业务id
     * @param jsonBody 请求体
     * @param to_account 提供方id
     * @return
     * @throws OpenAPIException
     */
    public JSONObject addByBizId(String bizId, String jsonBody, String to_account) throws OpenAPIException {
        JSONObject record;
        try {
            Map<String, String> paramMap = new HashMap();
            paramMap.put("to_account", to_account);
            paramMap.put("biz_id", bizId);
            String url = this.createURL("otherin/add", paramMap);
            logger.debug(url);
            String resultStr = HttpUtil.post(url, jsonBody);
            logger.debug(resultStr);
            JSONObject resultRecord = Record.parseObject(resultStr);
            Thread.sleep(3000);
            record = Record.parseObject(HttpUtil.get(resultRecord.getString("url")));
        } catch (Exception e) {
            throw new OpenAPIException(e.getMessage(), e);
        }
        return record;
    }
}
