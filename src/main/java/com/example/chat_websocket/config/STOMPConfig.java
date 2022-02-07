package com.example.chat_websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class STOMPConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp-chating")  //핸드셰이크 커넥션을 생성할 경로
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .withSockJS();
        //STOMP는 sockJS기반이다
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");  //메모리 기반 브로커가 해당 api를 구독하고 있는 클라이언트에게 메시지를 전달한다
        registry.setApplicationDestinationPrefixes("/");    //클라이언트로부터 메세지를 받을 api의 prefix를 설정한다.
        //Controller의 MessageMapping 메서드로 라우팅된다.
    }
}
