package com.apidiscussion.controller;

import com.apidiscussion.business.ConversationBusiness;
import com.apidiscussion.entity.ConversationEntity;
import com.apidiscussion.entity.MemberEntity;
import com.apidiscussion.entity.RoleEntity;
import com.apidiscussion.mapper.ConversationMapper;
import com.apidiscussion.mapper.MembreMapper;
import com.apidiscussion.dto.Conversation;
import com.apidiscussion.dto.Member;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Path("/conversation")
public class ConversationController {

    private final ConversationBusiness conversationBusiness;
    private final ConversationMapper conversationMapper;
    private final MembreMapper membreMapper;

    public ConversationController(ConversationBusiness conversationBusiness) {
        this.conversationBusiness = conversationBusiness;
        this.conversationMapper = new ConversationMapper();
        this.membreMapper = new MembreMapper();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllConversations() {
        List<ConversationEntity> conversationEntities = conversationBusiness.getAllConversations();
        List<Conversation> conversations = conversationEntities.stream()
                .map(conversationMapper::toDto)
                .collect(Collectors.toList());
        return Response.ok(conversations).build();
    }

    @GET
    @Path("/{conversationId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConversationById(@PathParam("conversationId") String conversationId) {
        ConversationEntity conversationEntity = conversationBusiness.getConversationById(conversationId);
        if (conversationEntity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(conversationMapper.toDto(conversationEntity)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createConversation(Conversation conversation) {
        conversationBusiness.createConversation(conversation.getName());
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{conversationId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateConversation(
            @PathParam("conversationId") String conversationId,
            Conversation conversation) {
        ConversationEntity updatedEntity = conversationBusiness.updateName(conversationId, conversation.getName());
        if (updatedEntity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(conversationMapper.toDto(updatedEntity)).build();
    }

    @DELETE
    @Path("/{conversationId}")
    public Response deleteConversation(@PathParam("conversationId") String conversationId) {
        conversationBusiness.deleteConversation(conversationId);
        return Response.noContent().build();
    }

    @GET
    @Path("/{conversationId}/member")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMembers(@PathParam("conversationId") String conversationId) {
        List<MemberEntity> memberEntities = conversationBusiness.getAllMembers(conversationId);
        List<Member> members = memberEntities.stream()
                .map(membreMapper::toDto)
                .collect(Collectors.toList());
        return Response.ok(members).build();
    }

    @POST
    @Path("/{conversationId}/member")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMembers(@PathParam("conversationId") String conversationId, List<Member> members) {
        List<MemberEntity> memberEntities = members.stream()
                .map(membreMapper::toEntity)
                .collect(Collectors.toList());
        
        List<MemberEntity> updatedMembersEntities = conversationBusiness.postMembers(conversationId, memberEntities);
        
        if (updatedMembersEntities.isEmpty() && !members.isEmpty()) {
             return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<Member> updatedMembers = updatedMembersEntities.stream()
                .map(membreMapper::toDto)
                .collect(Collectors.toList());
        return Response.status(Response.Status.CREATED).entity(updatedMembers).build();
    }

    @DELETE
    @Path("/{conversationId}/member")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response kickMembers(@PathParam("conversationId") String conversationId, List<String> ids) {
        List<MemberEntity> updatedMembersEntities = conversationBusiness.deleteMembers(conversationId, ids);
        List<Member> updatedMembers = updatedMembersEntities.stream()
                .map(membreMapper::toDto)
                .collect(Collectors.toList());
        return Response.ok(updatedMembers).build();
    }

    @GET
    @Path("/{conversationId}/member/{memberId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMember(@PathParam("conversationId") String conversationId, @PathParam("memberId") String memberId) {
        MemberEntity memberEntity = conversationBusiness.getMember(conversationId, memberId);
        if (memberEntity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(membreMapper.toDto(memberEntity)).build();
    }

    @PUT
    @Path("/{conversationId}/member/{memberId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMember(@PathParam("conversationId") String conversationId, @PathParam("memberId") String memberId, Member member) {
        RoleEntity roleEntity = RoleEntity.valueOf(member.getRole().name());
        MemberEntity updatedMemberEntity = conversationBusiness.modifyRoleMember(conversationId, memberId, roleEntity);
        
        if (updatedMemberEntity == null) {
             return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(membreMapper.toDto(updatedMemberEntity)).build();
    }

    @DELETE
    @Path("/{conversationId}/member/{memberId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response kickMember(@PathParam("conversationId") String conversationId, @PathParam("memberId") String memberId) {
        MemberEntity deletedMember = conversationBusiness.deleteMember(conversationId, memberId);
        
        if (deletedMember == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(membreMapper.toDto(deletedMember)).build();
    }
}
