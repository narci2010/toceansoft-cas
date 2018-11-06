/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */
package com.toceansoft.cas.support.single.service;

import java.util.List;

/**
 * 用户认证识别器
 *
 * @author Narci.Lee
 * @version 创建时间：2017/11/29
 */
public interface IUserIdObtainService {

    /**
     * 通过登录方式查询其他的id
     *
     * @param clientName 登录方式
     * @param id         用户id
     * @return 所有用户id
     */
    List<String> obtain(String clientName, String id);
}
