/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.shiro.client.demo.confg;

import org.apache.shiro.realm.Realm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.toceansoft.cas.shiro.client.demo.realm.ShiroPac4jRealm;

/**
 * @author Narci.Lee
 * @date 2018/10/8
 * @since 1.0.0
 */
@Configuration
public class RealmConfiguration {
	@Bean
	public Realm pac4jRealm() {
		return new ShiroPac4jRealm();
	}
}
