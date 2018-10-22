/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.support.validate.exception;

import com.toceansoft.cas.support.validate.ISender;

/**
 * @author Narci.Lee
 * @date 2017/11/2
 * @since 2.3.8
 */
public class ValidateSenderException extends Exception {
    private Class<? extends ISender> clz;

    public ValidateSenderException(Class<? extends ISender> clz) {
        this.clz = clz;
    }

    public ValidateSenderException(String message, Class<? extends ISender> clz) {
        super(message);
        this.clz = clz;
    }

    public ValidateSenderException(String message, Throwable cause, Class<? extends ISender> clz) {
        super(message, cause);
        this.clz = clz;
    }

    public ValidateSenderException(Throwable cause, Class<? extends ISender> clz) {
        super(cause);
        this.clz = clz;
    }

    public ValidateSenderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Class<? extends ISender> clz) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.clz = clz;
    }
}
