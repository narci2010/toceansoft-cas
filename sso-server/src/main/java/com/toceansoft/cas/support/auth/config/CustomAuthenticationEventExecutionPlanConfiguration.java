/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.support.auth.config;

import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

import com.toceansoft.cas.support.auth.handler.UsernamePasswordSystemAuthenticationHandler;

/**
 * @author Narci.Lee
 * @date 2018/10/22
 * @since 1.6.0
 */
// @Configuration("customAuthenticationEventExecutionPlanConfiguration")
// @EnableConfigurationProperties(CasConfigurationProperties.class)
public class CustomAuthenticationEventExecutionPlanConfiguration
		implements AuthenticationEventExecutionPlanConfigurer {
	@Autowired
	@Qualifier("servicesManager")
	private ServicesManager servicesManager;

	@Autowired
	@Qualifier("jdbcPrincipalFactory")
	public PrincipalFactory jdbcPrincipalFactory;

	/**
	 * 注册验证器 放到 shiro(order=10) 验证器的前面 先验证验证码
	 *
	 * @return
	 */
	@Bean
	public AuthenticationHandler customAuthenticationHandler() {
		// 优先验证
		UsernamePasswordSystemAuthenticationHandler upsah = new UsernamePasswordSystemAuthenticationHandler(
				"customAuthenticationHandler", servicesManager, jdbcPrincipalFactory, 9);

		return upsah;
	}

	// 注册自定义认证器
	@Override
	public void configureAuthenticationExecutionPlan(final AuthenticationEventExecutionPlan plan) {
		plan.registerAuthenticationHandler(customAuthenticationHandler());
	}
}
