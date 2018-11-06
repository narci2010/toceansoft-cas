/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.shiro.client.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Narci.Lee
 * @date 2018/10/8
 * @since 1.0.0
 */
@SpringBootApplication
@EnableAspectJAutoProxy
// Servlet 的listener filter标注需要这个才生效
@ServletComponentScan
public class ShiroClientApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShiroClientApplication.class, args);
	}
}
