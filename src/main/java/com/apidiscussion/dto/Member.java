package com.apidiscussion.dto;

import lombok.Data;

@Data
public class Member {
    private String id;
    private String conversationId;
    private String userId;
    private Role role;
    private boolean active;
    private int lastJoinDate;
}
