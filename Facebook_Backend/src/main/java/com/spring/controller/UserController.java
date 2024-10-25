package com.spring.controller;

import com.spring.config.JwtUtil;
import com.spring.dto.Request.User.UserAboutRequest;
import com.spring.dto.Request.User.UserFollowerRequest;
import com.spring.dto.Request.User.UserMessageRequest;
import com.spring.dto.Response.User.*;
import com.spring.entities.User;
import com.spring.service.UserAboutService;
import com.spring.service.UserFollowerService;
import com.spring.service.UserMessageService;
import com.spring.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserAboutService userAboutService;

    @Autowired
    private UserFollowerService userFollowerService;

    @Autowired
    private UserMessageService userMessageService;

    @GetMapping
    public ResponseEntity<String> sayHello(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        return ResponseEntity.ok("Hello, User! Your ID is: " + userId);
    }

    // TODO: Search User
    @GetMapping("/search-user-by-firstname")
    public ResponseEntity<List<SearchUserByNameResponse>> getUsersByFirstName(
            @RequestParam(required = false, name = "firstName") String firstName,
            @RequestParam(required = false, name = "limit", defaultValue = "10") Integer limit,
            @RequestParam(required = false, name = "offset", defaultValue = "0") Integer offset,
            HttpServletRequest request
    ) {
        Integer currentUserId = jwtUtil.getUserIdFromToken(request);
        List<SearchUserByNameResponse> users = userService.getUsersByFirstName(firstName, currentUserId, limit, offset);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search-user-by-lastname")
    public ResponseEntity<List<SearchUserByNameResponse>> getUsersByLastName(
            @RequestParam(required = false, name = "lastName") String lastName,
            @RequestParam(required = false, name = "limit", defaultValue = "10") Integer limit,
            @RequestParam(required = false, name = "offset", defaultValue = "0") Integer offset,
            HttpServletRequest request
    ) {
        Integer currentUserId = jwtUtil.getUserIdFromToken(request);
        List<SearchUserByNameResponse> users = userService.getUsersByLastName(lastName, currentUserId, limit, offset);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search-user-by-username")
    public ResponseEntity<List<SearchUserByUserNameResponse>> getUsersByUserName(
            @RequestParam(required = false, name = "userName") String userName,
            @RequestParam(required = false, name = "limit", defaultValue = "10") Integer limit,
            @RequestParam(required = false, name = "offset", defaultValue = "0") Integer offset,
            HttpServletRequest request
    ) {
        Integer currentUserId = jwtUtil.getUserIdFromToken(request);
        List<SearchUserByUserNameResponse> users = userService.getUsersByUserName(userName, currentUserId, limit, offset);
        return ResponseEntity.ok(users);
    }

    // TODO: UserAbout and Location
    @GetMapping("/listUserAbout")
    public ResponseEntity<List<UserAboutResponse>> getAllUserAbout() {
        List<UserAboutResponse> userAboutList = userAboutService.findAll();
        return ResponseEntity.ok(userAboutList);
    }

    @GetMapping("/userAbout/{id}")
    public ResponseEntity<UserAboutResponse> getUserAboutById(@PathVariable Integer id) {
        UserAboutResponse userAbout = userAboutService.findById(id);
        return ResponseEntity.ok(userAbout);
    }

    @PostMapping("/createUserAbout")
    public ResponseEntity<UserAboutResponse> createUserAbout(@RequestBody UserAboutRequest userAboutRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        UserAboutResponse userAboutResponse = userAboutService.save(userAboutRequest, userId);
        return new ResponseEntity<>(userAboutResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteUserAbout/{id}")
    public ResponseEntity<String> deleteUserAbout(@PathVariable Integer id) {
        userAboutService.deleteById(id);
        return ResponseEntity.ok("Delete UserAbout Successfully");
    }

    // TODO: UserFollower
    @PostMapping("/follow")
    public ResponseEntity<String> followUser(@RequestBody UserFollowerRequest userFollowerRequest, HttpServletRequest request) {
        Integer sourceUserId = jwtUtil.getUserIdFromToken(request);
        userFollowerService.followUser(sourceUserId, userFollowerRequest);
        return ResponseEntity.ok("Successfully followed user with ID: " + userFollowerRequest.getTargetUserId());
    }

    @DeleteMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(@RequestBody UserFollowerRequest userFollowerRequest, HttpServletRequest request) {
        Integer sourceUserId = jwtUtil.getUserIdFromToken(request);
        userFollowerService.unfollowUser(sourceUserId, userFollowerRequest);
        return ResponseEntity.ok("Successfully unfollowed user with ID: " + userFollowerRequest.getTargetUserId());
    }

    @GetMapping("/getFollowing")
    public ResponseEntity<List<UserFollowerResponse>> getFollowingList(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<User> followingList = userFollowerService.getFollowingList(userId);
        List<UserFollowerResponse> followingResponses = followingList.stream()
                .map(user -> UserFollowerResponse.builder()
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .dateOfBirth(user.getDateOfBirth())
                        .gender(user.getGender().toString())
                        .build())
                .toList();
        return ResponseEntity.ok(followingResponses);
    }

    @GetMapping("/getFollowers")
    public ResponseEntity<List<UserFollowerResponse>> getFollowersList(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<User> followersList = userFollowerService.getFollowersList(userId);
        List<UserFollowerResponse> followersResponses = followersList.stream()
                .map(user -> UserFollowerResponse.builder()
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .dateOfBirth(user.getDateOfBirth())
                        .gender(user.getGender().toString())
                        .build())
                .toList();
        return ResponseEntity.ok(followersResponses);
    }

    // TODO: UserMessage
    @PostMapping("/sendMessage")
    public ResponseEntity<UserMessageResponse> sendMessage(
            @RequestBody UserMessageRequest userMessageRequest,
            HttpServletRequest request) {
        Integer sourceId = jwtUtil.getUserIdFromToken(request);
        UserMessageResponse response = userMessageService.sendMessage(sourceId, userMessageRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/editMessage/{messageId}")
    public ResponseEntity<UserMessageResponse> editMessage(
            @PathVariable Integer messageId,
            @RequestBody UserMessageRequest userMessageRequest,
            HttpServletRequest request) {
        Integer sourceId = jwtUtil.getUserIdFromToken(request);
        UserMessageResponse response = userMessageService.editMessage(sourceId, messageId, userMessageRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteMessage/{messageId}")
    public ResponseEntity<String> deleteMessage(
            @PathVariable Integer messageId,
            HttpServletRequest request) {
        Integer sourceId = jwtUtil.getUserIdFromToken(request);
        userMessageService.deleteMessage(sourceId, messageId);
        return ResponseEntity.ok("Message deleted successfully");
    }

    @GetMapping("/messages/source")
    public ResponseEntity<List<UserMessageResponse>> getMessagesBySourceId(HttpServletRequest request) {
        Integer sourceId = jwtUtil.getUserIdFromToken(request);
        List<UserMessageResponse> messages = userMessageService.findBySourceId(sourceId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/messages/target")
    public ResponseEntity<List<UserMessageResponse>> getMessagesByTargetId(@RequestBody UserMessageRequest userMessageRequest) {
        List<UserMessageResponse> messages = userMessageService.findByTargetId(userMessageRequest);
        return ResponseEntity.ok(messages);
    }
}

