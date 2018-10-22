/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.client.demo;

import org.jasig.cas.client.authentication.UrlPatternMatcherStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Narci.Lee
 * @date 2018/10/17
 * @since 1.0.0
 */
public class SimpleUrlPatternMatcherStrategy implements UrlPatternMatcherStrategy {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean matches(String url) {
		logger.debug("访问路径：" + url);
		return url.contains("zhangsan.jsp") || url.contains("testjquery.html");
	}

	@Override
	public void setPattern(String pattern) {

	}
}
