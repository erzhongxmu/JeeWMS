package com.base.modules.cloud.feign.controller;


import cn.hutool.core.util.RandomUtil;
import com.base.modules.cloud.constant.CloudConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.base.common.api.vo.Result;
import com.base.common.base.BaseMap;
import com.base.modules.cloud.feign.feign.BaseTestClient;
import com.base.modules.cloud.feign.feign.BaseTestClientDyn;
import com.base.starter.cloud.feign.impl.BaseFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sys/test")
@Api(tags = "【微服务】单元测试")
public class BaseTestFeignController {

    @Autowired
    private BaseFeignService baseFeignService;
    @Autowired
    private BaseTestClient baseTestClient;
    /*@Autowired
    private RabbitMqClient rabbitMqClient;*/

    @GetMapping("getMessage")
    @ApiOperation(value = "测试feign", notes = "测试feign")
    public Result<String> getMessage() {
        return baseTestClient.getMessage("base-boot");
    }

    @GetMapping("getMessage2")
    @ApiOperation(value = "测试动态feign", notes = "测试动态feign")
    public Result<String> getMessage2() {
        BaseTestClientDyn myClientDyn = baseFeignService.newInstance(BaseTestClientDyn.class, CloudConstant.SERVER_NAME_BASEDEMO);
        return myClientDyn.getMessage("动态fegin——base-boot2");
    }

   /* @GetMapping(value = "/rabbitmq")
    @ApiOperation(value = "测试rabbitmq", notes = "测试rabbitmq")
    public Result<?> rabbitMqClientTest(HttpServletRequest req) {
        //rabbitmq消息队列测试
        BaseMap map = new BaseMap();
        map.put("orderId", RandomUtil.randomNumbers(10));
        rabbitMqClient.sendMessage(CloudConstant.MQ_BASE_PLACE_ORDER, map);
        rabbitMqClient.sendMessage(CloudConstant.MQ_BASE_PLACE_ORDER_TIME, map,10);

        //rabbitmq消息总线测试
        BaseMap params = new BaseMap();
        params.put("orderId", "123456");
        rabbitMqClient.publishEvent(CloudConstant.MQ_DEMO_BUS_EVENT, params);
        return Result.OK("MQ发送消息成功");
    }*/
}
