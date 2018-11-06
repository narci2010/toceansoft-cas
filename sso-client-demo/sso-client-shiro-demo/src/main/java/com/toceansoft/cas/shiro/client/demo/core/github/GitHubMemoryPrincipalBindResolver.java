/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.shiro.client.demo.core.github;

import io.buji.pac4j.subject.Pac4jPrincipal;

import java.util.List;

import com.toceansoft.cas.shiro.client.demo.core.PrincipalBindResolver;

/**
 * github内存id取决器
 *
 * @author Narci.Lee
 * @date 2018/10/8
 * @since 1.0.0
 */
public class GitHubMemoryPrincipalBindResolver implements PrincipalBindResolver {
    private List<String> idStorage;

    public GitHubMemoryPrincipalBindResolver(List<String> idStorage) {
        this.idStorage = idStorage;
    }

    public List<String> getIdStorage() {
        return idStorage;
    }

    public void setIdStorage(List<String> idStorage) {
        this.idStorage = idStorage;
    }

    @Override
    public boolean isBind(String id) {
        return this.idStorage.contains(id);
    }

    @Override
    public void bind(Pac4jPrincipal principal, Object user) {
        //添加即为绑定，因为切面那边判断访问首页时，是否已经在当前容器存在该用户
        getIdStorage().add(user.toString());
    }
}
