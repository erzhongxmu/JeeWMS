package com.base.common.modules.redis.receiver;


import cn.hutool.core.util.ObjectUtil;
import com.base.common.modules.redis.listener.BaseRedisListerer;
import lombok.Data;
import com.base.common.base.BaseMap;
import com.base.common.constant.GlobalConstants;
import com.base.common.util.SpringContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author zyf
 */
@Component
@Data
public class RedisReceiver {


    /**
     * 接受消息并调用业务逻辑处理器
     *
     * @param params
     */
    public void onMessage(BaseMap params) {
        Object handlerName = params.get(GlobalConstants.HANDLER_NAME);
        BaseRedisListerer messageListener = SpringContextHolder.getHandler(handlerName.toString(), BaseRedisListerer.class);
        if (ObjectUtil.isNotEmpty(messageListener)) {
            messageListener.onMessage(params);
        }
    }

}
