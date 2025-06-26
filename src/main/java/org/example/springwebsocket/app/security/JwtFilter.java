package org.example.springwebsocket.app.security;

import java.io.IOException;

import org.example.springwebsocket.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtFilter extends OncePerRequestFilter{
	
	private final JwtUtil jwtUtil;
	
	private final UserService userService;
	
	public JwtFilter(JwtUtil jwtUtil, UserService userService) {
		this.jwtUtil = jwtUtil;
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter( request, response );
			return;
		}
		String token = authHeader.substring( 7 );

		String username = jwtUtil.extractUsername( token );
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userService.loadUserByUsername( username );

			if(jwtUtil.isValid( token, userDetails )) {
				UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(
						userDetails,null,userDetails.getAuthorities());
				authtoken.setDetails( new WebAuthenticationDetailsSource().buildDetails( request ) );

				SecurityContextHolder.getContext().setAuthentication( authtoken );
				System.out.println( authtoken );
			}
			else System.out.println("Failed validation: " + token + " Requeries: " +userDetails.getUsername() );
			
		}else System.out.println( "1234567890-pl,mnbvcdswertyjm vcxdsdertghjnm vcxsdfghjnm xzsdfghn xaserfghbn " );
		filterChain.doFilter( request, response );
	}
	

}
