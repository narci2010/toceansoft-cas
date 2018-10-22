/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.shiro.client.demo.core;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户端存储器
 *
 * @author Narci.Lee
 * @date 2017/10/8
 * @since 1.0.0
 */
public class ClientStrategyFactory {
    private Map<String , ClientStrategy> clientStrategy = new HashMap<>();

    public ClientStrategyFactory(Map<String, ClientStrategy> clientStrategy) {
        this.clientStrategy = clientStrategy;
    }

    public Map<String, ClientStrategy> getClientStrategy() {
        return clientStrategy;
    }
}
