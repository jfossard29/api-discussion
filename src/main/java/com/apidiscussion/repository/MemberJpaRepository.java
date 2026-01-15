package com.apidiscussion.repository;

import com.apidiscussion.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberJpaRepository extends JpaRepository<MemberEntity, String> {
    List<MemberEntity> findByConversationId(String conversationId);
}
