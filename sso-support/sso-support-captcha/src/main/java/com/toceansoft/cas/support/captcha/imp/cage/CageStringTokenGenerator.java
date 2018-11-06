/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */


package com.toceansoft.cas.support.captcha.imp.cage;

import com.github.cage.token.RandomTokenGenerator;
import com.toceansoft.cas.support.captcha.string.StringTokenGenerator;

/**
 * cage字符串生成器
 *
 * @author Narci.Lee
 * @date 2018/10/27
 * @since 2.3.8
 */
public class CageStringTokenGenerator extends StringTokenGenerator {
    private RandomTokenGenerator generator = new RandomTokenGenerator(null, 4, 0);

    @Override
    public String generator() {
        return generator.next();
    }
}
