package com.spring.repository;

import com.spring.entities.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserMessageRepository extends JpaRepository<UserMessage, Integer> {
    @Query("SELECT um FROM UserMessage um WHERE um.sourceUser.id = :userId")
    List<UserMessage> findBySourceId(Integer userId);

    @Query("SELECT um FROM UserMessage um WHERE um.targetUser.id = :userId")
    List<UserMessage> findByTargetId(Integer userId);
}
