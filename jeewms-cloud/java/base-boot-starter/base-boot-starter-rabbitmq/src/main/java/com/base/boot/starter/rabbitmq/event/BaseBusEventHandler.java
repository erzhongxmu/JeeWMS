package com.base.boot.starter.rabbitmq.event;

/**
 * 业务模块消息处理器接口
 */
public interface BaseBusEventHandler {
    void onMessage(EventObj map);
}
