package com.apidiscussion.business;

import com.apidiscussion.entity.ConversationEntity;
import com.apidiscussion.entity.MemberEntity;
import com.apidiscussion.entity.RoleEntity;
import com.apidiscussion.repository.ConversationRepository;
import com.apidiscussion.repository.MembreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationBusiness {

    private final ConversationRepository conversationRepository;
    private final MembreRepository membreRepository;
    public ConversationBusiness(ConversationRepository conversationRepository, MembreRepository membreRepository) {
        this.conversationRepository = conversationRepository;
        this.membreRepository = membreRepository;
    }

    public List<ConversationEntity> getAllConversations() {
        return conversationRepository.getAllConversations();
    }

    public ConversationEntity getConversationById(String id) {
        return conversationRepository.findById(id);
    }

    public void createConversation(String name) {
        conversationRepository.createConversation(name);
    }

    public ConversationEntity updateName(String id, String name) {
        return conversationRepository.updateConversationName(id,name);
    }

    public void deleteConversation(String id) {
        conversationRepository.deleteConversation(id);
    }

    public List<MemberEntity> getAllMembers(String id) {
        return membreRepository.getAllMembers(id);
    }

    public ConversationEntity postMembers(String id, List<MemberEntity> members) {
        return membreRepository.postMembers(id, members);
    }

    public ConversationEntity deleteMembers(String id, List<String> membersId) {
        return membreRepository.deleteMembers(id, membersId);
    }

    public ConversationEntity getMember(String id, String memberId) {
        return membreRepository.getMember(id, memberId);
    }

    public ConversationEntity modifyRoleMember(String id, String memberId, RoleEntity role) {
        return membreRepository.ModifyRoleMember(id, memberId, role);
    }

    public ConversationEntity deleteMember(String id, String memberId) {
        return membreRepository.deleteMember(id, memberId);
    }
}
