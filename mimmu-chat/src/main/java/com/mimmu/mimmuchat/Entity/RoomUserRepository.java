package com.mimmu.mimmuchat.Entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomUserRepository extends JpaRepository<RoomUser, Long> {
    public ChatUser findRoomUserByChatRoom_UuidAndChatUser_Email(String uuid, String email);
    public List<RoomUser> findAllByChatRoom_Uuid(String uuid);
    public void deleteByChatRoom_UuidAndChatUser_Email(String uuid, String email);
}
