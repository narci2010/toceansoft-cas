/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.shiro.client.demo.confg;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.AbstractShiroWebFilterConfiguration;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.client.rest.CasRestFormClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.config.CasProtocol;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.matching.PathMatcher;
import org.pac4j.http.client.direct.ParameterClient;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.jwt.profile.JwtGenerator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import io.buji.pac4j.filter.CallbackFilter;
import io.buji.pac4j.filter.LogoutFilter;
import io.buji.pac4j.filter.SecurityFilter;
import io.buji.pac4j.subject.Pac4jSubjectFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * 对shiro的安全配置，是对cas的登录策略进行配置
 *
 * @author Narci.Lee
 * @date 2017/9/16
 * @since 1.0.0
 */
@Configuration
@Slf4j
public class ShiroConfiguration extends AbstractShiroWebFilterConfiguration {
	@Value("#{ @environment['cas.prefixUrl'] ?: null }")
	private String prefixUrl;
	@Value("#{ @environment['cas.loginUrl'] ?: null }")
	private String casLoginUrl;
	@Value("#{ @environment['cas.callbackUrl'] ?: null }")
	private String callbackUrl;
	@Value("#{ @environment['cas.serviceUrl'] ?: null }")
	private String serviceUrl;

	// jwt秘钥 密钥
	@Value("${jwt.salt}")
	private String salt;
	@Value("${jwt.algorithm}")
	private String algorithm;

	/**
	 * cas核心过滤器，把支持的client写上，filter过滤时才会处理，clients必须在casConfig.clients已经注册
	 *
	 * @return
	 */
	@Bean
	public Filter casSecurityFilter() {
		SecurityFilter filter = new SecurityFilter();
		filter.setClients("cas,rest,jwt");
		filter.setConfig(casConfig());

		return filter;
	}

	/**
	 * 通过rest接口可以获取tgt，获取service ticket，甚至可以获取CasProfile
	 * 
	 * @return
	 */
	@Bean
	protected CasRestFormClient casRestFormClient() {
		CasRestFormClient casRestFormClient = new CasRestFormClient();
		casRestFormClient.setConfiguration(casConfiguration());
		casRestFormClient.setName("rest");

		return casRestFormClient;
	}

	@Bean
	protected Clients clients() {
		// // 可以设置默认client
		// Clients clients = new Clients();
		// // 支持的client全部设置进去
		// clients.setClients(casClient(), casRestFormClient());
		// return clients;
		// 可以设置默认client
		Clients clients = new Clients();

		// token校验器，可以用HeaderClient更安全
		ParameterClient parameterClient = new ParameterClient("token", jwtAuthenticator());
		parameterClient.setSupportGetRequest(true);
		parameterClient.setName("jwt");
		// 支持的client全部设置进去
		clients.setClients(casClient(), casRestFormClient(), parameterClient);
		return clients;
	}

	@Bean
	// pac4j clients config
	protected Config casConfig() {
		Config config = new Config();
		config.setClients(clients());

		config.addMatcher("excludedPath", pathMatcher());
		return config;
	}

	/**
	 * cas的基本设置，包括或url等等，rest调用协议等
	 * 
	 * @return
	 */
	@Bean
	public CasConfiguration casConfiguration() {
		// https://localhost:8443/cas/login
		CasConfiguration casConfiguration = new CasConfiguration(casLoginUrl);
		casConfiguration.setProtocol(CasProtocol.CAS30);
		// https: // localhost:8443/cas
		casConfiguration.setPrefixUrl(prefixUrl);

		return casConfiguration;
	}

	@Bean
	public CasClient casClient() {
		CasClient casClient = new CasClient();
		casClient.setConfiguration(casConfiguration());
		// http://localhost:8083/callback
		casClient.setCallbackUrl(callbackUrl);
		casClient.setName("cas");

		return casClient;
	}

	/**
	 * 路径过滤设置
	 *
	 * @return
	 */
	@Bean
	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
		DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
		definition.addPathDefinition("/callback", "callbackFilter");
		definition.addPathDefinition("/logout", "logoutFilter");
		definition.addPathDefinition("/user/you", "anon");
		definition.addPathDefinition("/**", "casSecurityFilter");

