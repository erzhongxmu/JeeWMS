package com.base.config.oss;

import com.base.common.util.oss.OssBootUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 云存储 配置
 */
@Configuration
public class OssConfiguration {

    @Value("${base.oss.endpoint}")
    private String endpoint;
    @Value("${base.oss.accessKey}")
    private String accessKeyId;
    @Value("${base.oss.secretKey}")
    private String accessKeySecret;
    @Value("${base.oss.bucketName}")
    private String bucketName;
    @Value("${base.oss.staticDomain:}")
    private String staticDomain;


    @Bean
    public void initOssBootConfiguration() {
        OssBootUtil.setEndPoint(endpoint);
        OssBootUtil.setAccessKeyId(accessKeyId);
        OssBootUtil.setAccessKeySecret(accessKeySecret);
        OssBootUtil.setBucketName(bucketName);
        OssBootUtil.setStaticDomain(staticDomain);
    }
}