/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */



package com.toceansoft.cas.support.captcha;

/**
 * 验证码输出结果对象
 *
 * @author Narci.Lee
 * @date 2017/10/27
 * @since
 */
public interface ICaptchaResultAware<S, T> {
    /**
     * 获取数据结果
     *
     * @param s 存储器
     * @return
     */
    T getAndStore(S s);
}
