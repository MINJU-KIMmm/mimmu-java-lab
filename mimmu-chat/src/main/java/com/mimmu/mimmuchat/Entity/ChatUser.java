package com.mimmu.mimmuchat.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "CHAT_USER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname")
    private String nickName;

    @Column
    private String email;

    @Column
    private String passwd; // 유저 패스워드

    @Column
    private String provider;
}