package com.spring.repository;

import com.spring.entities.User;
import com.spring.entities.UserLikePost;
import com.spring.entities.UserPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLikePostRepository extends JpaRepository<UserLikePost, Integer> {
    Optional<UserLikePost> findByUserAndUserPost(User user, UserPost userPost);
    List<UserLikePost> findByUserPost(UserPost userPost);
}
