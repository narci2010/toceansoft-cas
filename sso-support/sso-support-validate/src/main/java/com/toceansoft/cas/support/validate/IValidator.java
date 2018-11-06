/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.support.validate;

/**
 * 验证数据
 *
 * @author Narci.Lee
 * @date 2018/11/2
 * @since
 */
public interface IValidator<T extends ValidateCredential> {
    /**
     * 验证器名称
     *
     * @return
     */
    String name();


    /**
     * 鉴定验证码
     *
     * @param t
     * @return
     */
    ValidateResult identify(T t);
}
