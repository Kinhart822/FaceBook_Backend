package com.spring.service;

import com.spring.dto.Request.User.CommentRequest;
import com.spring.dto.Response.User.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentResponse addComment(Integer userId, CommentRequest commentRequest);
    CommentResponse addGroupComment(Integer userId, CommentRequest commentRequest);
    CommentResponse addVideoComment(Integer userId, CommentRequest commentRequest);

    CommentResponse editComment(Integer userId, CommentRequest commentRequest);
    void deleteComment(Integer commentId);

    List<CommentResponse> getCommentsByUserId(Integer userId);
    List<CommentResponse> getCommentsByUserPostId(CommentRequest commentRequest);
    List<CommentResponse> getCommentsByGroupPostId(CommentRequest commentRequest);
    List<CommentResponse> getCommentsByVideoPostId(CommentRequest commentRequest);
}
