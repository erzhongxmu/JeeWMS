package com.base.modules.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.base.common.util.RedisUtil;
import com.base.modules.jeeerp.entity.ConfCode;
import com.base.modules.jeeerp.service.impl.ConfCodeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * redis 工具类
 * @Author base-boot
 *
 */
@Component
@Slf4j
public class WmsRedisUtil {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ConfCodeServiceImpl codeService;

    public Object queryConfTableType(String confType, String key1, String key2, String key3, String key4, String key5, String key6, String key7, String key8, String key9, String key10){
        StringBuilder sb = new StringBuilder("prefix_redis_conf_");
        sb.append(confType).append("_").append(key1).append("_").append(key2);
        Object obj = redisUtil.get(sb.toString());
        if (obj != null){
            return obj;
        }
        QueryWrapper<ConfCode> qw = new QueryWrapper<>();
        qw.eq("table_name",key1);
        qw.eq("code_type",key2);
        obj = codeService.getOne(qw, false);
        redisUtil.set(sb.toString(), obj);
        log.info("存入缓存：{}",sb);
        return obj;
    }
}
