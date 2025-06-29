package org.example.springwebsocket;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.example.springwebsocket.app.model.ChatMessage;
import org.example.springwebsocket.app.model.MessageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
public class WebSocketTest {
	
//	@LocalServerPort
//	private int port;
//	private WebSocketStompClient stompClient;
//	
//	@BeforeEach
//	public void set_up() {
//		this.stompClient = new WebSocketStompClient(new StandardWebSocketClient());
//		//List<MessageConverter> converters = new ArrayList<>();
//		//converters.add( new MappingJackson2MessageConverter() );
//		this.stompClient.setMessageConverter( new MappingJackson2MessageConverter() );
//	}
//	@Test
//	public void testChatSendMessage() throws Exception{
//		ChatMessage message = new ChatMessage();
//		message.setSender("admin");
//		message.setContent("hello");
//		message.setType( MessageType.CHAT );
//		BlockingQueue<ChatMessage> responseQueue = new ArrayBlockingQueue<>(1);
//		StompSession session = stompClient.connectAsync( "ws://localhost:"+port+"/ws", new StompSessionHandlerAdapter() {}).get(5,TimeUnit.SECONDS);
//		session.subscribe( "/topic/public", new StompFrameHandler() {
//			@Override
//			public Type getPayloadType(StompHeaders headers) {
//				return ChatMessage.class;
//			}
//			@Override
//			public void handleFrame(StompHeaders headers, Object payload) {
//				responseQueue.offer((ChatMessage)payload);
//			}
//		});
//		session.send( "/chat.sendMessage", message);
//		ChatMessage response = responseQueue.poll(5,TimeUnit.SECONDS);
//		assertThat(response).isNotNull();
//		assertThat(response.getSender()).isEqualTo("admin");
//		assertThat(response.getContent()).isEqualTo("hello");
//	}
	
}
