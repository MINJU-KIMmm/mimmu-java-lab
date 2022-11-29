package com.mimmu.mimmuchat.service.ChatService;
import com.mimmu.mimmuchat.Entity.*;
import com.mimmu.mimmuchat.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import com.mimmu.mimmuchat.service.fileService.FileService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class MsgChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatUserRepository chatUserRepository;
    private final RoomUserRepository roomUserRepository;
    private final MessageRepository messageRepository;
    // 채팅방 삭제에 따른 채팅방의 사진 삭제를 위한 fileService 선언
//    private final FileService fileService;

    public ChatRoomDto createChatRoom(String roomName, String roomPwd, boolean secretChk, int maxUserCnt) {
        // roomName 와 roomPwd 로 chatRoom 빌드 후 return
//        ChatRoomDto room = ChatRoomDto.builder()
//                .roomId(UUID.randomUUID().toString())
//                .roomName(roomName)
//                .roomPwd(roomPwd) // 채팅방 패스워드
//                .secretChk(secretChk) // 채팅방 잠금 여부
//                .userCount(0) // 채팅방 참여 인원수
//                .maxUserCnt(maxUserCnt) // 최대 인원수 제한
//                .build();

//        room.setUserList(new HashMap<String, String>());

        ChatRoom chatRoom = ChatRoom.builder()
                .uuid(UUID.randomUUID().toString())
                .roomName(roomName)
                .chatType("msgChat")
                .roomPwd(roomPwd) // 채팅방 패스워드
                .secretChk(secretChk) // 채팅방 잠금 여부
                .userCount(0) // 채팅방 참여 인원수
                .maxUserCount(maxUserCnt) // 최대 인원수 제한
                .build();

        ChatRoomDto room = new ChatRoomDto(chatRoom);
        // msg 타입이면 ChatType.MSG
        room.setChatType(ChatRoomDto.ChatType.MSG);

        // map 에 채팅룸 아이디와 만들어진 채팅룸을 저장
//        ChatRoomMap.getInstance().getChatRooms().put(room.getRoomId(), room);
        chatRoomRepository.save(chatRoom);

        return room;
    }


    // 채팅방 유저 리스트에 유저 추가
    public String addUser(String roomId, String email){
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByUuid(roomId);
        ChatUser chatUser = chatUserRepository.findChatUserByEmail(email);

        //chatUserRepository.save(chatUser);
        // 아이디 중복 확인 후 userList 에 추가
        //room.getUserList().put(userUUID, userName);

//        HashMap<String, String> userList = (HashMap<String, String>)room.getUserList();
//        userList.put(userUUID, userName);

        RoomUser roomUser = RoomUser.builder()
                .chatUser(chatUser)
                .chatRoom(chatRoom)
                .build();
        if(!roomUserRepository.existsByChatRoom_UuidAndAndChatUser_Email(roomId, email)) {
            roomUserRepository.save(roomUser);
        }

        return email;
    }

    // 채팅방 유저 이름 중복 확인
    public String isDuplicateName(String roomId, String username){
//        ChatRoomDto room = chatRoomMap.get(roomId);
//        String tmp = username;
//
//        // 만약 userName 이 중복이라면 랜덤한 숫자를 붙임
//        // 이때 랜덤한 숫자를 붙였을 때 getUserlist 안에 있는 닉네임이라면 다시 랜덤한 숫자 붙이기!
//        while(room.getUserList().containsValue(tmp)){
//            int ranNum = (int) (Math.random()*100)+1;
//
//            tmp = username+ranNum;
//        }

        return username;
    }

    // 채팅방 userName 조회
    public String findUserNameByRoomIdAndUserUUID(String roomId, String email){
        ChatUser chatUser = roomUserRepository.findRoomUserByChatRoom_UuidAndChatUser_Email(roomId, email);
        return chatUser.getEmail();
    }

    // 채팅방 전체 userlist 조회
    public List<ChatUserDto> getUserList(String roomId){
        System.out.println(roomId);
        List<RoomUser> roomUsers = roomUserRepository.findAllByChatRoom_Uuid(roomId);
        List<ChatUser> chatUsers = new ArrayList<>();
        for(RoomUser roomUser:roomUsers) {
            chatUsers.add(roomUser.getChatUser());
        }
        List<ChatUserDto> chatUserDtos = chatUsers
                .stream()
                .map(roomUser -> new ChatUserDto(roomUser))
                .collect(Collectors.toList());

        return chatUserDtos;
    }

    // 채팅방 특정 유저 삭제
    public void delUser(String roomId, String email){
        roomUserRepository.deleteByChatRoom_UuidAndChatUser_Email(roomId, email);
    }

    @Transactional(rollbackFor = Exception.class)
    public void sendMessage(ChatDTO chatDTO) {
        Message message = Message.builder()
                .sender(chatUserRepository.findChatUserByEmail(chatDTO.getSender()))
                .chatRoom(chatRoomRepository.findChatRoomByUuid(chatDTO.getRoomId()))
                .message(chatDTO.getMessage())
                .build();

        messageRepository.save(message);
    }

    public List<MessageDto> getHistoryMessage(String roomId, String email) {
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByUuid(roomId);
        ChatUser chatUser = chatUserRepository.findChatUserByEmail(email);
        RoomUser roomUser = roomUserRepository.findByChatRoomAndChatUser(chatRoom, chatUser);
        LocalDateTime enterTime = roomUser.getEnterTime();

        return messageRepository.findAllByChatRoomAndSenderAndSendtimeIsAfter(chatRoom, chatUser, enterTime)
                .stream()
                .map(message -> new MessageDto(message))
                .collect(Collectors.toList());
    }

}
