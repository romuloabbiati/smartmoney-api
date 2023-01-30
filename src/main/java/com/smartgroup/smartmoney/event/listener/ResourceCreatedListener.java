package com.smartgroup.smartmoney.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.smartgroup.smartmoney.event.ResourceCreatedEvent;

@Component
public class ResourceCreatedListener implements ApplicationListener<ResourceCreatedEvent> {

	@Override
	public void onApplicationEvent(ResourceCreatedEvent event) {
		
		HttpServletResponse response = event.getResponse();
		Long id = event.getId();
		
		addHeaderLocation(id, response);
	}
	
	public void addHeaderLocation(Long id, HttpServletResponse response) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(id).toUri();
			
			response.setHeader("Location", uri.toASCIIString());
	}

}
