/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.support.validate.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.toceansoft.cas.support.validate.configuration.SSOValidateConfigurationProperties;

/**
 * @author Narci.Lee
 * @date 2017/11/2
 * @since
 */
@Configuration("ssoValidateConfiguration")
@EnableConfigurationProperties(SSOValidateConfigurationProperties.class)
public class SSOValidateConfiguration {
    @Autowired
    private SSOValidateConfigurationProperties properties;
}
