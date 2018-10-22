/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */



package com.toceansoft.cas.support.captcha;

/**
 * 校验码生成器
 *
 * @author Narci.Lee
 * @date 2017/10/27
 * @since 2.3.8
 */
public interface ITokenGenerator<T> {
    /**
     * 生成校验码
     *
     * @return
     */
    T generator();
}
