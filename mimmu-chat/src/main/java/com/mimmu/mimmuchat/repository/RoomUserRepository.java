package com.mimmu.mimmuchat.repository;

import com.mimmu.mimmuchat.Entity.RoomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomUserRepository extends JpaRepository<RoomUser, Long> {
}
