/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */


package com.toceansoft.cas.support.captcha.string;

import com.toceansoft.cas.support.captcha.ICaptchaResultProvider;
import com.toceansoft.cas.support.captcha.ITokenGenerator;
import com.toceansoft.cas.support.captcha.SessionCaptchaResultAware;

/**
 * 字符串验证码识别器
 *
 * @author Narci.Lee
 * @date 2017/10/27
 * @since 2.3.8
 */
public class StringCaptchaResultAware extends SessionCaptchaResultAware {
    public StringCaptchaResultAware(ICaptchaResultProvider provider, ITokenGenerator generator) {
        super(provider, generator);
    }
}
