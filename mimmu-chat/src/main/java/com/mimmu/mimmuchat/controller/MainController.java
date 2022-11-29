package com.mimmu.mimmuchat.controller;


import com.mimmu.mimmuchat.Entity.ChatUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.mimmu.mimmuchat.service.ChatService.ChatServiceMain;
import com.mimmu.mimmuchat.service.ChatService.MsgChatService;
import com.mimmu.mimmuchat.service.social.PrincipalDetails;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final ChatServiceMain chatServiceMain;

    // 채팅 리스트 화면
    // / 로 요청이 들어오면 전체 채팅룸 리스트를 담아서 return

    // 스프링 시큐리티의 로그인 유저 정보는 Security 세션의 PrincipalDetails 안에 담긴다
    // 정확히는 PrincipalDetails 안에 ChatUser 객체가 담기고, 이것을 가져오면 된다.
    @GetMapping("/")
    public String goChatRoom(Model model, @AuthenticationPrincipal ChatUser chatUser){
//        System.out.println(chatUser.getEmail());

        //System.out.println(chatUser.getEmail());
        if(chatUser == null) {
            System.out.println("is Null");
        }
        // principalDetails 가 null 이 아니라면 로그인 된 상태!!
        if (chatUser != null) {
            // 세션에서 로그인 유저 정보를 가져옴
            model.addAttribute("list", chatServiceMain.findAllMyRoom(chatUser.getEmail()));

            model.addAttribute("user", chatUser.getEmail());
            log.debug("user [{}] ", chatUser.getEmail());
            log.debug("SHOW ALL ChatList {}", chatServiceMain.findAllMyRoom(chatUser.getEmail()));

        }

//        model.addAttribute("user", "hey");
        return "roomlist";
    }

}