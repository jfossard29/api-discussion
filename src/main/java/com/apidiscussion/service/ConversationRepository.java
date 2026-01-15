package com.apidiscussion.service;

import com.apidiscussion.entity.ConversationEntity;

import java.util.List;

public interface ConversationRepository {

    List<ConversationEntity> getAllConversations();
    ConversationEntity findById(String idDiscussion);
    void createConversation(String name);
    ConversationEntity updateConversationName(String id, String name);
    ConversationEntity deleteConversation(String id);
}
