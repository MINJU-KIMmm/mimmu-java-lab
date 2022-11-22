package com.mimmu.mimmuchat.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "ROOM_USER")
@Entity
@Getter
@NoArgsConstructor
public class RoomUser {
    @Id
    @Column(name = "room_user_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ChatUser chatUser;

}
