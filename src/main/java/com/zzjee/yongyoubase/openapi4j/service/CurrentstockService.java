package com.zzjee.yongyoubase.openapi4j.service;

import com.alibaba.fastjson.JSONObject;
import com.zzjee.yongyoubase.openapi4j.commons.TokenManager;
import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.model.Record;
import com.zzjee.yongyoubase.openapi4j.platform.TradeService;
import com.zzjee.yongyoubase.openapi4j.util.HttpUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Copyright(c) 2015-2015 by yonyouup. All Rights Reserved 现存量查询
 * 
 * @author yanwuyang
 * @version <类版本> , 2015年12月8日
 * @see <相关类/方法>
 * @since <产品/模块版本>
 */
@SuppressWarnings("all")
public class CurrentstockService extends BaseService {

    private static final long serialVersionUID = 8697005112465513332L;

    final static Logger logger = LogManager.getLogger(CurrentstockService.class);

    public CurrentstockService() {
        this.access_token = TokenManager.getToKenId();
    }

    public CurrentstockService(String token) {
        this.access_token = token;
    }

    /**
     * 获取现存量列表信息
     * 
     * @param paramMap  参数
     * @return
     * @throws OpenAPIException
     */
    public JSONObject batchGet(Map<String, String> paramMap) throws OpenAPIException {
        JSONObject record;
        try {
            String url = this.createURL("currentstock/batch_get", paramMap);
            logger.debug(url);
            record = JSONObject.parseObject(HttpUtil.get(url));
        } catch (Exception e) {
            throw new OpenAPIException(e.getMessage(), e);
        }
        return record;
    }

}
