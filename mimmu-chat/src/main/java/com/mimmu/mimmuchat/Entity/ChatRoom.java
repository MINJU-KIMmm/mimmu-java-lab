package com.mimmu.mimmuchat.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "CHAT_ROOM")
@Entity
public class ChatRoom {
    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_uuid", length = 300)
    private String uuid;

    @Column(name = "room_pwd")
    private String roomPwd;

    @Column(name = "secret_chk")
    private Boolean secretChk;


    @Column(name = "chat_type", length = 45)
    private String chatType;

    @Column(name = "user_count")
    private Integer userCount;

    @Column(name = "max_user_count")
    private Integer maxUserCount;

    @Column(name = "room_name", length = 45)
    private String roomName;

    @Builder
    public ChatRoom(Long id, String uuid, String roomPwd, Boolean secretChk, String chatType, Integer maxUserCount, String roomName, Integer userCount) {
        this.id = id;
        this.uuid = uuid;
        this.roomPwd = roomPwd;
        this.secretChk = secretChk;
        this.chatType = chatType;
        this.maxUserCount = maxUserCount;
        this.userCount = userCount;
        this.roomName = roomName;
    }

    public void increaseUser() {
        this.userCount += 1;
    }

    public void decreaseUser() {
        this.userCount -= 1;
    }

}
