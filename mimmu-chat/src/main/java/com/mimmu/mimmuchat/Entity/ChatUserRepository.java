package com.mimmu.mimmuchat.Entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {
//    public Optional<ChatUser> findChatUserByEmail(String email);
    public ChatUser findChatUserByEmail(String email);
    public Boolean existsByEmail(String email);
    public Optional<ChatUser> findByEmail(String email);
}
