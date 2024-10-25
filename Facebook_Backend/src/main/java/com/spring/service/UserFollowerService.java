package com.spring.service;

import com.spring.dto.Request.User.UserFollowerRequest;
import com.spring.entities.User;

import java.util.List;

public interface UserFollowerService {
    void followUser(Integer sourceUserId, UserFollowerRequest userFollowerRequest);
    void unfollowUser(Integer sourceUserId, UserFollowerRequest userFollowerRequest);
    List<User> getFollowingList(Integer userId);
    List<User> getFollowersList(Integer userId);
}
