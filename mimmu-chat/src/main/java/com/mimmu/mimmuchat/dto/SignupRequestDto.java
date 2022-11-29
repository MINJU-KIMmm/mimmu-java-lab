package com.mimmu.mimmuchat.dto;

import com.mimmu.mimmuchat.Entity.ChatUser;
import com.mimmu.mimmuchat.util.errorutil.CustomException;
import com.mimmu.mimmuchat.util.errorutil.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequestDto {
    private String nickname;
    private String email;
    private String password;
    private String role;

    @Builder
    public SignupRequestDto(String email, String password, String nickName, String role) {
        this.nickname = nickName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void encryptPassword(BCryptPasswordEncoder passwordEncoder) {

        if(password.isEmpty())
            throw new CustomException(ErrorCode.CANNOT_EMPTY_CONTENT);
        else
            this.password = passwordEncoder.encode(password);
    }

    public ChatUser toEntity(SignupRequestDto signupRequestDto) {
        return ChatUser.builder()
                .nickName(signupRequestDto.getNickname())
                .email(signupRequestDto.getEmail())
                .password(signupRequestDto.getPassword())
                .build();
    }
}
