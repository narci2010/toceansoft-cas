/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.support.validate.imp.mail;

import javax.validation.constraints.NotNull;

import com.toceansoft.cas.support.validate.ValidateCredential;

/**
 * @author Narci.Lee
 * @date 2017/11/2
 * @since 2.3.8
 */
public class MailValidateCredential extends MailCredential implements ValidateCredential {
    //校验码
    private String code;

    /**
     * @param sessionId
     * @param mail
     * @param busId     业务id，用于区分发送场景
     */
    public MailValidateCredential(@NotNull String sessionId, @NotNull String mail, @NotNull String busId, @NotNull String code) {
        super(sessionId, mail, busId);
        this.code = code;
    }

    @Override
    public Object data() {
        return code;
    }

    @Override
    public String id() {
        return super.id();
    }
}
