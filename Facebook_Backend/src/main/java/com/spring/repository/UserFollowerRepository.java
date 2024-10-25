package com.spring.repository;

import com.spring.entities.User;
import com.spring.entities.UserFollower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserFollowerRepository extends JpaRepository<UserFollower, Integer> {
    boolean existsBySourceUserAndTargetUser(User sourceUser, User targetUser);
    Optional<UserFollower> findBySourceUserAndTargetUser(User sourceUser, User targetUser);
    List<UserFollower> findAllBySourceUserId(Integer sourceUserId);
    List<UserFollower> findAllByTargetUserId(Integer targetUserId);
}
