package com.spring.service;

import com.spring.dto.Request.User.VideoPostRequest;
import com.spring.dto.Response.User.UserResponse;
import com.spring.dto.Response.User.VideoPostResponse;

import java.util.List;

public interface VideoPostService {
    VideoPostResponse addPost(Integer userId, VideoPostRequest videoPostRequest);
    VideoPostResponse editPost(Integer userId, VideoPostRequest videoPostRequest);
    void deletePost(VideoPostRequest videoPostRequest);
    VideoPostResponse getPostById(Integer userId, VideoPostRequest videoPostRequest);
    List<VideoPostResponse> getVideoPosts(Integer userId);
    Boolean likePost(Integer userId, VideoPostRequest videoPostRequest);
    List<UserResponse> getUsersWhoLikedPost(VideoPostRequest videoPostRequest);
    String getVideoUrlByVideoPostAndPostStatus(VideoPostRequest videoPostRequest);
}