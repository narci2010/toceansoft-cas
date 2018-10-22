/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class SsoConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoConfigApplication.class, args);
    }
}
