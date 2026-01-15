package com.apidiscussion.service;

import com.apidiscussion.entity.ConversationEntity;
import com.apidiscussion.entity.MemberEntity;
import com.apidiscussion.entity.RoleEntity;

import java.util.List;

public interface MembreRepository {
    List<MemberEntity> getAllMembers(String id);
    ConversationEntity postMembers(String id, List<MemberEntity> members);
    ConversationEntity deleteMembers(String id, List<String> membersId);
    ConversationEntity getMember(String id, String memberId);
    ConversationEntity ModifyRoleMember(String id, String memberId, RoleEntity role);
    ConversationEntity deleteMember(String id, String memberId);
}
