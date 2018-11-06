/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.support.auth;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.flow.CasWebflowConstants;
import org.apereo.cas.web.flow.configurer.AbstractCasWebflowConfigurer;
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
 * @date 2018/10/23
 * @since 1.6.0
 */
public class CustomWebflowConfigurer extends AbstractCasWebflowConfigurer {
	// 5.1.6 在org.apereo.cas.web.flow.AbstractCasWebflowConfigurer
	// 5.3.3 在org.apereo.cas.web.flow.configurer.AbstractCasWebflowConfigurer
	public CustomWebflowConfigurer(FlowBuilderServices flowBuilderServices,
			FlowDefinitionRegistry flowDefinitionRegistry, ApplicationContext applicationContext,
			CasConfigurationProperties casProperties) {
		// 5.1.6两个参数super(flowBuilderServices, flowDefinitionRegistry);
		// 5.3.3四个参数
		super(flowBuilderServices, flowDefinitionRegistry, applicationContext, casProperties);
	}

	@Override
	protected void doInitialize() {
		// 5.1.6 throws Exception
		// 5.3.3 none
		final Flow flow = getLoginFlow();
		bindCredential(flow);
	}

	/**
	 * 绑定输入信息
	 *
	 * @param flow
	 */
	protected void bindCredential(Flow flow) {
		// 重写绑定自定义credential
		createFlowVariable(flow, CasWebflowConstants.VAR_ID_CREDENTIAL,
				UsernamePasswordSysCredential.class);
		// 登录页绑定新参数
		final ViewState state = (ViewState) flow
				.getState(CasWebflowConstants.STATE_ID_VIEW_LOGIN_FORM);
		final BinderConfiguration cfg = getViewStateBinderConfiguration(state);
		// 由于用户名以及密码已经绑定，所以只需对新加系统参数绑定即可
		cfg.addBinding(new BinderConfiguration.Binding("system", null, false));
	}
}
