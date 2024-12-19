package com.base.handler;

import lombok.extern.slf4j.Slf4j;
import com.base.common.base.BaseMap;
import com.base.common.modules.redis.listener.BaseRedisListerer;
import com.base.loader.DynamicRouteLoader;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 路由刷新监听
 */
@Slf4j
@Component
public class LoderRouderHandler implements BaseRedisListerer {

    @Resource
    private DynamicRouteLoader dynamicRouteLoader;


    @Override
    public void onMessage(BaseMap message) {
        dynamicRouteLoader.refresh();
    }

}