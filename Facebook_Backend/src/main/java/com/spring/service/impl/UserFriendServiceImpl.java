package com.spring.service.impl;

import com.spring.dto.Request.User.UserFriendRequest;
import com.spring.dto.response.User.UserFriendResponse;
import com.spring.dto.response.UserProjectionNew;
import com.spring.entities.User;
import com.spring.entities.UserFriend;
import com.spring.enums.FriendRequestStatus;
import com.spring.repository.UserFriendRepository;
import com.spring.repository.UserRepository;
import com.spring.service.UserFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserFriendServiceImpl implements UserFriendService {
    @Autowired
    private UserFriendRepository userFriendRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void sendFriendRequest(Integer sourceId, UserFriendRequest userFriendRequest) {
        if (sourceId.equals(userFriendRequest.getTargetUserId())) {
            throw new IllegalArgumentException("You cannot send a friend request to yourself");
        }

        User sourceUser = userRepository.findById(sourceId)
                .orElseThrow(() -> new IllegalArgumentException("Source user not found"));
        User targetUser = userRepository.findById(userFriendRequest.getTargetUserId())
                .orElseThrow(() -> new IllegalArgumentException("Target user not found"));

        Optional<UserFriend> existingRequest = userFriendRepository.findBySourceUserIdAndTargetUserId(sourceId, userFriendRequest.getTargetUserId());
        Optional<UserFriend> reverseRequest = userFriendRepository.findBySourceUserIdAndTargetUserId(userFriendRequest.getTargetUserId(), sourceId);

        if (existingRequest.isPresent()) {
            FriendRequestStatus status = existingRequest.get().getStatus();
            if (status == FriendRequestStatus.PENDING) {
                throw new IllegalArgumentException("Friend request already sent");
            } else if (status == FriendRequestStatus.ACCEPTED) {
                throw new IllegalArgumentException("You two have already been friends");
            } else if (status == FriendRequestStatus.DECLINED) {
                userFriendRepository.delete(existingRequest.get());
            }
        }

        if (reverseRequest.isPresent()) {
            FriendRequestStatus reverseStatus = reverseRequest.get().getStatus();
            if (reverseStatus == FriendRequestStatus.PENDING) {
                throw new IllegalArgumentException("The user has already sent you a friend request, you can only accept or decline");
            } else if (reverseStatus == FriendRequestStatus.ACCEPTED) {
                throw new IllegalArgumentException("You two have already been friends");
            } else if (reverseStatus == FriendRequestStatus.DECLINED) {
                userFriendRepository.delete(reverseRequest.get());
            }
        }

        UserFriend friendRequest = new UserFriend();
        friendRequest.setSourceUser(sourceUser);
        friendRequest.setTargetUser(targetUser);
        friendRequest.setStatus(FriendRequestStatus.PENDING);
        friendRequest.setDateCreated(new Date());
        friendRequest.setDateUpdated(new Date());

        userFriendRepository.save(friendRequest);
    }

    @Override
    public void acceptFriendRequest(Integer sourceId, Integer targetId) {
        UserFriend friendRequest = userFriendRepository.findBySourceUserIdAndTargetUserId(sourceId, targetId)
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found"));

        if (friendRequest.getStatus() != FriendRequestStatus.PENDING) {
            throw new IllegalArgumentException("No pending friend request to accept");
        }

        friendRequest.setStatus(FriendRequestStatus.ACCEPTED);
        friendRequest.setDateUpdated(new Date());

        userFriendRepository.save(friendRequest);
    }

    @Override
    public void declineFriendRequest(Integer sourceId, Integer targetId) {
        UserFriend friendRequest = userFriendRepository.findBySourceUserIdAndTargetUserId(sourceId, targetId)
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found"));

        if (friendRequest.getStatus() != FriendRequestStatus.PENDING) {
            throw new IllegalArgumentException("No pending friend request to decline");
        }

        friendRequest.setStatus(FriendRequestStatus.DECLINED);
        friendRequest.setDateUpdated(new Date());
        userFriendRepository.save(friendRequest);
    }


    @Override
    public void removeFriend(Integer sourceId, UserFriendRequest userFriendRequest) {
        UserFriend friendship = userFriendRepository.findBySourceUserIdAndTargetUserIdAndStatus(sourceId, userFriendRequest.getTargetUserId(), FriendRequestStatus.ACCEPTED)
                .orElseThrow(() -> new IllegalArgumentException("Friendship not found"));
        userFriendRepository.delete(friendship);
    }

    @Override
    public List<UserProjectionNew> getStrangers(Integer userId, String name) {
        return userFriendRepository.findStrangers(userId, name);
    }


    @Override
    public List<UserFriendResponse> getPendingFriendRequests(Integer targetId, FriendRequestStatus friendRequestStatus) {
        List<UserFriend> pendingRequests = userFriendRepository.findByTargetUserIdAndFriendRequestStatus(targetId, FriendRequestStatus.PENDING);
        return pendingRequests.stream()
                .map(friend -> UserFriendResponse.builder()
                        .sourceUserName(friend.getSourceUser().getLastName() + " " + friend.getSourceUser().getFirstName())
                        .targetUserName(friend.getTargetUser().getLastName() + " " + friend.getSourceUser().getFirstName())
                        .friendRequestStatus(friend.getStatus().toString())
                        .build())
                .toList();
    }

    @Override
    public List<UserFriendResponse> getAcceptedFriendRequests(Integer targetId, FriendRequestStatus friendRequestStatus) {
        List<UserFriend> acceptedRequests = userFriendRepository.findByTargetUserIdAndFriendRequestStatus(targetId, FriendRequestStatus.ACCEPTED);
        return acceptedRequests.stream()
                .map(friend -> UserFriendResponse.builder()
                        .sourceUserName(friend.getSourceUser().getLastName() + " " + friend.getSourceUser().getFirstName())
                        .targetUserName(friend.getTargetUser().getLastName() + " " + friend.getSourceUser().getFirstName())
                        .friendRequestStatus(friend.getStatus().toString())
                        .build())
                .toList();
    }

}
