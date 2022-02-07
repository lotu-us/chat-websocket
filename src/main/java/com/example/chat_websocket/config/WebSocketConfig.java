package com.example.chat_websocket.config;

import com.example.chat_websocket.handler.SocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired private SocketHandler socketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketHandler, "/chating/*")  //구현체 등록. //WebSocket에 접속하기 위한 Endpoint는 /chating
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                //websocketsession에서 httpsession로 접근할 수 있게 도와줌
                //.setAllowedOrigins("*") //도메인이 다른 서버에서도 접속 가능하게한다
                .withSockJS();  //sockJS 가능하게


    }
}
