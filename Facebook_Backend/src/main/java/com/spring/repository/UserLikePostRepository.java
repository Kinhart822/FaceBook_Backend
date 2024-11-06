package com.spring.repository;

import com.spring.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLikePostRepository extends JpaRepository<UserLikePost, Integer> {
    Optional<UserLikePost> findByUserAndUserPost(User user, UserPost userPost);
    List<UserLikePost> findByUserPost(UserPost userPost);
    Optional<UserLikePost> findByUserAndGroupPost(User user, GroupPost groupPost);
    List<UserLikePost> findByGroupPost(GroupPost groupPost);
    Optional<UserLikePost> findByUserAndVideoPost(User user, VideoPost videoPost);
    List<UserLikePost> findByVideoPost(VideoPost videoPost);
}
