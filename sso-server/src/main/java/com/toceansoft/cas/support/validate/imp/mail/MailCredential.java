/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.support.validate.imp.mail;

import com.toceansoft.cas.support.validate.Credential;

/**
 * @author Narci.Lee
 * @date 2017/11/2
 * @since 2.3.8
 */
public class MailCredential implements Credential {
    private String mail;
    private String sessionId;
    private String busId;


    /**
     * @param sessionId
     * @param mail
     * @param busId     业务id，用于区分发送场景
     */
    public MailCredential(String sessionId, String mail, String busId) {
        this.mail = mail;
        this.sessionId = sessionId;
        this.busId = busId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String id() {
        return String.format("%s-%s-%s", sessionId, mail, busId);
    }
}
