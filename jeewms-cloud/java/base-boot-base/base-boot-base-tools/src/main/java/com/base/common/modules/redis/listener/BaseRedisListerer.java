package com.base.common.modules.redis.listener;

import com.base.common.base.BaseMap;

/**
 * 自定义消息监听
 */
public interface BaseRedisListerer {

    void onMessage(BaseMap message);

}
