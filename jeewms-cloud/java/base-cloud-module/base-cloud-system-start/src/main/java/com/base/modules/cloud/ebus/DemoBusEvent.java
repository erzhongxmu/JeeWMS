//package com.base.modules.cloud.ebus;
//
//import cn.hutool.core.util.ObjectUtil;
//import lombok.extern.slf4j.Slf4j;
//import com.base.common.base.BaseMap;
//
///**
// * 消息处理器【发布订阅】
// */
//@Slf4j
////@Component(CloudConstant.MQ_DEMO_BUS_EVENT)
//public class DemoBusEvent implements BaseBusEventHandler {
//
//
//    @Override
//    public void onMessage(EventObj obj) {
//        if (ObjectUtil.isNotEmpty(obj)) {
//            BaseMap baseMap = obj.getBaseMap();
//            String orderId = baseMap.get("orderId");
//            log.info("业务处理----订单ID:" + orderId);
//        }
//    }
//}
