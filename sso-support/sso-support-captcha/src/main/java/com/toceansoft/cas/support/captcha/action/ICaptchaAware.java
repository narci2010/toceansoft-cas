/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.support.captcha.action;


/**
 * @author Narci.Lee
 * @date 2018/10/30
 * @since
 */
public interface ICaptchaAware {
    /**
     * 识别的id流转
     * @return
     */
    String id();

    /**
     * 成功流转
     * @return
     */
    String success();

    /**
     * 失败流转
     * @return
     */
    String fail();
}
