package com.spring.repository;

import com.spring.entities.UserPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPostRepository extends JpaRepository<UserPost, Integer> {
    List<UserPost> findByUserId(Integer userId);
}