		return definition;
	}

	/**
	 * 由于cas代理了用户，所以必须通过cas进行创建对象
	 *
	 * @return
	 */
	// @Bean(name = "subjectFactory")
	// protected SubjectFactory subjectFactory() {
	// return new Pac4jSubjectFactory();
	// }

	/**
	 * 对过滤器进行调整
	 *
	 * @return
	 */
	@Bean
	protected ShiroFilterFactoryBean shiroFilterFactoryBean(Realm pac4jRealm,
			SubjectFactory subjectFactory) {
		// 把subject对象设为subjectFactory
		// 由于cas代理了用户，所以必须通过cas进行创建对象
		((DefaultSecurityManager) securityManager).setSubjectFactory(new Pac4jSubjectFactory());
		// LOGGER.debug("enter securityManager");
		// DefaultSecurityManager defaultWebSecurityManager = (DefaultSecurityManager)
		// this.securityManager;
		// defaultWebSecurityManager.setRealm(pac4jRealm);
		// defaultWebSecurityManager.setSubjectFactory(subjectFactory);

		ShiroFilterFactoryBean filterFactoryBean = super.shiroFilterFactoryBean();
		// filterFactoryBean.setSecurityManager(securityManager);

		filterFactoryBean.setFilters(shiroFilters());
		filterFactoryBean
				.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());
		return filterFactoryBean;
	}

	/**
	 * 对shiro的过滤策略进行明确
	 * 
	 * @return
	 */
	@Bean
	protected Map<String, Filter> shiroFilters() {
		// 过滤器设置
		Map<String, Filter> filters = new HashMap<>();
		filters.put("casSecurityFilter", casSecurityFilter());
		CallbackFilter callbackFilter = new CallbackFilter();
		callbackFilter.setConfig(casConfig());
		filters.put("callbackFilter", callbackFilter);

		LogoutFilter logoutFilter = new LogoutFilter();
		logoutFilter.setConfig(casConfig());
		logoutFilter.setCentralLogout(true);
		logoutFilter.setDefaultUrl(serviceUrl);
		filters.put("logoutFilter", logoutFilter);

		return filters;
	}

	/**
	 * JWT Token 生成器，对CommonProfile生成然后每次携带token访问
	 * 
	 * @return
	 */
	@Bean
	protected JwtGenerator jwtGenerator() {
		SecretSignatureConfiguration ssc = new SecretSignatureConfiguration(salt);
		// ssc.setAlgorithm(JWSAlgorithm.parse(algorithm));
		SecretEncryptionConfiguration sec = new SecretEncryptionConfiguration(salt);
		// sec.setAlgorithm(JWEAlgorithm.parse(algorithm));
		return new JwtGenerator(ssc, sec);
	}

	/**
	 * JWT校验器，也就是目前设置的ParameterClient进行的校验器，是rest/或者前后端分离的核心校验器
	 * 
	 * @return
	 */
	@Bean
	protected JwtAuthenticator jwtAuthenticator() {
		JwtAuthenticator jwtAuthenticator = new JwtAuthenticator();
		SecretSignatureConfiguration ssc = new SecretSignatureConfiguration(salt);
		// ssc.setAlgorithm(JWSAlgorithm.parse(algorithm));
		jwtAuthenticator.addSignatureConfiguration(ssc);
		SecretEncryptionConfiguration sec = new SecretEncryptionConfiguration(salt);
		// sec.setAlgorithm(JWEAlgorithm.parse(algorithm));
		jwtAuthenticator.addEncryptionConfiguration(sec);
		return jwtAuthenticator;
	}

	/**
	 * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
	 * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
	 *
	 * @return
	 */
	@Bean
	@DependsOn({ "lifecycleBeanPostProcessor" })
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}

	/**
	 * 开启 shiro aop注解支持
	 *
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
		aasa.setSecurityManager(securityManager);
		return aasa;
	}

	/**
	 * 单点登出的listener(pac4j+shiro不需要)
	 *
	 * @return
	 */
	// @SuppressWarnings({ "rawtypes", "unchecked" })
	// @Bean
	// public ServletListenerRegistrationBean<?> singleSignOutHttpSessionListener()
	// {
	// ServletListenerRegistrationBean bean = new ServletListenerRegistrationBean();
	// bean.setListener(new SingleSignOutHttpSessionListener());
	// bean.setEnabled(true);
	// return bean;
	// }

	/**
	 * 单点登出filter(pac4j+shiro不需要)
	 *
	 * @return
	 */
	// @Bean
	// @Order(Ordered.HIGHEST_PRECEDENCE)
	// public FilterRegistrationBean singleSignOutFilter() {
	// FilterRegistrationBean bean = new FilterRegistrationBean();
	// bean.setName("singleSignOutFilter");
	// SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
	// singleSignOutFilter.setCasServerUrlPrefix(prefixUrl);
	// singleSignOutFilter.setIgnoreInitConfiguration(true);
	// bean.setFilter(singleSignOutFilter);
	// bean.addUrlPatterns("/*");
	// bean.setEnabled(true);
	// return bean;
	// }

	/**
	 * 不拦截的路径
	 *
	 * @return
	 */
	@Bean
	public PathMatcher pathMatcher() {
		PathMatcher pathMatcher = new PathMatcher();
		pathMatcher.excludePath("/html/**");

		return pathMatcher;
	}

}
