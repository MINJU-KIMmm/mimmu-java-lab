package com.mimmu.mimmuchat.controller;
import com.mimmu.mimmuchat.Entity.ChatRoom;
import com.mimmu.mimmuchat.Entity.ChatRoomRepository;
import com.mimmu.mimmuchat.Entity.ChatUser;
import com.mimmu.mimmuchat.dto.MessageDto;
import com.mimmu.mimmuchat.service.ChatService.MsgChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.mimmu.mimmuchat.dto.ChatRoomMap;
import com.mimmu.mimmuchat.service.ChatService.ChatServiceMain;
import com.mimmu.mimmuchat.dto.ChatRoomDto;
import com.mimmu.mimmuchat.service.social.PrincipalDetails;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatRoomController {

    // ChatService Bean 가져오기
    private final ChatServiceMain chatServiceMain;
    private final MsgChatService msgChatService;

    // 채팅방 생성
    // 채팅방 생성 후 다시 / 로 return
    @PostMapping("/chat/createroom")
    public String createRoom(@RequestParam("roomName") String name,
                             @RequestParam("roomPwd") String roomPwd,
                             @RequestParam("secretChk") String secretChk,
                             @RequestParam(value = "maxUserCnt", defaultValue = "2") String maxUserCnt,
                             @RequestParam("chatType") String chatType,
                             RedirectAttributes rttr, @AuthenticationPrincipal ChatUser chatUser) {

        // log.info("chk {}", secretChk);

        // 매개변수 : 방 이름, 패스워드, 방 잠금 여부, 방 인원수
        ChatRoomDto room;

        room = chatServiceMain.createChatRoom(name, roomPwd, Boolean.parseBoolean(secretChk), Integer.parseInt(maxUserCnt), chatType);


        log.info("CREATE Chat Room [{}]", room);

        rttr.addFlashAttribute("roomName", room);
        System.out.println(name);

        chatServiceMain.plusUserCnt(room.getRoomId());

        // 채팅방에 유저 추가 및 UserUUID 반환
        String email = msgChatService.addUser(room.getRoomId(), chatUser.getEmail());
        return "redirect:/";
    }

    // 채팅방 입장 화면
    // 파라미터로 넘어오는 roomId 를 확인후 해당 roomId 를 기준으로
    // 채팅방을 찾아서 클라이언트를 chatroom 으로 보낸다.
    @GetMapping("/chat/room")
    public String roomDetail(Model model, String roomId, @AuthenticationPrincipal ChatUser chatUser){

        log.info("roomId {}", roomId);

        // principalDetails 가 null 이 아니라면 로그인 된 상태!!
        if (chatUser != null) {
            // 세션에서 로그인 유저 정보를 가져옴
            model.addAttribute("user", chatUser.getEmail());
        }

        ChatRoomDto room = chatServiceMain.findRoomById(roomId);
        model.addAttribute("room", room);

        System.out.println(room.getChatType());
        return "chatroom";

    }

    // 채팅방 비밀번호 확인
    @PostMapping("/chat/confirmPwd/{roomId}")
    @ResponseBody
    public boolean confirmPwd(@PathVariable String roomId, @RequestParam String roomPwd){

        // 넘어온 roomId 와 roomPwd 를 이용해서 비밀번호 찾기
        // 찾아서 입력받은 roomPwd 와 room pwd 와 비교해서 맞으면 true, 아니면  false
        return chatServiceMain.confirmPwd(roomId, roomPwd);
    }

    // 채팅방 삭제
    @GetMapping("/chat/delRoom/{roomId}")
    public String delChatRoom(@PathVariable String roomId){

        // roomId 기준으로 chatRoomMap 에서 삭제, 해당 채팅룸 안에 있는 사진 삭제
        chatServiceMain.delChatRoom(roomId);

        return "redirect:/";
    }

    // 유저 카운트
    @GetMapping("/chat/chkUserCnt/{roomId}")
    @ResponseBody
    public boolean chUserCnt(@PathVariable String roomId, Principal chatUser){

        return chatServiceMain.chkRoomUserCnt(roomId, chatUser.getName());
    }
}
