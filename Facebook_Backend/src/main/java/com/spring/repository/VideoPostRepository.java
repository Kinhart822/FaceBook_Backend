package com.spring.repository;

import com.spring.entities.User;
import com.spring.entities.VideoPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoPostRepository extends JpaRepository<VideoPost, Integer> {
    List<VideoPost> findByUser(User user);
}
