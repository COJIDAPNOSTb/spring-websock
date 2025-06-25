package org.example.springwebsocket.service;

import java.io.IOException;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

public class JwtInterceptor  {
//	
//	@Bean
//	public RestTemplate restTemplate(RestTemplateBuilder builder) {
//		RestTemplate restTemplate = builder.build();
//		restTemplate.getInterceptors().add( new JwtInterceptor() );
//		return restTemplate;
//	}
//	
//	
//	@Override
//	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
//		String jwt = getJwt();
//		if(jwt != null) {
//			request.getHeaders().setBearerAuth( jwt );
//		}
//		return execution.execute( request, body );
//	}
//	
//	private String getJwt() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if(authentication != null && authentication.getCredentials() instanceof String) {
//			return (String)authentication.getCredentials();
//		}
//		return null;
//	}

}
