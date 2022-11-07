package com.mimmu.mimmuchat.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mimmu.mimmuchat.dto.ChatMessage;
import com.mimmu.mimmuchat.dto.ChatRoom;
import com.mimmu.mimmuchat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler { //스피링이 지원하는 Text/Binary 타입의 핸들러 중 Text타입 선택(채팅 서비스 만들기 때문)

    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("{}", payload);
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class); //json 형식으로 웹소켓을 통해 서버로 보낸 메시지를 Handler가 받아 ObjectMapper로 해당 데이터를 chatMessage.class에 맞게 파싱하여 객체로 변환

        ChatRoom chatRoom = chatService.findRoomById(chatMessage.getRoomId()); //json 데이터에 있는 roomId 이용해서 해당 채팅방을 찾아 handlerAction()이라는 메서드 실행
        chatRoom.handlerActions(session, chatMessage, chatService); //참여자가 이미 채팅방에 접속된 상태인지, 채팅에 참여햐 있는 상태인지 판별해서 채팅방에 처음 참여하는 거면 session을 연결해줌과 동시에 메시지를 보내고 아니라면 메시지를 해당 채팅방으로 보냄
    }

}
