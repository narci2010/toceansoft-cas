package com.toceansoft.cas.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CopyrightController {

	@GetMapping("/copyright")
	public Map<String, String> copyright(HttpServletResponse response) {
		// response.setContentType("text/html;charset=UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		// cookie的值不能有32空格
		Cookie cookie = new Cookie("copyright", "Toceansoft-All-rights-Received.");
		// cookie.setDomain("http://www.toceansoft.com");
		// 不能有协议
		// cookie.setDomain("www.toceansoft.com");
		response.addCookie(cookie);
		Map<String, String> map = new HashMap<String, String>();
		map.put("copyright", "Toceansoft All rights Received.");
		return map;
	}

}
