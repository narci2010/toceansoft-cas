/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */


package com.toceansoft.cas.support.captcha;



/**
 * 验证码结果提供者
 *
 * @author Narci.Lee
 * @date 2018/10/27
 * @since
 */
public interface ICaptchaResultProvider<T, S>{
    /**
     * 把S存储到T
     *
     * @param t
     * @param s
     */
    void store(T t, S s);

    /**
     * 在T中提供出s
     * @param t
     * @return
     */
    S get(T t);

    /**
     * 校验
     * @param store 持久化对象
     * @param code 校验编码
     * @return
     */
    boolean validate(T store, S code);
}

