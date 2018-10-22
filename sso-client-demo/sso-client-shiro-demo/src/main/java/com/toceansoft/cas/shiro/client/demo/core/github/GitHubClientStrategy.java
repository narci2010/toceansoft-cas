/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.shiro.client.demo.core.github;

import com.toceansoft.cas.shiro.client.demo.core.ClientStrategy;
import com.toceansoft.cas.shiro.client.demo.core.PrincipalBindResolver;
import com.toceansoft.cas.shiro.client.demo.exception.NotBindException;

import io.buji.pac4j.subject.Pac4jPrincipal;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * github处理策略
 *
 * @author Narci.Lee
 * @date 2017/10/8
 * @since 1.0.0
 */
public class GitHubClientStrategy implements ClientStrategy {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //用户绑定决定
    private PrincipalBindResolver resolver;

    public GitHubClientStrategy(PrincipalBindResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public String name() {
        return "github";
    }

    @Override
    public void handle(JoinPoint joinPoint, Pac4jPrincipal pac4jPrincipal) throws Exception {
        //todo 未登录转发页面处理
        logger.debug("GitHub用户未绑定");
        //抛出异常进行吹
        throw new NotBindException().setClientName(name()).setRedirectUrl("bind/github").setPrincipal(pac4jPrincipal);
    }

    @Override
    public boolean isBind(Pac4jPrincipal principal) {
        return resolver.isBind(principal.getProfile().getId());
    }
}
