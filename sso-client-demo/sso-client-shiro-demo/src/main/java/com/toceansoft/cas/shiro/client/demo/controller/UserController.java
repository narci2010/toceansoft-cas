/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.shiro.client.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.PrincipalCollection;
import org.pac4j.cas.client.rest.CasRestFormClient;
import org.pac4j.cas.profile.CasProfile;
import org.pac4j.cas.profile.CasRestProfile;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.credentials.TokenCredentials;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.jwt.profile.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Narci.Lee
 * @date 2017/9/16
 * @since 1.0.0
 */
@RestController()
@RequestMapping("/user")
public class UserController {

	@Value("#{ @environment['cas.serviceUrl'] ?: null }")
	private String serviceUrl;
	@Autowired
	private CasRestFormClient casRestFormClient;
	@Autowired
	private JwtGenerator jwtGenerator;

	@GetMapping("/{id}")
	public Object user(@PathVariable(value = "id") String id) {
		return "users page:" + id;
	}

	@GetMapping("/detail")
	public Object detail(HttpServletRequest request) {
		// 用户详细信息
		return request.getUserPrincipal();
	}

	@RequestMapping("/login")
	public Object login(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<>();
		J2EContext context = new J2EContext(request, response);
		final ProfileManager<CasRestProfile> manager = new ProfileManager(context);
		final Optional<CasRestProfile> profile = manager.get(true);
		// 获取ticket
		TokenCredentials tokenCredentials = casRestFormClient.requestServiceTicket(serviceUrl,
				profile.get(), context);
		// 根据ticket获取用户信息
		final CasProfile casProfile = casRestFormClient.validateServiceTicket(serviceUrl,
				tokenCredentials, context);
		// 生成jwt token
		String token = jwtGenerator.generate(casProfile);
		model.put("token", token);
		return new HttpEntity<>(model);
	}

	@RequiresPermissions(value = "role:edit")
	@GetMapping(value = "/roles/{id}")
	public String put() {
		return "允许修改角色";
	}

	@RequiresPermissions(value = "user:info")
	@GetMapping(value = "/users/{id}")
	public PrincipalCollection getUserById() {
		return SecurityUtils.getSubject().getPrincipals();
	}

	@GetMapping(value = "/you")
	public String you() {
		return "you you 不需要权限";
	}

}
