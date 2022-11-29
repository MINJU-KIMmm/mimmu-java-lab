package com.mimmu.mimmuchat.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "ROOM_USER")
@Entity
@Getter
@NoArgsConstructor
public class RoomUser {
    @Id
    @Column(name = "room_user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ChatUser chatUser;

    @Column(name = "enter_time")
    private LocalDateTime enterTime;

    @Builder
    public RoomUser(Long id, ChatRoom chatRoom, ChatUser chatUser) {
        this.id = id;
        this.chatRoom = chatRoom;
        this.chatUser = chatUser;
    }

    @PrePersist
    public void enterTime() {
        this.enterTime = LocalDateTime.now();
    }
}
