/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.shiro.client.demo.confg;

import com.toceansoft.cas.shiro.client.demo.confg.pros.GithubProperties;
import com.toceansoft.cas.shiro.client.demo.core.ClientStrategy;
import com.toceansoft.cas.shiro.client.demo.core.ClientStrategyFactory;
import com.toceansoft.cas.shiro.client.demo.core.PrincipalBindResolver;
import com.toceansoft.cas.shiro.client.demo.core.github.GitHubClientStrategy;
import com.toceansoft.cas.shiro.client.demo.core.github.GitHubMemoryPrincipalBindResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Narci.Lee
 * @date 2018/10/8
 * @since 1.0.0
 */
@Configuration
@Profile("dev")
public class ClientConfiguration {
    @Autowired
    private GithubProperties properties;

    @Bean
    protected ClientStrategyFactory clientStrategyFactory() {
        Map<String, ClientStrategy> clientStrategyMap = new HashMap<>();
        GitHubClientStrategy clientStrategy = new GitHubClientStrategy(bindResolver());
        clientStrategyMap.put(clientStrategy.name(), clientStrategy);
        return new ClientStrategyFactory(clientStrategyMap);
    }

    /**
     * 绑定用户取决器
     * @return
     */
    @Bean
    protected PrincipalBindResolver bindResolver() {
        GitHubMemoryPrincipalBindResolver gitHubBinder = new GitHubMemoryPrincipalBindResolver(properties.getBindId());
        return gitHubBinder;
    }
}
