package com.example.chat_websocket.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class SocketHandler extends TextWebSocketHandler {
    //web socket 구현체에 등록할 핸들러
    //소켓통신은 서버와 클라이언트가 1:N의 관계를 맺는다. 즉, 하나의 서버에 다수 클라이언트가 접속할 수 있다. 따라서 서버는 다수의 클라이언트가 보낸 메세지를 처리할 핸들러가 필요하다.

    //웹소켓 세션을 담아둘 리스트
    List<WebSocketSession> list = new ArrayList<>();
    //사용자와 웹소켓 세션을 담아둘 맵
    Map<String, WebSocketSession> map = new HashMap<>();


    //웹소켓 연결되면 동작
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info(session + " 클라이언트 접속");
        //StandardWebSocketSession[id=e408f658-4a5c-0763-8a79-733a7a572a3d, uri=ws://localhost:8080/chating] 클라이언트 접속
        list.add(session);

        String senderId = getSenderId(session);
        map.put(senderId, session);
    }


    //메시지를 수신하면 실행됨.
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String senderId = getSenderId(session);  //id=e408f658-4a5c-0763-8a79-733a7a572a3d
        String payload = message.getPayload();   //전송된 데이터
        log.info("senderId : "+senderId + " payload : " + payload);

        //모든 사용자에게 메시지 발송
        for (WebSocketSession webSocketSession : list) {
            webSocketSession.sendMessage(new TextMessage(senderId + "#," + payload));
        }
    }


    //웹소켓 종료되면 동작
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(session + " 클라이언트 접속 해제");
        list.remove(session);
    }



    private String getSenderId(WebSocketSession session){
        //Map<String, Object> httpSessions = session.getAttributes();
        String userId = session.getUri().getPath().replace("/chating/", "");
        return userId;
    }
}
