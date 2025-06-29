package org.example.springwebsocket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.aspectj.lang.annotation.Before;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.example.springwebsocket.app.config.SecurityConfig;
import org.example.springwebsocket.app.controller.AuthController;
import org.example.springwebsocket.app.model.User;
import org.example.springwebsocket.app.model.dto.AuthRequest;
import org.example.springwebsocket.app.repository.UserRepository;
import org.example.springwebsocket.app.security.JwtUtil;
import org.example.springwebsocket.app.service.AuthenticationService;
import org.example.springwebsocket.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.jayway.jsonpath.JsonPath;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
class SpringWebsocketApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	AuthController authController;
	
	@BeforeAll
	void set_up() {
		//userRepository.deleteAll();
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername( "admin" );
		authRequest.setPassword( "admin" );
		authController.register( authRequest );
	}
	@Test
	void login_ShouldReturnJwt() throws Exception{	
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername( "admin" );
		authRequest.setPassword( "admin" );
		String token = authenticationService.authentication(authRequest);
		MvcResult result = mockMvc.perform(post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
						"username": "admin",
						"password": "admin"
					}
					"""))
				.andExpect(status().isOk())
				.andReturn();
		String response = result.getResponse().getContentAsString();
		String token1 = JsonPath.parse( response ).read( "$.token", String.class );
		assertEquals( token, token1 );
	}
	@Test
	void register_ShouldReturnJwt() throws Exception{
		mockMvc.perform(post("/api/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
						"username": "admin1",
						"password": "admin1"
					}
					"""))
				.andExpect(status().isOk());
				//.andExpect(content().string(token));
	}
	@Test
	void register_UserAlreadyExist() throws Exception{
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername( "admin1" );
		authRequest.setPassword( "admin" );
		
		Throwable exception = assertThrows( RuntimeException.class, ()-> authController.register( authRequest ) );
		assertEquals( "User already exists", exception.getMessage() );
	}
	@Test
	void login_UserNotFound() throws Exception{
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername( "user" );
		authRequest.setPassword( "admin" );
		
		Throwable exception = assertThrows( RuntimeException.class, ()-> authController.login( authRequest ) );
		assertEquals( "User not found", exception.getMessage() );
	}
	@Test
	void login_InvalidCredentials() throws Exception{
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername( "admin" );
		authRequest.setPassword( "admin1111" );
		
		Throwable exception = assertThrows( RuntimeException.class, ()-> authController.login( authRequest ) );
		assertEquals( "Invalid credentials", exception.getMessage() );
	}
	
	
	
	
}
