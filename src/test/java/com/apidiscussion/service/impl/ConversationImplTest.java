package com.apidiscussion.service.impl;

import com.apidiscussion.entity.ConversationEntity;
import com.apidiscussion.repository.ConversationJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConversationImplTest {

    @Mock
    private ConversationJpaRepository conversationJpaRepository;

    @InjectMocks
    private ConversationImpl conversationService;

    @Test
    void getAllConversations() {
        ConversationEntity c1 = new ConversationEntity();
        ConversationEntity c2 = new ConversationEntity();
        when(conversationJpaRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<ConversationEntity> result = conversationService.getAllConversations();

        assertEquals(2, result.size());
        verify(conversationJpaRepository).findAll();
    }

    @Test
    void findById() {
        String id = "123";
        ConversationEntity c = new ConversationEntity();
        when(conversationJpaRepository.findById(id)).thenReturn(Optional.of(c));

        ConversationEntity result = conversationService.findById(id);

        assertNotNull(result);
        verify(conversationJpaRepository).findById(id);
    }

    @Test
    void findById_NotFound() {
        String id = "123";
        when(conversationJpaRepository.findById(id)).thenReturn(Optional.empty());

        ConversationEntity result = conversationService.findById(id);

        assertNull(result);
    }

    @Test
    void createConversation() {
        String name = "Test Conversation";
        
        conversationService.createConversation(name);

        verify(conversationJpaRepository).save(any(ConversationEntity.class));
    }

    @Test
    void updateConversationName() {
        String id = "123";
        String newName = "New Name";
        ConversationEntity c = new ConversationEntity();
        c.setName("Old Name");
        
        when(conversationJpaRepository.findById(id)).thenReturn(Optional.of(c));
        when(conversationJpaRepository.save(any(ConversationEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ConversationEntity result = conversationService.updateConversationName(id, newName);

        assertNotNull(result);
        assertEquals(newName, result.getName());
        verify(conversationJpaRepository).save(c);
    }

    @Test
    void updateConversationName_NotFound() {
        String id = "123";
        String newName = "New Name";
        
        when(conversationJpaRepository.findById(id)).thenReturn(Optional.empty());

        ConversationEntity result = conversationService.updateConversationName(id, newName);

        assertNull(result);
        verify(conversationJpaRepository, never()).save(any());
    }

    @Test
    void deleteConversation() {
        String id = "123";
        ConversationEntity c = new ConversationEntity();
        
        when(conversationJpaRepository.findById(id)).thenReturn(Optional.of(c));

        ConversationEntity result = conversationService.deleteConversation(id);

        assertNotNull(result);
        verify(conversationJpaRepository).delete(c);
    }

    @Test
    void deleteConversation_NotFound() {
        String id = "123";
        
        when(conversationJpaRepository.findById(id)).thenReturn(Optional.empty());

        ConversationEntity result = conversationService.deleteConversation(id);

        assertNull(result);
        verify(conversationJpaRepository, never()).delete(any());
    }
}
