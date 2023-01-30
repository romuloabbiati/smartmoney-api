package com.smartgroup.smartmoney.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class ResourceCreatedEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private HttpServletResponse response;

	public ResourceCreatedEvent(Object source, HttpServletResponse response, Long id) {
		super(source);
		this.id = id;
		this.response = response;
	}

	public Long getId() {
		return id;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

}
