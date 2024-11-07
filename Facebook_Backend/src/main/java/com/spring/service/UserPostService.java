package com.spring.service;

import com.spring.dto.Request.User.PostRequest;
import com.spring.dto.Response.User.PostResponse;
import com.spring.dto.Response.User.UserResponse;

import java.util.List;

public interface UserPostService {
    PostResponse addPost(Integer userId, PostRequest postRequest);
    PostResponse editPost(Integer userId, PostRequest postRequest);
    void deletePost(Integer userId, PostRequest postRequest);
    PostResponse getPostById(Integer userId, PostRequest postRequest);
    List<PostResponse> getUserPosts(Integer userId);
    Boolean likePost(Integer userId, PostRequest postRequest);
    List<UserResponse> getUsersWhoLikedPost(PostRequest postRequest);
    List<String> getAllImageUrlByUserPostAndPostStatus(PostRequest postRequest);
}
