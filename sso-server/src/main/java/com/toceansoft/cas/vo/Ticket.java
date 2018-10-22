package com.toceansoft.cas.vo;

import lombok.Data;

@Data
public class Ticket {

	private String ticket;

	public Ticket(String ticket) {
		this.ticket = ticket;

	}

}
