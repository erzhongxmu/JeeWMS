package com.base.starter.cloud.feign;

public interface IBaseFeignService {

    <T> T newInstance(Class<T> apiType, String name);
}
