/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */


package com.toceansoft.cas.support.captcha;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 验证码输出者
 *
 * @author Narci.Lee
 * @date 2017/10/27
 * @since
 */
public interface ICaptchaWriter<T> {

    /**
     * 对外写出验证码并且返回结果集
     *
     * @param outputStream
     * @return
     * @throws IOException
     */
    void write(T t, OutputStream outputStream) throws IOException;
}
