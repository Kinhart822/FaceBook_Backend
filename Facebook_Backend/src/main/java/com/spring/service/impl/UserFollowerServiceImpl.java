package com.spring.service.impl;

import com.spring.dto.Request.User.UserFollowerRequest;
import com.spring.entities.User;
import com.spring.entities.UserFollower;
import com.spring.enums.FollowerStatus;
import com.spring.repository.UserFollowerRepository;
import com.spring.repository.UserRepository;
import com.spring.service.UserFollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserFollowerServiceImpl implements UserFollowerService {
    @Autowired
    private UserFollowerRepository userFollowerRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void followUser(Integer sourceUserId, UserFollowerRequest userFollowerRequest) {
        User sourceUser = userRepository.findById(sourceUserId)
                .orElseThrow(() -> new IllegalArgumentException("Source user not found"));
        User targetUser = userRepository.findById(userFollowerRequest.getTargetUserId())
                .orElseThrow(() -> new IllegalArgumentException("Target user not found"));

        if (userFollowerRepository.existsBySourceUserAndTargetUser(sourceUser, targetUser)) {
            throw new IllegalStateException("User is already following this target user");
        }

        UserFollower userFollower = new UserFollower();
        userFollower.setSourceUser(sourceUser);
        userFollower.setTargetUser(targetUser);
        userFollower.setStatus(FollowerStatus.Followed);
        userFollower.setDateCreated(new Date());
        userFollower.setDateUpdated(new Date());

        userFollowerRepository.save(userFollower);
    }

    @Override
    public void unfollowUser(Integer sourceUserId, UserFollowerRequest userFollowerRequest) {
        User sourceUser = userRepository.findById(sourceUserId)
                .orElseThrow(() -> new IllegalArgumentException("Source user not found"));
        User targetUser = userRepository.findById(userFollowerRequest.getTargetUserId())
                .orElseThrow(() -> new IllegalArgumentException("Target user not found"));

        UserFollower userFollower = userFollowerRepository.findBySourceUserAndTargetUser(sourceUser, targetUser)
                .orElseThrow(() -> new IllegalArgumentException("User is not following this target user"));
        userFollower.setStatus(FollowerStatus.Unfollowed);
        userFollower.setDateUpdated(new Date());
        userFollowerRepository.save(userFollower);
    }

    @Override
    public List<User> getFollowingList(Integer userId) {
        return userFollowerRepository.findAllBySourceUserId(userId).stream()
                .map(UserFollower::getTargetUser)
                .toList();
    }

    @Override
    public List<User> getFollowersList(Integer userId) {
        return userFollowerRepository.findAllByTargetUserId(userId).stream()
                .map(UserFollower::getSourceUser)
                .toList();
    }
}
