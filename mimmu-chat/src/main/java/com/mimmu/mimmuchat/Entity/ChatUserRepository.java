package com.mimmu.mimmuchat.Entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {
    public ChatUser findChatUserByEmail(String email);
}
