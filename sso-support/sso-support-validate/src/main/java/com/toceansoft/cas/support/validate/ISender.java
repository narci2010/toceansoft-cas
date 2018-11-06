/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.support.validate;

import com.toceansoft.cas.support.validate.exception.ValidateSenderException;

/**
 * 校验发送者，发送短信，邮件等
 *
 * @author Narci.Lee
 * @date 2018/11/2
 * @since 2.3.8
 */
public interface ISender<T extends Informative> {

    /**
     * @param t
     * @throws ValidateSenderException 发送失败时抛出异常
     */
    void send(T t) throws ValidateSenderException;
}
