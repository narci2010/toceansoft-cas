package com.toceansoft.cas.custom;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

//@Configuration("captchaWebflowConfiguration")
//@EnableConfigurationProperties(CasConfigurationProperties.class)
//@AutoConfigureBefore(value = CasWebflowContextConfiguration.class)
public class CaptchaWebflowConfiguration {

	@Autowired
	@Qualifier("loginFlowRegistry")
	private FlowDefinitionRegistry loginFlowRegistry;
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private CasConfigurationProperties casProperties;
	@Autowired
	private FlowBuilderServices flowBuilderServices;

	@Bean("defaultLoginWebflowConfigurer")
	public CasWebflowConfigurer defaultLoginWebflowConfigurer() {
		DefaultCaptchaWebflowConfigurer c = new DefaultCaptchaWebflowConfigurer(flowBuilderServices,
				loginFlowRegistry, applicationContext, casProperties);
		c.initialize();
		return c;
	}
}
