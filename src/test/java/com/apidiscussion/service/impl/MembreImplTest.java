package com.apidiscussion.service.impl;

import com.apidiscussion.entity.ConversationEntity;
import com.apidiscussion.entity.MemberEntity;
import com.apidiscussion.entity.RoleEntity;
import com.apidiscussion.repository.ConversationJpaRepository;
import com.apidiscussion.repository.MemberJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MembreImplTest {

    @Mock
    private MemberJpaRepository memberJpaRepository;

    @Mock
    private ConversationJpaRepository conversationJpaRepository;

    @InjectMocks
    private MembreImpl membreService;

    @Test
    void getAllMembers() {
        String conversationId = "conv1";
        MemberEntity m1 = new MemberEntity();
        MemberEntity m2 = new MemberEntity();
        when(memberJpaRepository.findByConversationId(conversationId)).thenReturn(Arrays.asList(m1, m2));

        List<MemberEntity> result = membreService.getAllMembers(conversationId);

        assertEquals(2, result.size());
        verify(memberJpaRepository).findByConversationId(conversationId);
    }

    @Test
    void postMembers() {
        String conversationId = "conv1";
        ConversationEntity conversation = new ConversationEntity();
        MemberEntity m1 = new MemberEntity();
        List<MemberEntity> members = Collections.singletonList(m1);

        when(conversationJpaRepository.findById(conversationId)).thenReturn(Optional.of(conversation));

        ConversationEntity result = membreService.postMembers(conversationId, members);

        assertNotNull(result);
        assertEquals(conversationId, m1.getConversationId());
        verify(memberJpaRepository).save(m1);
        verify(memberJpaRepository).flush();
        verify(conversationJpaRepository, times(2)).findById(conversationId);
    }

    @Test
    void postMembers_ConversationNotFound() {
        String conversationId = "conv1";
        List<MemberEntity> members = Collections.singletonList(new MemberEntity());

        when(conversationJpaRepository.findById(conversationId)).thenReturn(Optional.empty());

        ConversationEntity result = membreService.postMembers(conversationId, members);

        assertNull(result);
        verify(memberJpaRepository, never()).save(any());
    }

    @Test
    void deleteMembers() {
        String conversationId = "conv1";
        String memberId = "mem1";
        List<String> memberIds = Collections.singletonList(memberId);
        
        MemberEntity member = new MemberEntity();
        member.setConversationId(conversationId);
        
        ConversationEntity conversation = new ConversationEntity();

        when(memberJpaRepository.findAllById(memberIds)).thenReturn(Collections.singletonList(member));
        when(conversationJpaRepository.findById(conversationId)).thenReturn(Optional.of(conversation));
        when(memberJpaRepository.findByConversationId(conversationId)).thenReturn(Collections.emptyList());

        ConversationEntity result = membreService.deleteMembers(conversationId, memberIds);

        assertNotNull(result);
        verify(memberJpaRepository).delete(member);
        verify(memberJpaRepository).flush();
        verify(conversationJpaRepository).findById(conversationId);
    }

    @Test
    void getMember() {
        String conversationId = "conv1";
        String memberId = "mem1";
        ConversationEntity conversation = new ConversationEntity();
        
        when(conversationJpaRepository.findById(conversationId)).thenReturn(Optional.of(conversation));

        ConversationEntity result = membreService.getMember(conversationId, memberId);

        assertNotNull(result);
    }

    @Test
    void modifyRoleMember() {
        String conversationId = "conv1";
        String memberId = "mem1";
        RoleEntity newRole = RoleEntity.ADMIN;
        
        MemberEntity member = new MemberEntity();
        member.setConversationId(conversationId);
        
        ConversationEntity conversation = new ConversationEntity();

        when(memberJpaRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(conversationJpaRepository.findById(conversationId)).thenReturn(Optional.of(conversation));

        ConversationEntity result = membreService.ModifyRoleMember(conversationId, memberId, newRole);

        assertNotNull(result);
        assertEquals(newRole, member.getRole());
        verify(memberJpaRepository).save(member);
        verify(memberJpaRepository).flush();
    }

    @Test
    void deleteMember() {
        String conversationId = "conv1";
        String memberId = "mem1";
        
        MemberEntity member = new MemberEntity();
        member.setConversationId(conversationId);
        
        ConversationEntity conversation = new ConversationEntity();

        when(memberJpaRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(conversationJpaRepository.findById(conversationId)).thenReturn(Optional.of(conversation));
        when(memberJpaRepository.findByConversationId(conversationId)).thenReturn(Collections.emptyList());

        ConversationEntity result = membreService.deleteMember(conversationId, memberId);

        assertNotNull(result);
        verify(memberJpaRepository).delete(member);
        verify(memberJpaRepository).flush();
    }
}
