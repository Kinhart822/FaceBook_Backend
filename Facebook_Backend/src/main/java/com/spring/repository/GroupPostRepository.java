package com.spring.repository;

import com.spring.entities.GroupPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupPostRepository extends JpaRepository<GroupPost, Integer> {
    List<GroupPost> findByUserId(Integer userId);
    List<GroupPost> findByGroupId(Integer groupId);

}
