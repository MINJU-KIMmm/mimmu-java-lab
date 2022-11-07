package com.mimmu.mimmuchat.dto;

import com.mimmu.mimmuchat.config.WebSocketHandler;
import com.mimmu.mimmuchat.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ChatRoom {
    //ChatRoom은 roomId, name, session을 관리할 sessions를 가짐
    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    //json 데이터를 받아 WebSocketHandler에서 해당 데이터에 담긴 roomId를 chatService를 통해 조회해서 해당 id의 채팅방을 찾아 json 데이터에 담긴 메시지를 해당 채팅방으로 보내는 기능을 담당
    public void handlerActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
        }
        sendMessage(chatMessage, chatService); //해당 채팅방에 입장해 있는 모든 클라이언트들(Websocket session)에게 메시지 보냄
    }

    private <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream()
                .forEach(session -> chatService.sendMessage(session, message));
    }
}
