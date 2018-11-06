/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */


package com.toceansoft.cas.support.captcha.imp.cage;

import com.github.cage.Cage;
import com.github.cage.GCage;
import com.toceansoft.cas.support.captcha.string.StringCaptchaWriter;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.OutputStream;

/**
 * http://akiraly.github.io/cage/quickstart.html 验证码库
 *
 * @author Narci.Lee
 * @date 2018/10/27
 * @since 2.3.8
 */
public class CageStringCaptchaWriter extends StringCaptchaWriter {
    private Cage cage = new GCage();

    @Override
    public void write(String text, OutputStream outputStream) throws IOException {
        ImageIO.write(cage.drawImage(text),"png", outputStream);
    }
}
