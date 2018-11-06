/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.support.validate;

import com.toceansoft.cas.support.validate.exception.ValidateSenderException;

/**
 * @author Narci.Lee
 * @date 2018/11/2
 * @since 2.3.8
 */
public interface IValidateService<T extends Credential, O extends ValidateCredential> {
    /**
     * 发送数据
     *
     * @param t
     */
    void send(T t) throws ValidateSenderException;

    /**
     * 校验
     *
     * @param o
     * @return
     */
    ValidateResult validate(O o);
}
