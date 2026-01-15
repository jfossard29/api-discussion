package com.apidiscussion.mapper;
import com.apidiscussion.entity.MemberEntity;
import com.apidiscussion.entity.RoleEntity;
import com.apidiscussion.dto.Member;
import com.apidiscussion.dto.Role;

public class MembreMapper {

public Member toDto(MemberEntity entity) {
        Member dto = new Member();
        dto.setId(entity.getId());
        dto.setRole(Role.valueOf(String.valueOf(entity.getRole())));
        dto.setLastJoinDate(entity.getLastJoinDate());
        dto.setActive(entity.isActive());
        dto.setConversationId(entity.getConversationId());
        dto.setUserId(entity.getUserId());
        return dto;
    }

    public MemberEntity toEntity(Member dto) {
        MemberEntity entity = new MemberEntity();
        entity.setId(dto.getId());
        entity.setRole(RoleEntity.valueOf(String.valueOf(dto.getRole())));
        entity.setLastJoinDate(dto.getLastJoinDate());
        entity.setActive(dto.isActive());
        entity.setConversationId(dto.getConversationId());
        entity.setUserId(dto.getUserId());
        return entity;
    }

}
