package com.mimmu.mimmuchat.service;

import com.mimmu.mimmuchat.Entity.ChatUser;
import com.mimmu.mimmuchat.Entity.ChatUserRepository;
import com.mimmu.mimmuchat.dto.ChatUserDto;
import com.mimmu.mimmuchat.dto.SignupRequestDto;
import com.mimmu.mimmuchat.util.errorutil.CustomException;
import com.mimmu.mimmuchat.util.errorutil.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatUserService implements UserDetailsService {
    private final ChatUserRepository chatUserRepository;

    @Transactional
    public ResponseEntity<Object> joinUser(SignupRequestDto signupRequestDto){

        ChatUser user = signupRequestDto.toEntity(signupRequestDto);

        chatUserRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user.getEmail()+"님이 성공적으로 가입되었습니다.");
    }


    public void deleteUserById(Long id) {
        chatUserRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        chatUserRepository.deleteById(id);
    }


    public ChatUserDto findUserById(Long id) {
        ChatUser user = chatUserRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        return new ChatUserDto(user);
    }


    public List<ChatUserDto> findUserList() {
        return chatUserRepository.findAll()
                .stream()
                .map(user -> new ChatUserDto(user))
                .collect(Collectors.toList());
    }


    public Boolean checkDuplicateUsers(SignupRequestDto signupRequestDto){
        return chatUserRepository.existsByEmail(signupRequestDto.getEmail());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ChatUser chatUser = chatUserRepository.findChatUserByEmail(username);
        if (chatUser == null) {
            throw new UsernameNotFoundException("User not authorized");
        }
        return chatUser;
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        return chatUserRepository.findChatUserByEmail(email);
//                //.orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 사용자입니다."));
//
//    }
//
//    @Transactional
//    public UserUpdateResponseDto updateUser(Long userId, UserUpdateRequestDto updateRequestDto){
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
//        user.update(updateRequestDto);
//
//        return new UserUpdateResponseDto(userRepository.save(user), "정보가 수정되었습니다.");
//    }


}