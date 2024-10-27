package com.spring.service;

import com.spring.dto.Request.Group.*;
import com.spring.dto.Response.Group.*;
import com.spring.dto.Response.User.UserResponse;

import java.util.List;

public interface GroupPostService {
    GroupPostResponse addPost(Integer userId, GroupPostRequest groupPostRequest);
    GroupPostResponse editPost(Integer userId, GroupPostRequest groupPostRequest);
    void deletePost(GroupPostRequest groupPostRequest);
    GroupPostResponse getPostById(Integer userId, GroupPostRequest groupPostRequest);
    List<GroupPostResponse> getUserPosts(Integer userId);
    List<GroupPostResponse> getPostsByGroupId(Integer userId, GroupPostRequest groupPostRequest);
    Boolean likePost(Integer userId, GroupPostRequest groupPostRequest);
    List<UserResponse> getUsersWhoLikedPost(GroupPostRequest groupPostRequest);
}