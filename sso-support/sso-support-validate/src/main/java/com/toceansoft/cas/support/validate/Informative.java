/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.support.validate;

import java.io.Serializable;

/**
 * @author Narci.Lee
 * @date 2018/11/2
 * @since 2.3.8
 */
public interface Informative extends Credential, Serializable {
    /**
     * 有效时间秒, -1 无限制
     *
     * @return
     */
    long effective();

    /**
     * 创建时间
     *
     * @return
     */
    long time();
}
