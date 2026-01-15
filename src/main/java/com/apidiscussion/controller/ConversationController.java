package com.apidiscussion.controller;

import com.apidiscussion.business.ConversationBusiness;
import com.apidiscussion.entity.ConversationEntity;
import com.apidiscussion.entity.MemberEntity;
import com.apidiscussion.mapper.ConversationMapper;
import com.apidiscussion.mapper.MembreMapper;
import dto.Conversation.Conversation;
import dto.Conversation.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/conversation")
public class ConversationController {

    private final ConversationBusiness conversationBusiness;
    private final ConversationMapper conversationMapper;
    private final MembreMapper membreMapper;

    public ConversationController(ConversationBusiness conversationBusiness) {
        this.conversationBusiness = conversationBusiness;
        this.conversationMapper = new ConversationMapper();
        this.membreMapper = new MembreMapper();
    }

    @GetMapping
    public ResponseEntity<List<Conversation>> getAllConversations() {
        List<ConversationEntity> conversationEntities = conversationBusiness.getAllConversations();
        List<Conversation> conversations = conversationEntities.stream()
                .map(conversationMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(conversations);
    }

    @GetMapping("/{conversationId}")
    public ResponseEntity<Conversation> getConversationById(@PathVariable String conversationId) {
        ConversationEntity conversationEntity = conversationBusiness.getConversationById(conversationId);
        if (conversationEntity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(conversationMapper.toDto(conversationEntity));
    }

    @PostMapping
    public ResponseEntity<Conversation> createConversation(@RequestBody String name) {
        conversationBusiness.createConversation(name);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{conversationId}")
    public ResponseEntity<Conversation> updateConversation(
            @PathVariable String conversationId,
            @RequestBody Conversation conversation) {
        ConversationEntity updatedEntity = conversationBusiness.updateName(conversationId, conversation.getName());
        if (updatedEntity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(conversationMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{conversationId}")
    public ResponseEntity<Void> deleteConversation(@PathVariable String conversationId) {
        conversationBusiness.deleteConversation(conversationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{conversationId}/member")
    public ResponseEntity<List<Member>> getAllMembers(@PathVariable String conversationId) {
        List<MemberEntity> memberEntities = conversationBusiness.getAllMembers(conversationId);
        List<Member> members = memberEntities.stream()
                .map(membreMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(members);
    }

}
