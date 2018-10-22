/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.shiro.client.demo.aspect;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.toceansoft.cas.shiro.client.demo.core.ClientStrategy;
import com.toceansoft.cas.shiro.client.demo.core.ClientStrategyFactory;

import io.buji.pac4j.subject.Pac4jPrincipal;

/**
 * @author Narci.Lee
 * @date 2017/10/8
 * @since 1.0.0
 */
@Aspect
@Component
public class ThirdPartyLoginAspect {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ClientStrategyFactory clientStrategyFactory;

	/**
	 * 定义一个切入点.
	 * <p>
	 * 解释下：
	 * <p>
	 * <p>
	 * <p>
	 * ~ 第一个 * 代表任意修饰符及任意返回值.
	 * <p>
	 * ~ 第二个 * 任意包名
	 * <p>
	 * ~ 第三个 * 代表任意方法.
	 * <p>
	 * ~ 第四个 * 定义在web包或者子包
	 * <p>
	 * ~ 第五个 * 任意方法
	 * <p>
	 * ~ .. 匹配任意数量的参数.
	 */

	@Pointcut("execution(public * com.toceansoft.cas.shiro..*.controller.IndexController.*(..))")
	public void loginHandle() {
	}

	@Before("loginHandle()")
	public void doBefore(JoinPoint pjp) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Principal principal = request.getUserPrincipal();
		if (principal != null && principal instanceof Pac4jPrincipal) {
			logger.debug("准备判断用户绑定状态");
			Pac4jPrincipal pac4jPrincipal = (Pac4jPrincipal) principal;

			// 获取认证客户端
			String clientName = (String) pac4jPrincipal.getProfile().getAttribute("clientName");
			// 获取客户端策略
			ClientStrategy clientStrategy = clientStrategyFactory.getClientStrategy()
					.get(clientName);
			if (clientStrategy != null) {

				// 判断该客户端是否已经有绑定用户
				if (!clientStrategy.isBind(pac4jPrincipal)) {
					logger.debug("用户[" + pac4jPrincipal.getProfile().getId() + "]通过"
							+ clientStrategy.name() + "登录尚未绑定");
					// 未绑定给予处理
					clientStrategy.handle(pjp, pac4jPrincipal);
				}
			}
		}
	}
}
