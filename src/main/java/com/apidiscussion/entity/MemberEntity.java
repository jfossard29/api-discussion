package com.apidiscussion.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "member")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "conversation_id")
    private String conversationId;

    private String userId;

    @Enumerated(EnumType.STRING)
    private RoleEntity role;

    private boolean active;
    private int lastJoinDate;
}
