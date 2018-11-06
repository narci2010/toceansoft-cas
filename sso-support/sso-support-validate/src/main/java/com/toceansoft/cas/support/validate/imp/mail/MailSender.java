/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.support.validate.imp.mail;

import com.toceansoft.cas.support.validate.ISender;
import com.toceansoft.cas.support.validate.exception.ValidateSenderException;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * @author Narci.Lee
 * @date 2018/11/2
 * @since
 */
public class MailSender implements ISender<MailInformative> {
    private JavaMailSender mailSender;

    public MailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(MailInformative informative) throws ValidateSenderException {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(informative.getFromMail());//发送者.
        message.setTo(informative.getToMail());//接收者.
        message.setSubject(informative.getSubject());//邮件主题.
        message.setText(informative.getContent());//邮件内容.

        mailSender.send(message);//发送邮件
    }
}
