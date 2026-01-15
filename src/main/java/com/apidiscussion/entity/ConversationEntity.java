package com.apidiscussion.entity;

import lombok.Data;

import java.util.List;

@Data
public class ConversationEntity {
    String id;
    String name;
    List<MemberEntity> members;
}
