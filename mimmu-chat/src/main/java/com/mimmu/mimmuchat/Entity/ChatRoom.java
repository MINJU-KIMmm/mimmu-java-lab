package com.mimmu.mimmuchat.Entity;

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
    @GeneratedValue
    private Long id;

    @Column(name = "room_uuid", length = 300)
    private String uuid;

    @Column(name = "room_pwd")
    private String roomPwd;

    @Column(name = "secret_chk", length = 45)
    private String secretChk;

    @Column(name = "max_user_chk", length = 45)
    private String maxUserChk;

    @Column(name = "chat_type", length = 45)
    private String chatType;

}
