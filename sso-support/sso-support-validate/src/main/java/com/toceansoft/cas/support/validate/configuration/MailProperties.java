/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.support.validate.configuration;

import java.io.Serializable;

/**
 * @author Narci.Lee
 * @date 2018/11/2
 * @since
 */
public class MailProperties implements Serializable {
    private boolean enable = true;
    //有效期，十分钟有效
    private long effective = 60 * 10;
    //邮件内容，例如：你的验证码为%s
    private String content = "欢迎注册，你的验证码为：%s（统一登录门户）";

    private String subject = "统一门户注册验证码";
    //来自哪个用户发送
    private String from;
    //验证码长度
    private int codeLen = 6;

    public long getEffective() {
        return effective;
    }

    public void setEffective(long effective) {
        this.effective = effective;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getCodeLen() {
        return codeLen;
    }

    public void setCodeLen(int codeLen) {
        this.codeLen = codeLen;
    }
}
