package com.mimmu.mimmuchat.mapper;

import com.mimmu.mimmuchat.Entity.ChatUser;
import com.mimmu.mimmuchat.dto.ChatUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ChatUserMapper {
    ChatUserMapper INSTANCE = Mappers.getMapper(ChatUserMapper.class);

    ChatUserDto toDto(ChatUser e);
    ChatUser toEntity(ChatUserDto d);
}
