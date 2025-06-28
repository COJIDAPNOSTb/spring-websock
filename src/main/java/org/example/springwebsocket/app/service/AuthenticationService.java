package org.example.springwebsocket.app.service;

import java.util.Collections;
import java.util.List;

import org.example.springwebsocket.app.model.Token;
import org.example.springwebsocket.app.model.User;
import org.example.springwebsocket.app.model.dto.AuthRequest;
import org.example.springwebsocket.app.model.dto.AuthResponse;
import org.example.springwebsocket.app.repository.TokenRepository;
import org.example.springwebsocket.app.repository.UserRepository;
import org.example.springwebsocket.app.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

	private final UserRepository userRepo;
	private final TokenRepository tokenRepo;
	private final JwtUtil jwtUtil;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationService(UserRepository userRepo,
								TokenRepository tokenRepo, 
								JwtUtil jwtUtil,
								PasswordEncoder passwordEncoder,
								AuthenticationManager authenticationManager) {
		this.userRepo = userRepo;
		this.tokenRepo = tokenRepo;
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.passwordEncoder = passwordEncoder;
	}
	

	
	private void revokeAll(User user) {
		List<Token> validToken = tokenRepo.findAllAccessTokenByUser( user.getId() );
		if(!validToken.isEmpty()) {
			validToken.forEach( t -> t.setLoggedOut( true ) );
		}
		tokenRepo.saveAll( validToken );
	}
	
	private void saveUserToken(User user, String accessToken) {
		Token token = new Token();
		token.setAccessToken( accessToken );
		token.setLoggedOut( false );
		token.setUser( user );
		tokenRepo.save(token);
	}
	
	public String authentication(AuthRequest request) {
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken( 
					request.getUsername(),
					request.getPassword() 
				)
			);
		User user = userRepo.findByUsername( request.getUsername() ).orElseThrow();
		String accessToken = jwtUtil.generateToken( user.getUsername() );
		revokeAll( user );
		saveUserToken( user, accessToken);
		return  accessToken;
	}
	
}
