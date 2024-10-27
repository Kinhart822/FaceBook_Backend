package com.spring.repository;

import com.spring.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByUserId(Integer userId);
    List<Comment> findByUserPostId(Integer postId);
    List<Comment> findByGroupPostId(Integer postId);
}
