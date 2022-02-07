package com.example.chat_websocket.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SocketHandler extends TextWebSocketHandler {
    //web socket 구현체에 등록할 핸들러
    //소켓통신은 서버와 클라이언트가 1:N의 관계를 맺는다. 즉, 하나의 서버에 다수 클라이언트가 접속할 수 있다. 따라서 서버는 다수의 클라이언트가 보낸 메세지를 처리할 핸들러가 필요하다.

    //웹소켓 세션을 담아둘 맵맵
    private static List<WebSocketSession> list = new ArrayList<>();

    //웹소켓 연결되면 동작
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info(session + " 클라이언트 접속");
        //StandardWebSocketSession[id=e408f658-4a5c-0763-8a79-733a7a572a3d, uri=ws://localhost:8080/chating] 클라이언트 접속
    }


    //메시지를 수신하면 실행됨.
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("message : "+message); //message : TextMessage payload=[d], byteCount=1, last=true]
        log.info("payload : " + payload);   //전송된 데이터

    }


    //웹소켓 종료되면 동작
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(session + " 클라이언트 접속 해제");

    }

}
