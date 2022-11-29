package com.mimmu.mimmuchat.controller;

import com.mimmu.mimmuchat.Entity.ChatUserRepository;
import com.mimmu.mimmuchat.dto.SignupRequestDto;
import com.mimmu.mimmuchat.service.ChatUserService;
import com.mimmu.mimmuchat.util.errorutil.CustomException;
import com.mimmu.mimmuchat.util.errorutil.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class ChatUserController {
    private final ChatUserService chatUserService;
    private final ChatUserRepository chatUserRepository;

    @PostMapping("/signup")
    public String signup(@RequestParam String email, @RequestParam String password, @RequestParam String nickName){
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .email(email)
                .password(password)
                .nickName(nickName)
                .build();
        if (chatUserService.checkDuplicateUsers(signupRequestDto))
            throw new CustomException(ErrorCode.CANNOT_DUPLICATE_EMAIL);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //signupRequestDto.encryptPassword(passwordEncoder);
        signupRequestDto.setPassword(passwordEncoder.encode(password));
        signupRequestDto.setRole("USER");
        chatUserService.joinUser(signupRequestDto);
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String signupForm(Model model){
        model.addAttribute("signupRequestDto", new SignupRequestDto());
        return "signup";
    }

//    @PostMapping("/login")
//    public String login(@RequestParam String email, @RequestParam String password) {
//        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
//                .email(email)
//                .password(password)
//                .build();
//
//        ChatUser user = chatUserRepository.findChatUserByEmail(loginRequestDto.getEmail().trim()); //서비스로 리팩토링
//
//        if (!chatUserRepository.existsByEmail(loginRequestDto.getEmail().trim())) {
//            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
//        }
//
//        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())){
//            throw new CustomException(ErrorCode.INCORRECT_PASSWORD);
//        }
//
//        tokenService.updateRefreshToken(loginRequestDto.getEmail(), refresh); //리프레시 토큰 저장
//
////        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
////                .user(user)
////                .accessToken(access)
////                .refreshToken(refresh)
////                .build();
////        return loginResponseDto;
//        System.out.println(email);
//        return "redirect:/";
//    }

    @GetMapping("/login")
    public String loginForm() {
        //model.addAttribute("signupRequestDto", new SignupRequestDto());
        return "login";
    }

}
