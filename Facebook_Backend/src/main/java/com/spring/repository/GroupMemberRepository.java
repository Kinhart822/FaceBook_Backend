package com.spring.repository;

import com.spring.entities.GroupMember;
import com.spring.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Integer> {
    List<GroupMember> findByGroupId(Integer groupId);
    List<GroupMember> findByRole(Role role);
    boolean existsByGroupIdAndUserId(Integer groupId, Integer userId);
}

