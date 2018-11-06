/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.support.validate;

/**
 * @param <T> 发送信息
 * @param <I> 构建信息
 * @author Narci.Lee
 * @date 2018/11/2
 * @since
 */
public interface InformativeGenerator<T extends Informative, I extends Credential> {

    /**
     * 生成发送信息
     * @param i
     * @return
     */
    T generate(I i);
}
