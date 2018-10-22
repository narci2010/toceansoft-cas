package com.toceansoft.cas.custom;

import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.ServicesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("rememberMeConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class RememberMeConfiguration implements AuthenticationEventExecutionPlanConfigurer {

	@Autowired
	private CasConfigurationProperties casProperties;

	@Autowired
	@Qualifier("servicesManager")
	private ServicesManager servicesManager;

	/**
	 * 放到 shiro(order=10) 验证器的前面 先验证验证码
	 * 
	 * @return
	 */
	@Bean
	public AuthenticationHandler rememberMeUsernamePasswordCaptchaAuthenticationHandler() {
		RememberMeUsernamePasswordCaptchaAuthenticationHandler handler = new RememberMeUsernamePasswordCaptchaAuthenticationHandler(
				RememberMeUsernamePasswordCaptchaAuthenticationHandler.class.getSimpleName(),
				servicesManager, new DefaultPrincipalFactory(), 9);

		return handler;
	}

	@Override
	public void configureAuthenticationExecutionPlan(AuthenticationEventExecutionPlan plan) {
		plan.registerAuthenticationHandler(
				rememberMeUsernamePasswordCaptchaAuthenticationHandler());
	}
}
