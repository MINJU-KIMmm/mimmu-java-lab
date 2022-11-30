package com.mimmu.mimmuchat.Entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    public List<Message> findAllByChatRoomAndSendtimeIsAfter(ChatRoom chatRoom, LocalDateTime enterTime);
}
