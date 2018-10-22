/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.support.auth;

import org.apereo.cas.authentication.UsernamePasswordCredential;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.flow.CasWebflowConstants;
import org.apereo.cas.web.flow.configurer.DefaultLoginWebflowConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.ViewState;
import org.springframework.webflow.engine.builder.BinderConfiguration;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

/**
 * 重新定义默认的web流程
 *
 * @author Narci.Lee
 * @date 2018/10/22
 * @since 1.6.0
 */
public class CustomWebflowConfigurer extends DefaultLoginWebflowConfigurer {

	public CustomWebflowConfigurer(FlowBuilderServices flowBuilderServices,
			FlowDefinitionRegistry flowDefinitionRegistry, ApplicationContext applicationContext,
			CasConfigurationProperties casProperties) {
		super(flowBuilderServices, flowDefinitionRegistry, applicationContext, casProperties);
	}

	@Override
	protected void createRememberMeAuthnWebflowConfig(Flow flow) {
		if (this.casProperties.getTicket().getTgt().getRememberMe().isEnabled()) {
			this.createFlowVariable(flow, "credential", UsernamePasswordSysCredential.class);
			ViewState state = (ViewState) this.getState(flow, "viewLoginForm", ViewState.class);
			BinderConfiguration cfg = this.getViewStateBinderConfiguration(state);
			cfg.addBinding(new BinderConfiguration.Binding("system", null, false));
			// cfg.addBinding(new BinderConfiguration.Binding("capcha", (String) null,
			// true));

		} else {
			// this.createFlowVariable(flow, "credential",
			// RememberMeUsernamePasswordCredential.class);
			// ViewState state = (ViewState) this.getState(flow, "viewLoginForm",
			// ViewState.class);
			// BinderConfiguration cfg = this.getViewStateBinderConfiguration(state);
			// cfg.addBinding(new BinderConfiguration.Binding("rememberMe", (String) null,
			// false));
			//
			createFlowVariable(flow, CasWebflowConstants.VAR_ID_CREDENTIAL,
					UsernamePasswordCredential.class);
		}

	}

}
