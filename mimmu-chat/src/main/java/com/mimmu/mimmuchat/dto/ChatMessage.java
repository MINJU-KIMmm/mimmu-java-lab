package com.mimmu.mimmuchat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    public enum MessageType {
        //ENTER : 사용자가 처음 채팅방 들어옴
        //TALK : 이미 세션에 연결되어있고 채팅 중임
        ENTER, TALK
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
}
