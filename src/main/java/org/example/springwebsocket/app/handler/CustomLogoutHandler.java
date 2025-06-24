package org.example.springwebsocket.app.handler;

import org.example.springwebsocket.app.model.Token;
import org.example.springwebsocket.app.repository.TokenRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLogoutHandler implements LogoutHandler{
	
	private final TokenRepository tokenRepository;
	
	public CustomLogoutHandler(TokenRepository tokenRepository) {
		this.tokenRepository = tokenRepository;
	}
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		
		String authHeader = request.getHeader("Authorization");
		
		if(authHeader == null || authHeader.startsWith("Bearer ")) {
			return;
		}
		String token = authHeader.substring( 7 );
		Token tokenEntity = tokenRepository.findByAccessToken( token ).orElse( null );
		
		if(tokenEntity != null) {
			tokenEntity.setLoggedOut( true );
			tokenRepository.save( tokenEntity );
		}
	}
}
