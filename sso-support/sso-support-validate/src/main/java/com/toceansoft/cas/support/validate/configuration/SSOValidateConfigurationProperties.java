/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.support.validate.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.io.Serializable;

/**
 * @author Narci.Lee
 * @date 2018/11/2
 * @since
 */
@ConfigurationProperties(value = "sso.validate")
public class SSOValidateConfigurationProperties implements Serializable {
    public static final String PREFIX = "sso.validate";

    @NestedConfigurationProperty
    private MailProperties mail = new MailProperties();

    public MailProperties getMail() {
        return mail;
    }

    public void setMail(MailProperties mail) {
        this.mail = mail;
    }
}
