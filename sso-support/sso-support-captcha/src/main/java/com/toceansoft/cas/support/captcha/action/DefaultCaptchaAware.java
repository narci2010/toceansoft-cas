/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.support.captcha.action;

/**
 * @author Narci.Lee
 * @date 2018/10/30
 * @since 2.3.8
 */
public class DefaultCaptchaAware implements ICaptchaAware {
    private String id;
    private String success;
    private String fail;

    public DefaultCaptchaAware(String id, String success, String fail) {
        this.id = id;
        this.success = success;
        this.fail = fail;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String success() {
        return success;
    }

    @Override
    public String fail() {
        return fail;
    }

    /**
     * 创建对象工具
     * @param id
     * @param success
     * @param fail
     * @return
     */
    public static DefaultCaptchaAware newInstance(String id, String success, String fail) {

        return new DefaultCaptchaAware(id, success, fail);
    }
}