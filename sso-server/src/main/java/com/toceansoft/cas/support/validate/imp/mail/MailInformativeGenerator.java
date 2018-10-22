/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.support.validate.imp.mail;

import com.toceansoft.cas.support.validate.InformativeGenerator;
import com.toceansoft.cas.support.validate.configuration.MailProperties;

/**
 * @author Narci.Lee
 * @date 2017/11/2
 * @since
 */
public class MailInformativeGenerator implements InformativeGenerator<MailInformative, MailCredential> {
    private MailProperties properties;

    public MailInformativeGenerator(MailProperties properties) {
        this.properties = properties;
    }

    @Override
    public MailInformative generate(MailCredential mailCredential) {
        //生成随机码
        int code = (int) ((Math.random() * 9 + 1) * Math.pow(10, properties.getCodeLen()));
        String strCode = String.valueOf(code);
        MailInformative informative = new MailInformative()
                .setCode(strCode)
                .setEffective(properties.getEffective())
                .setContent(String.format(properties.getContent(), strCode))
                .setFromMail(properties.getFrom())
                .setToMail(mailCredential.getMail())
                .setId(mailCredential.id())
                .setSubject(properties.getSubject());

        return informative;
    }
}
