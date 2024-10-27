package com.spring.repository;

import com.spring.entities.GroupMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupMessageRepository extends JpaRepository<GroupMessage, Integer> {
    List<GroupMessage> findByGroupId(Integer groupId);

    @Query("SELECT gm FROM GroupMessage gm WHERE gm.sourceUser.id = :userId")
    List<GroupMessage> findBySourceId(Integer userId);

    @Query("SELECT gm FROM GroupMessage gm WHERE gm.targetUser.id = :userId")
    List<GroupMessage> findByTargetId(Integer userId);
}
