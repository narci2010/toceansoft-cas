/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */


package com.toceansoft.cas.support.captcha;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;

/**
 * session提供
 *
 * @author Narci.Lee
 * @date 2018/10/27
 * @since
 */
public class SessionCaptchaResultProvider implements ICaptchaResultProvider<HttpSession, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionCaptchaResultProvider.class);
    @Override
    public void store(HttpSession httpSession, String s) {
        httpSession.setAttribute(CaptchaConstants.STORE_CODE, s);
    }

    @Override
    public String get(HttpSession httpSession) {
        return (String) httpSession.getAttribute(CaptchaConstants.STORE_CODE);
    }

    @Override
    public boolean validate(HttpSession store, String code) {
        String relCode = get(store);
        if (!StringUtils.isEmpty(relCode) && relCode.equals(code)) {
            //校验完清空
            store(store, null);
            return true;
        }
        return false;
    }
}
