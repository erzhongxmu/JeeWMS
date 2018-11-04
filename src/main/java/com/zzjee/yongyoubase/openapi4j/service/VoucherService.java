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
 * Copyright(c) 2015-2015 by yonyouup. All Rights Reserved 凭证
 *
 * @author yanwuyang
 * @version <类版本> , 2015年12月8日
 * @see <相关类/方法>
 * @since <产品/模块版本>
 */
@SuppressWarnings("all")
public class VoucherService extends BaseService {

    private static final long serialVersionUID = 9094957269886633171L;

    final static Logger logger = LogManager.getLogger(VoucherService.class);

    public VoucherService() {
        this.access_token = TokenManager.getToKenId();
    }

    public VoucherService(String token) {
        this.access_token = token;
    }

    public JSONObject add(String jsonBody, String to_account) throws OpenAPIException {
        JSONObject record;
        try {
            String tradeId = TradeService.getTradeId();
            Map<String, String> paramMap = new HashMap();
            paramMap.put("to_account", to_account);
            paramMap.put("tradeid", tradeId);
            String url = this.createURL("voucher/add", paramMap);
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

    public JSONObject addByBizId(String bizId, String jsonBody, String to_account) throws OpenAPIException {
        JSONObject record;
        try {
            Map<String, String> paramMap = new HashMap();
            paramMap.put("to_account", to_account);
            paramMap.put("biz_id", bizId);
            String url = this.createURL("voucher/add", paramMap);
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
