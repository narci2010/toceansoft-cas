/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.support.auth.config;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

import com.toceansoft.cas.support.auth.CustomWebflowConfigurer;

/**
 * @author Narci.Lee
 * @date 2018/10/22
 * @since 1.6.0
 */
// @Configuration("customerAuthWebflowConfiguration")
// @EnableConfigurationProperties(CasConfigurationProperties.class)
// @AutoConfigureBefore(value = CasWebflowContextConfiguration.class)
public class CustomerAuthWebflowConfiguration {
	@Autowired
	@Qualifier("logoutFlowRegistry")
	private FlowDefinitionRegistry logoutFlowRegistry;
	@Autowired
	@Qualifier("loginFlowRegistry")
	private FlowDefinitionRegistry loginFlowRegistry;

	@Autowired
	@Qualifier("builder")
	private FlowBuilderServices builder;

	@Autowired
	@Qualifier("applicationContext")
	private ApplicationContext applicationContext;

	@Autowired
	// @Qualifier("casProperties")
	private CasConfigurationProperties casProperties;

	@Bean("defaultLoginWebflowConfigurer")
	public CasWebflowConfigurer customWebflowConfigurer() {
		final CustomWebflowConfigurer c = new CustomWebflowConfigurer(builder, loginFlowRegistry,
				applicationContext, casProperties);
		c.setLogoutFlowDefinitionRegistry(logoutFlowRegistry);
		c.initialize();
		return c;
	}

}
