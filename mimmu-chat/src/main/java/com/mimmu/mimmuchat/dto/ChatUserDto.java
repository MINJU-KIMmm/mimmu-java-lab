package com.mimmu.mimmuchat.dto;

import com.mimmu.mimmuchat.Entity.ChatUser;
import lombok.*;

@Builder
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatUserDto {
    private Long id; // DB 저장되는 id : PK
    private String nickName; // 소셜에서 제공받은 유저명 => 유저 닉네임
    private String passwd; // 유저 패스워드
    private String email; // 소셜에서 제공받은 이메일

    public ChatUserDto(ChatUser chatUser) {
        this.id = chatUser.getId();
        this.nickName = chatUser.getNickName();
        this.passwd = chatUser.getPassword();
        this.email = chatUser.getEmail();
    }



//    // Member 엔티티를 ChatUserDto 로 변환 근데 mapper 를 사용하는 순간 필요없다
//    public static ChatUserDto of(ChatUser chatUserEntity) {
//        ChatUserDto chatUserDto = ChatUserDto.builder()
//                .id(chatUserEntity.getId())
//                .nickName(chatUserEntity.getNickName())
//                .email(chatUserEntity.getEmail())
//                .provider(chatUserEntity.getProvider())
//                .build();
//
//        return chatUserDto;
//    }


}
