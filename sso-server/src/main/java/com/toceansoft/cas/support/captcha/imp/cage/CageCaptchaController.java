/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */


package com.toceansoft.cas.support.captcha.imp.cage;

import com.toceansoft.cas.support.captcha.CaptchaController;
import com.toceansoft.cas.support.captcha.ICaptchaWriter;
import com.toceansoft.cas.support.captcha.SessionCaptchaResultAware;
import com.toceansoft.cas.support.captcha.SessionCaptchaResultProvider;
import com.toceansoft.cas.support.captcha.string.StringCaptchaResultAware;

/**
 * Cage验证码控制器
 *
 * @author Narci.Lee
 * @date 2017/10/27
 */
public class CageCaptchaController extends CaptchaController {

    public CageCaptchaController(ICaptchaWriter<String> captchaWriter, SessionCaptchaResultAware<String> aware) {
        super(captchaWriter, aware);
    }

    public CageCaptchaController() {
        super(new CageStringCaptchaWriter(), new StringCaptchaResultAware(new SessionCaptchaResultProvider(), new CageStringTokenGenerator()));
    }

    public CageCaptchaController(SessionCaptchaResultProvider provider) {
        super(new CageStringCaptchaWriter(), new StringCaptchaResultAware(provider, new CageStringTokenGenerator()));
    }
}
