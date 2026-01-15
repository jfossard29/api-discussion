package com.apidiscussion.repository;

import com.apidiscussion.entity.ConversationEntity;
import com.apidiscussion.entity.MemberEntity;
import com.apidiscussion.entity.RoleEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ConversationRepository {

    List<ConversationEntity> getAllConversations();
    ConversationEntity findById(String idDiscussion);
    void createConversation(String name);
    ConversationEntity updateConversationName(String id, String name);
    ConversationEntity deleteConversation(String id);
}
