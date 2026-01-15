package com.apidiscussion.service.impl;

import com.apidiscussion.entity.ConversationEntity;
import com.apidiscussion.repository.ConversationJpaRepository;
import com.apidiscussion.service.ConversationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConversationImpl implements ConversationRepository {

    private final ConversationJpaRepository conversationJpaRepository;

    public ConversationImpl(ConversationJpaRepository conversationJpaRepository) {
        this.conversationJpaRepository = conversationJpaRepository;
    }

    @Override
    public List<ConversationEntity> getAllConversations() {
        return conversationJpaRepository.findAll();
    }

    @Override
    public ConversationEntity findById(String idDiscussion) {
        return conversationJpaRepository.findById(idDiscussion).orElse(null);
    }

    @Override
    public void createConversation(String name) {
        ConversationEntity conversation = new ConversationEntity();
        conversation.setName(name);
        conversationJpaRepository.save(conversation);
    }

    @Override
    public ConversationEntity updateConversationName(String id, String name) {
        ConversationEntity conversation = findById(id);
        if (conversation != null) {
            conversation.setName(name);
            return conversationJpaRepository.save(conversation);
        }
        return null;
    }

    @Override
    public ConversationEntity deleteConversation(String id) {
        ConversationEntity conversation = findById(id);
        if (conversation != null) {
            conversationJpaRepository.delete(conversation);
            return conversation;
        }
        return null;
    }
}
