/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.shiro.client.demo.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.toceansoft.cas.shiro.client.demo.exception.NotBindException;

import javax.servlet.http.HttpServletRequest;

/**
 *
 *
 * @author Narci.Lee
 * @date 2018/10/9
 * @since 1.0.0
 */
@ControllerAdvice
public class NotBindExceptionController {

    /**
     * 抛出未绑定异常时进行转发页面处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = NotBindException.class)
    public ModelAndView notBindHandler(NotBindException e) {
        return new ModelAndView("redirect:" + e.getRedirectUrl());
    }
}
