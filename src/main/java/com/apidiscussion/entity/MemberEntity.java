package com.apidiscussion.entity;

import lombok.Data;

@Data
public class MemberEntity {
    String id;
    String conversationId;
    String userId;
    RoleEntity role;
    boolean active;
    int lastJoinDate;
}
