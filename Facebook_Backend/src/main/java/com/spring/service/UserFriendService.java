package com.spring.service;

import com.spring.dto.Request.User.UserFriendRequest;
import com.spring.dto.Response.User.UserFriendResponse;
import com.spring.enums.FriendRequestStatus;

import java.util.List;

public interface UserFriendService {
    void sendFriendRequest(Integer sourceId, UserFriendRequest userFriendRequest);
    void acceptFriendRequest(Integer sourceId, Integer targetId);
    void declineFriendRequest(Integer sourceId,  Integer targetId);
    void removeFriend(Integer sourceId, UserFriendRequest userFriendRequest);
    List<UserFriendResponse> getStrangers(Integer userId);
    List<UserFriendResponse> getPendingFriendRequests(Integer targetId, FriendRequestStatus friendRequestStatus);
    List<UserFriendResponse> getAcceptedFriendRequests(Integer targetId, FriendRequestStatus friendRequestStatus);
}
