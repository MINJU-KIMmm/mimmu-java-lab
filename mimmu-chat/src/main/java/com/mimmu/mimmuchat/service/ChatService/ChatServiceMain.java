package com.mimmu.mimmuchat.service.ChatService;

import com.mimmu.mimmuchat.Entity.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import com.mimmu.mimmuchat.dto.ChatRoomDto;
import com.mimmu.mimmuchat.dto.ChatRoomMap;
//import com.mimmu.mimmuchat.service.fileService.FileService;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Getter
@Setter
@RequiredArgsConstructor
@Slf4j
public class ChatServiceMain {

    private final MsgChatService msgChatService;
    private final ChatRoomRepository chatRoomRepository;
    private final RoomUserRepository roomUserRepository;

    // 전체 채팅방 조회
    public List<ChatRoomDto> findAllRoom(){
        // 채팅방 생성 순서를 최근순으로 반환

        List<ChatRoomDto> chatRooms = chatRoomRepository.findAll()
                .stream()
                .map(chatRoom -> new ChatRoomDto(chatRoom))
                .collect(Collectors.toList());
        Collections.reverse(chatRooms);

        return chatRooms;
    }

    //내 전체 채팅방
    public List<ChatRoomDto> findAllMyRoom(String email) {
        List<RoomUser> roomUsers = roomUserRepository.findAllByChatUser_Email(email);
        List<ChatRoomDto> chatRooms = new ArrayList<>();
        for(RoomUser roomUser:roomUsers) {
            ChatRoom chatRoom = roomUser.getChatRoom();
            ChatRoomDto chatRoomDto = new ChatRoomDto(chatRoom);
            chatRooms.add(chatRoomDto);
        }
        return chatRooms;
    }

    public Boolean alreadyEnteredRoom(String roomId, String email) {
        return roomUserRepository.existsByChatRoom_UuidAndAndChatUser_Email(roomId, email);
    }
    // roomID 기준으로 채팅방 찾기
    public ChatRoomDto findRoomById(String roomId){
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByUuid(roomId);
//        ChatRoomDto chatRoomDto = ChatRoomDto.builder()
//                .roomId(chatRoom.getUuid())
//                .roomName(chatRoom.getRoomName())
//                .roomPwd(chatRoom.getRoomPwd())
//                .chatType(ChatRoomDto.ChatType.MSG)
//                .maxUserCnt(chatRoom.getMaxUserCount())
//                .userCount(chatRoom.getUserCount())
//                .secretChk(chatRoom.getSecretChk())
//                .build();
        ChatRoomDto chatRoomDto = new ChatRoomDto(chatRoom);
        return chatRoomDto;
    }

    // roomName 로 채팅방 만들기
    public ChatRoomDto createChatRoom(String roomName, String roomPwd, Boolean secretChk, Integer maxUserCnt, String chatType){

        ChatRoomDto room = msgChatService.createChatRoom(roomName, roomPwd, secretChk, maxUserCnt);
        ChatRoom chatRoom = ChatRoom.builder()
                .uuid(room.getRoomId())
                .roomPwd(room.getRoomPwd())
                .secretChk(secretChk)
                .maxUserCount(maxUserCnt)
                .chatType(chatType)
                .build();

        return room;

    }

    // 채팅방 비밀번호 조회
    public boolean confirmPwd(String roomId, String roomPwd) {
//        String pwd = chatRoomMap.get(roomId).getRoomPwd();
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByUuid(roomId);
        return roomPwd.equals(chatRoom.getRoomPwd());
    }

    // 채팅방 인원+1
    public void plusUserCnt(String roomId){
//        log.info("cnt {}",ChatRoomMap.getInstance().getChatRooms().get(roomId).getUserCount());
//        ChatRoomDto room = ChatRoomMap.getInstance().getChatRooms().get(roomId);
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByUuid(roomId);

        chatRoom.increaseUser();
    }

    // 채팅방 인원-1
    public void minusUserCnt(String roomId){
//        ChatRoomDto room = ChatRoomMap.getInstance().getChatRooms().get(roomId);
//        room.setUserCount(room.getUserCount()-1);

        ChatRoom chatRoom = chatRoomRepository.findChatRoomByUuid(roomId);

        chatRoom.decreaseUser();
    }

    // maxUserCnt 에 따른 채팅방 입장 여부
    public boolean chkRoomUserCnt(String roomId){
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByUuid(roomId);


        if (chatRoom.getUserCount() + 1 > chatRoom.getMaxUserCount()) {
            return false;
        }

        return true;
    }

    // 채팅방 삭제
    public void delChatRoom(String roomId){

        try {
            // 채팅방 타입에 따라서 단순히 채팅방만 삭제할지 업로드된 파일도 삭제할지 결정
            chatRoomRepository.delete(chatRoomRepository.findChatRoomByUuid(roomId));
//            ChatRoomMap.getInstance().getChatRooms().remove(roomId);

//            if (ChatRoomMap.getInstance().getChatRooms().get(roomId).getChatType().equals(ChatRoomDto.ChatType.MSG)) { // MSG 채팅방은 사진도 추가 삭제
//                // 채팅방 안에 있는 파일 삭제
//                fileService.deleteFileDir(roomId);
//            }

            log.info("삭제 완료 roomId : {}", roomId);

        } catch (Exception e) { // 만약에 예외 발생시 확인하기 위해서 try catch
            System.out.println(e.getMessage());
        }

    }



}
