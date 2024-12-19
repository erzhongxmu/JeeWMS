package com.base.modules.demo.cloud.service;

import com.base.common.api.vo.Result;

public interface JcloudDemoService {
    Result<String> getMessage(String name);
}
