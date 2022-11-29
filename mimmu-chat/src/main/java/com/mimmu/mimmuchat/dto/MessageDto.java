package com.mimmu.mimmuchat.dto;

import com.mimmu.mimmuchat.Entity.ChatRoom;
import com.mimmu.mimmuchat.Entity.ChatUser;
import com.mimmu.mimmuchat.Entity.Message;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class MessageDto {
    private Long id;
    private String roomId;
    private String sender;
    private String message;
    private LocalDateTime sendtime;

    @Builder
    public MessageDto(Long id, ChatRoom chatRoom, ChatUser chatUser, String message, LocalDateTime sendtime) {
        this.id = id;
        this.roomId = chatRoom.getUuid();
        this.sender = chatUser.getEmail();
        this.message = message;
        this.sendtime = sendtime;
    }

    public MessageDto(Message message) {
        this.id = message.getId();
        this.roomId = message.getChatRoom().getUuid();
        this.sender = message.getSender().getEmail();
        this.message = message.getMessage();
        this.sendtime = message.getSendtime();
    }
}
