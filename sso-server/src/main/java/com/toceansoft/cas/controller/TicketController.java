package com.toceansoft.cas.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.support.rest.resources.ServiceTicketResource;
import org.apereo.cas.support.rest.resources.TicketGrantingTicketResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.toceansoft.cas.util.MatchHtmlElementAttrValue;
import com.toceansoft.cas.vo.Ticket;
import com.toceansoft.cas.web.ToceanCookieGenerator;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TicketController {
	private static final int HTTP_MAX_SUCCESS_STATUS = 399;

	@Autowired
	private TicketGrantingTicketResource ticketGrantingTicketResource;
	@Autowired
	private ServiceTicketResource serviceTicketResourceRestController;

	@Autowired
	private ToceanCookieGenerator toceanCookieGenerator;

	/**
	 * Create new ticket granting ticket.
	 *
	 * @param requestBody
	 *            username and password application/x-www-form-urlencoded values
	 * @param request
	 *            raw HttpServletRequest used to call this method
	 * @return ResponseEntity representing RESTful response
	 */
	@PostMapping(value = "/v2/ticket", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> createTicketGrantingTicket(
			@RequestBody(required = false) final MultiValueMap<String, String> requestBody,
			final HttpServletRequest request, final HttpServletResponse response) {

		ResponseEntity<?> re = null;

		// 1. 通过用户提交表达数据进行登陆验证，成功返回包含tgtId的HTML代码
		re = ticketGrantingTicketResource.createTicketGrantingTicket(requestBody, request);

		// 登陆失败提示(不知道原因)
		if (re == null) {
			re = new ResponseEntity<Ticket>(new Ticket("Login fail,please relogin."),
					HttpStatus.EXPECTATION_FAILED);
			return re;
		}
		if (re.getStatusCodeValue() > HTTP_MAX_SUCCESS_STATUS) {
			// 说明已经发生某种错误了
			if (!re.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
				re = new ResponseEntity<Ticket>(new Ticket((String) re.getBody()),
						re.getStatusCode());
			}
			return re;
		}
		LOGGER.debug("createTicketGrantingTicket:" + re.getBody());
		// 2. 将成功返回的TGC添加cookie
		toceanCookieGenerator.addCookie((String) re.getBody());

		String tgtId = null;
		// 3. HTML代码中抽取真正的tgtId
		if (re != null && re.getBody() != null) {
			if (isDefaultContentType(request))
				tgtId = MatchHtmlElementAttrValue.getTgtId((String) re.getBody());
			else
				tgtId = (String) re.getBody();
			LOGGER.debug("tgtId:" + tgtId);
			// 3.通过tgtId获取st
			if (tgtId != null && !StringUtils.isBlank(tgtId)) {
				re = serviceTicketResourceRestController.createServiceTicket(request, tgtId);
				re = new ResponseEntity<Ticket>(new Ticket((String) re.getBody()),
						re.getStatusCode());
			}
		}

		// 请求重定向
		// response.setStatus(302);
		// response.setHeader("location", "index.jsp");

		// 方法二
		// response.sendRedirect("index.jsp");

		// 清楚掉response全部数据
		// 备份header
		// if (response != null) {
		// Collection<String> headerNames = response.getHeaderNames();
		// Map<String, String> headers = new HashMap<String, String>();
		// for (String headerName : headerNames) {
		// LOGGER.debug(headerName + ":" + response.getHeader(headerName));
		// if (!"Location".equalsIgnoreCase(headerName))
		// headers.put(headerName, response.getHeader(headerName));
		// }
		// response.reset();
		// headers.forEach((name, value) -> {
		// response.addHeader(name, value);
		// });
		// }
		return re;
	}

	// TGT-1-wanWmB1y8-rTINDx02HIXXXA3f6BAhelPYGgj77rjyXjrghTurG7mGPVbIJa7AhDc-8SKY-20171031ICW
	// could not be found or is considered invalid
	// 从客户端得到所有cookie信息
	// Cookie[] allCookies = request.getCookies();
	// int i = 0;
	// // 如果allCookies不为空...
	// if (allCookies != null) {
	// // 从中取出cookie
	// for (i = 0; i < allCookies.length; i++) {
	// // 依次取出
	// Cookie temp = allCookies[i];
	// if (temp.getName().equals("TGC")) {
	// //将该cookie删除
	// temp.setMaxAge(0);
	// break;
	//
	// }
	// }
	//
	// }

	@GetMapping(value = "/v2/ticket")
	public ResponseEntity<?> createTicketGrantingTicketThruTGCCookie(
			final HttpServletRequest request) {
		// 获取cookie：TGC->TGT
		// cookie存在，则获取cookie中的TGC，并通过TGC获取TGT
		// 通过TGT获取ST

		// 1. 此时的tgtId其实是一段包含tgtId的HTML代码
		String tgtId = toceanCookieGenerator.getTicketGrantingTicketIdFromRequest(request);

		if (tgtId != null) {
			// 2.从html代码中取出真正的tgtId
			tgtId = MatchHtmlElementAttrValue.getTgtId(tgtId);
			LOGGER.debug("tgtId:" + tgtId);
		}

		ResponseEntity<?> re = null;
		if (tgtId != null && !StringUtils.isBlank(tgtId)) {
			re = serviceTicketResourceRestController.createServiceTicket(request, tgtId);
			if (re != null) {
				if (((String) re.getBody())
						.contains("could not be found or is considered invalid")) {
					re = new ResponseEntity<Ticket>(new Ticket("Invalid ticket or not exists."),
							HttpStatus.MOVED_PERMANENTLY);
				} else {
					re = new ResponseEntity<Ticket>(new Ticket((String) re.getBody()),
							re.getStatusCode());
				}
			}
		}

		// cookie中根本不存在TGC
		if (re == null) {
			re = new ResponseEntity<Ticket>(new Ticket("Invalid ticket or not exists."),
					HttpStatus.MOVED_PERMANENTLY);
		}
		return re;
	}

	private boolean isDefaultContentType(final HttpServletRequest request) {
		final String accept = request.getHeader(HttpHeaders.ACCEPT) == null ? null
				: request.getHeader(HttpHeaders.ACCEPT).trim();
		return StringUtils.isBlank(accept) || accept.startsWith(MediaType.ALL_VALUE)
				|| accept.startsWith(MediaType.TEXT_HTML_VALUE);
	}

}
