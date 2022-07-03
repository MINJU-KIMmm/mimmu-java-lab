package com.mimmu.springchat;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

//핸들러를 이용해 웹소켓을 활성화하기 위한 Config
@Configuration
@RequiredArgsConstructor
@EnableWebSocket //WebSocket 활성화
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatHandler chatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //도메인이 다른 서버에서도 접속 가능하도록 CORS 설정
        //WebSocket에 접속하기 위한 Endpoint = /chat
        registry.addHandler(chatHandler, "ws/chat").setAllowedOrigins("*");
    }
}
