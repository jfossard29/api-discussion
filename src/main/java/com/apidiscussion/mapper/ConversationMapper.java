package com.apidiscussion.mapper;

import com.apidiscussion.entity.ConversationEntity;
import com.apidiscussion.dto.Conversation;

public class ConversationMapper {
    public Conversation toDto(ConversationEntity entity) {
        Conversation dto = new Conversation();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
    public ConversationEntity toEntity(Conversation dto) {
        ConversationEntity entity = new ConversationEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }
}
