package com.base.modules.cloud.feign.feign;

import com.base.common.api.vo.Result;
import com.base.modules.cloud.constant.CloudConstant;
import com.base.modules.cloud.feign.feign.fallback.BaseTestClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 常规feign接口定义
 */
@FeignClient(value = CloudConstant.SERVER_NAME_BASEDEMO, fallbackFactory = BaseTestClientFallback.class)
@Component
public interface BaseTestClient {

    @GetMapping(value = "/test/getMessage")
    Result<String> getMessage(@RequestParam("name") String name);
}
