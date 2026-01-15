package com.apidiscussion.repository;

import com.apidiscussion.entity.ConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationJpaRepository extends JpaRepository<ConversationEntity, String> {
}
