package com.apidiscussion.business;

import com.apidiscussion.entity.ConversationEntity;
import com.apidiscussion.entity.MemberEntity;
import com.apidiscussion.entity.RoleEntity;
import com.apidiscussion.service.ConversationRepository;
import com.apidiscussion.service.MembreRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
        return conversationRepository.updateConversationName(id, name);
    }

    public void deleteConversation(String id) {
        conversationRepository.deleteConversation(id);
    }

    public List<MemberEntity> getAllMembers(String id) {
        return membreRepository.getAllMembers(id);
    }

    public List<MemberEntity> postMembers(String id, List<MemberEntity> members) {
        ConversationEntity conversation = membreRepository.postMembers(id, members);
        if (conversation != null) {
            return conversation.getMembers();
        }
        return Collections.emptyList();
    }

    public List<MemberEntity> deleteMembers(String id, List<String> membersId) {
        ConversationEntity conversation = membreRepository.deleteMembers(id, membersId);
        if (conversation != null) {
            return conversation.getMembers();
        }
        return Collections.emptyList();
    }

    public MemberEntity getMember(String id, String memberId) {
        ConversationEntity conversation = membreRepository.getMember(id, memberId);
        if (conversation != null && conversation.getMembers() != null) {
            return conversation.getMembers().stream()
                    .filter(m -> m.getId().equals(memberId))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public MemberEntity modifyRoleMember(String id, String memberId, RoleEntity role) {
        ConversationEntity conversation = membreRepository.ModifyRoleMember(id, memberId, role);
        if (conversation != null && conversation.getMembers() != null) {
            return conversation.getMembers().stream()
                    .filter(m -> m.getId().equals(memberId))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public MemberEntity deleteMember(String id, String memberId) {
        // On récupère le membre avant de le supprimer pour pouvoir le retourner
        MemberEntity memberToDelete = getMember(id, memberId);
        
        if (memberToDelete != null) {
            membreRepository.deleteMember(id, memberId);
            return memberToDelete;
        }
        return null;
    }
}
