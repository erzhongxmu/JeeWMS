package com.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"org.jeecg","com.base"})
@EnableFeignClients(basePackages = {"org.jeecg","com.base"})
public class BaseDemoCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseDemoCloudApplication.class, args);
    }
}
