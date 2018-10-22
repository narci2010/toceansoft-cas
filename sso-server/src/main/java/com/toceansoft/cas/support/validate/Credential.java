/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.support.validate;

import java.io.Serializable;

/**
 * @author Narci.Lee
 * @date 2017/11/2
 * @since 2.3.8
 */
public interface Credential extends Serializable {
    /**
     * 唯一标志
     *
     * @return
     */
    String id();
}
