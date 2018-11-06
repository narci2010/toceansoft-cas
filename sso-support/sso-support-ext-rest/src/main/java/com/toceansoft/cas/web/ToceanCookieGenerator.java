package com.toceansoft.cas.web;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apereo.cas.CipherExecutor;
import org.apereo.cas.configuration.model.support.cookie.TicketGrantingCookieProperties;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.registry.TicketRegistry;
import org.apereo.cas.web.support.CookieRetrievingCookieGenerator;
import org.apereo.cas.web.support.CookieUtils;
import org.apereo.cas.web.support.CookieValueManager;
import org.apereo.cas.web.support.DefaultCasCookieValueManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.CookieGenerator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ToceanCookieGenerator extends CookieGenerator {

	@Autowired
	private CookieValueManager cookieValueManager;
	@Autowired
	private TicketRegistry ticketRegistry;

	@Autowired
	@Qualifier("cookieCipherExecutor")
	private CipherExecutor cookieCipherExecutor;

	@Autowired
	@Qualifier("ticketGrantingTicketCookieGenerator")
	private CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator;

	@PostConstruct
	public void init() {

		LOGGER.debug("cookieValueManager:" + cookieValueManager);
		LOGGER.debug("ticketRegistry:" + ticketRegistry);
		LOGGER.debug("cookieCipherExecutor:" + cookieCipherExecutor);
		LOGGER.debug("ticketGrantingTicketCookieGenerator:" + ticketGrantingTicketCookieGenerator);
		super.setCookieName(ticketGrantingTicketCookieGenerator.getCookieName());
		super.setCookiePath(ticketGrantingTicketCookieGenerator.getCookiePath());
		this.setCookieDomain(ticketGrantingTicketCookieGenerator.getCookieDomain());
		super.setCookieMaxAge(ticketGrantingTicketCookieGenerator.getCookieMaxAge());
		super.setCookieSecure(ticketGrantingTicketCookieGenerator.isCookieSecure());
		super.setCookieHttpOnly(ticketGrantingTicketCookieGenerator.isCookieHttpOnly());

		cookieValueManager = new DefaultCasCookieValueManager(cookieCipherExecutor,
				new TicketGrantingCookieProperties());
	}

	public void addCookie(String tgt) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		HttpServletResponse response = attributes.getResponse();
		String theCookieValue = this.cookieValueManager.buildCookieValue(tgt, request);
		LOGGER.debug("this time TGC cookie VALUE is:" + theCookieValue);
		super.addCookie(response, theCookieValue);
	}

	public TicketGrantingTicket getTicketGrantingTicketFromRequest(
			final HttpServletRequest request) {
		return CookieUtils.getTicketGrantingTicketFromRequest(ticketGrantingTicketCookieGenerator,
				ticketRegistry, request);
	}

	public String getTicketGrantingTicketIdFromRequest(final HttpServletRequest request) {
		// LOGGER.debug("getCookieName:" +
		// ticketGrantingTicketCookieGenerator.getCookieName());
		// Cookie cookie = org.springframework.web.util.WebUtils.getCookie(request,
		// "TGC");
		// if (cookie != null) {
		// LOGGER.debug("cookieValue1:" + cookie.getValue());
		// LOGGER.debug("cookieCipherExecutor:" + cookieCipherExecutor.getClass());
		// if (cookie.getValue() != null) {
		// Object cookieValue = cookieCipherExecutor.decode(cookie.getValue(),
		// new Object[] {});
		// LOGGER.debug("cookieValue2:" + cookieValue);
		// }
		// }
		// String tgtId = cookieValueManager.obtainCookieValue(cookie, request);
		// LOGGER.debug("getTicketGrantingTicketIdFromRequest.tgtId:" + tgtId);
		return ticketGrantingTicketCookieGenerator.retrieveCookieValue(request);
	}

}
