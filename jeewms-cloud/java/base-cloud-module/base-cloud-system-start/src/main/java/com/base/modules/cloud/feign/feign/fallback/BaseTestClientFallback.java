package com.base.modules.cloud.feign.feign.fallback;

import com.base.modules.cloud.feign.feign.BaseTestClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author qinfeng
 */
@Component
public class BaseTestClientFallback implements FallbackFactory<BaseTestClient> {

    @Override
    public BaseTestClient create(Throwable throwable) {
        return null;
    }
}
