package org.example.springwebsocket.app.config;

import org.example.springwebsocket.app.handler.CustomAccessDeniedHandler;
import org.example.springwebsocket.app.handler.CustomLogoutHandler;
import org.example.springwebsocket.app.security.JwtFilter;
import org.example.springwebsocket.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtFilter jwtFilter;

    private final UserService userService;

    private final CustomAccessDeniedHandler accessDeniedHandler;

    private final CustomLogoutHandler customLogoutHandler;

    public SecurityConfig(JwtFilter jwtFilter,
                          UserService userService,
                          CustomAccessDeniedHandler accessDeniedHandler, 
                          CustomLogoutHandler customLogoutHandler) {
      
        this.jwtFilter = jwtFilter;
        this.userService = userService;
        this.accessDeniedHandler = accessDeniedHandler;
        this.customLogoutHandler = customLogoutHandler;
    }
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->{
                        auth.requestMatchers("/index.html","/css/**","/js/**","/ws/**","/topic/**","/app/**","/").permitAll();
                        auth.requestMatchers("/api/auth/**","/actuator/**").permitAll();
                        auth.anyRequest().authenticated();
                }
                )
                .userDetailsService( userService )
        		.exceptionHandling(e ->{
        		e.accessDeniedHandler( accessDeniedHandler );
        		e.authenticationEntryPoint( new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
        		})
        		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        		.addFilterBefore( jwtFilter, UsernamePasswordAuthenticationFilter.class)
        		.logout(log -> {
        			log.logoutUrl("/logout");
        			log.addLogoutHandler( customLogoutHandler );
        			log.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
        		})
        		.build();
    }
}
