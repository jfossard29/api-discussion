package com.apidiscussion.service.impl;

import com.apidiscussion.entity.ConversationEntity;
import com.apidiscussion.entity.MemberEntity;
import com.apidiscussion.entity.RoleEntity;
import com.apidiscussion.repository.ConversationJpaRepository;
import com.apidiscussion.repository.MemberJpaRepository;
import com.apidiscussion.service.MembreRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class MembreImpl implements MembreRepository {

    private final MemberJpaRepository memberJpaRepository;
    private final ConversationJpaRepository conversationJpaRepository;

    public MembreImpl(MemberJpaRepository memberJpaRepository, ConversationJpaRepository conversationJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
        this.conversationJpaRepository = conversationJpaRepository;
    }

    @Override
    public List<MemberEntity> getAllMembers(String id) {
        return memberJpaRepository.findByConversationId(id);
    }

    @Override
    @Transactional
    public ConversationEntity postMembers(String id, List<MemberEntity> members) {
        Optional<ConversationEntity> conversation = conversationJpaRepository.findById(id);
        if (conversation.isPresent()) {
            for (MemberEntity member : members) {
                member.setConversationId(id);
                memberJpaRepository.save(member);
            }
            // Force flush to ensure members are saved before fetching conversation again
            memberJpaRepository.flush();
            // We might need to clear the cache for the conversation to pick up new members if it was already loaded
            // But since we are in a new transaction or same, let's try just fetching.
            // If it doesn't work, we might need to refresh the entity.
            return conversationJpaRepository.findById(id).orElse(null);
        }
        return null;
    }

    @Override
    @Transactional
    public ConversationEntity deleteMembers(String id, List<String> membersId) {
        List<MemberEntity> members = memberJpaRepository.findAllById(membersId);
        for (MemberEntity member : members) {
            if (member.getConversationId().equals(id)) {
                memberJpaRepository.delete(member);
            }
        }
        memberJpaRepository.flush();
        
        // Fetch the conversation again. 
        // Note: Since ConversationEntity has @OneToMany with EAGER fetch, 
        // Hibernate might return the cached instance with the old list of members.
        // To fix this properly without EntityManager.refresh(), we can manually filter the list 
        // or rely on a new transaction context.
        // Here, we will fetch the conversation and manually ensure the list is up to date 
        // by fetching members directly if needed, but let's trust the flush first.
        
        ConversationEntity conversation = conversationJpaRepository.findById(id).orElse(null);
        if (conversation != null) {
            // Force refresh of members list by fetching them directly
            List<MemberEntity> currentMembers = memberJpaRepository.findByConversationId(id);
            conversation.setMembers(currentMembers);
        }
        return conversation;
    }

    @Override
    public ConversationEntity getMember(String id, String memberId) {
        return conversationJpaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public ConversationEntity ModifyRoleMember(String id, String memberId, RoleEntity role) {
        Optional<MemberEntity> memberOpt = memberJpaRepository.findById(memberId);
        if (memberOpt.isPresent()) {
            MemberEntity member = memberOpt.get();
            if (member.getConversationId().equals(id)) {
                member.setRole(role);
                memberJpaRepository.save(member);
            }
        }
        memberJpaRepository.flush();
        return conversationJpaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public ConversationEntity deleteMember(String id, String memberId) {
        Optional<MemberEntity> memberOpt = memberJpaRepository.findById(memberId);
        if (memberOpt.isPresent()) {
            MemberEntity member = memberOpt.get();
            if (member.getConversationId().equals(id)) {
                memberJpaRepository.delete(member);
            }
        }
        memberJpaRepository.flush();
        
        ConversationEntity conversation = conversationJpaRepository.findById(id).orElse(null);
        if (conversation != null) {
             List<MemberEntity> currentMembers = memberJpaRepository.findByConversationId(id);
             conversation.setMembers(currentMembers);
        }
        return conversation;
    }
}
