//package com.base.modules.cloud.rabbitmq;
//
//import com.rabbitmq.client.Channel;
//import lombok.extern.slf4j.Slf4j;
//import com.base.boot.starter.rabbitmq.core.BaseRabbiMqHandler;
//import com.base.boot.starter.rabbitmq.listenter.MqListener;
//import com.base.common.annotation.RabbitComponent;
//import com.base.common.base.BaseMap;
//import com.base.modules.cloud.constant.CloudConstant;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.support.AmqpHeaders;
//import org.springframework.messaging.handler.annotation.Header;
//
//@Slf4j
//@RabbitListener(queues = CloudConstant.MQ_BASE_PLACE_ORDER_TIME)
//@RabbitComponent(value = "helloTimeReceiver")
//public class HelloTimeReceiver extends BaseRabbiMqHandler<BaseMap> {
//
//    @RabbitHandler
//    public void onMessage(BaseMap baseMap, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
//        super.onMessage(baseMap, deliveryTag, channel, new MqListener<BaseMap>() {
//            @Override
//            public void handler(BaseMap map, Channel channel) {
//                //业务处理
//                String orderId = map.get("orderId").toString();
//                log.info("Time Receiver1，orderId : " + orderId);
//            }
//        });
//    }
//
//}