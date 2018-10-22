package com.toceansoft.cas.test;
/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import com.toceansoft.cas.config.SsoConfigApplication;

import javax.mail.internet.MimeMessage;

/**
 * @author Narci.Lee
 * @version 创建时间：2017/12/20
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SsoConfigApplication.class)
public class MailTest {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mail;


    @Test
    public void connect() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mail);
        message.setTo(mail); //自己给自己发送邮件
        message.setSubject("主题：测试邮件");
        message.setText("测试邮件内容");

        //可以进行测试
//        mailSender.send(message);
    }
}
