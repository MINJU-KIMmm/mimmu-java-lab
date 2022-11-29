package com.mimmu.mimmuchat.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Table(name = "message")
@Entity
public class Message{

    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "sender")
    private ChatUser sender;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime sendtime;

    @PrePersist
    public void createDate(){
        this.sendtime = LocalDateTime.now();
    }



    @Builder
    public Message(Long id, ChatRoom chatRoom, ChatUser sender, String message) {
        this.id = id;
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.message = message;
    }

}
