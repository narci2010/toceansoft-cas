/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.shiro.client.demo.confg.pros;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Narci.Lee
 * @date 2018/10/8
 * @since
 */
@Component
@ConfigurationProperties(prefix="github") //接收application.yml中的github下面的属性
public class GithubProperties {

    private List<String> bindId = new ArrayList<>();

    public List<String> getBindId() {
        return bindId;
    }

    public GithubProperties setBindId(List<String> bindId) {
        this.bindId = bindId;
        return this;
    }
}
