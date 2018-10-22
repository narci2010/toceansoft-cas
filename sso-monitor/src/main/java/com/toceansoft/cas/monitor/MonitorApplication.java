/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.monitor;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * 认证中心监控平台，监控各服务运行情况
 * @author Narci.Lee
 */
@Configuration
@EnableAutoConfiguration
@EnableAdminServer
public class MonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(MonitorApplication.class, args);
    }
}
