/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.support.validate.config;

import com.toceansoft.cas.support.validate.configuration.SSOValidateConfigurationProperties;
import com.toceansoft.cas.support.validate.core.DefaultValidateService;
import com.toceansoft.cas.support.validate.imp.mail.MailInformativeGenerator;
import com.toceansoft.cas.support.validate.imp.mail.MailMemoryStore;
import com.toceansoft.cas.support.validate.imp.mail.MailSender;
import com.toceansoft.cas.support.validate.imp.mail.MailValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * 邮箱校验服务，当配置<code>sso.validate.mail.enable=true</code>
 * 生效
 *
 * @author Narci.Lee
 * @date 2018/11/2
 * @since
 */
@Configuration("ssoMailValidateConfiguration")
@EnableConfigurationProperties(SSOValidateConfigurationProperties.class)
@ConditionalOnProperty(name = "sso.validate.mail.enable", havingValue = "true")
public class SSOMailValidateConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(SSOMailValidateConfiguration.class);
    @Autowired
    private SSOValidateConfigurationProperties properties;

    @Autowired
    private JavaMailSender mailSender;

    @Bean
    @ConditionalOnMissingBean(name = "mailInformativeGenerator")
    @RefreshScope
    public MailInformativeGenerator mailInformativeGenerator() {
        return new MailInformativeGenerator(properties.getMail());
    }

    @Bean
    @ConditionalOnMissingBean(name = "mailStore")
    @RefreshScope
    public MailMemoryStore mailStore() {
        LOGGER.warn("验证码内存存储，应考虑放于数据库或缓存设备");
        return new MailMemoryStore();
    }

    @Bean
    @RefreshScope
    @ConditionalOnMissingBean(name = "validateMailSender")
    public MailSender validateMailSender() {
        LOGGER.info("邮件发送验证码");
        return new MailSender(mailSender);
    }

    @Bean
    @ConditionalOnMissingBean(name = "mailValidator")
    public MailValidator mailValidator() {
        return new MailValidator(mailStore());
    }

    @Bean
    @ConditionalOnMissingBean(name = "defaultValidateService")
    @RefreshScope
    public DefaultValidateService defaultValidateService() {
        //默认的邮箱校验服务
        DefaultValidateService service = new DefaultValidateService(
                mailInformativeGenerator(),
                mailStore(),
                validateMailSender(),
                mailValidator());
        return service;
    }
}
