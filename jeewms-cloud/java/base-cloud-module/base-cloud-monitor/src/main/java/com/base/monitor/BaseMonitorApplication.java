package com.base.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 监控服务
 */
@SpringBootApplication
@EnableAdminServer
public class BaseMonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseMonitorApplication.class);
    }
}