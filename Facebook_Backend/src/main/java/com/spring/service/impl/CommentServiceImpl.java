package com.spring.service.impl;

import com.spring.dto.Request.User.CommentRequest;
import com.spring.dto.response.User.CommentResponse;
import com.spring.entities.*;
import com.spring.repository.*;
import com.spring.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserPostRepository userPostRepository;

    @Autowired
    private GroupPostRepository groupPostRepository;

    @Autowired
    private VideoPostRepository videoPostRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CommentResponse addComment(Integer userId, CommentRequest commentRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        UserPost userPost = userPostRepository.findById(commentRequest.getUserPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Comment comment = Comment.builder()
                .user(user)
                .content(commentRequest.getContent())
                .userPost(userPost)
                .dateCreated(new Date())
                .dateUpdated(new Date())
                .build();

        commentRepository.save(comment);

        return CommentResponse.builder()
                .name(user.getLastName() + " " + user.getFirstName())
                .content(comment.getContent())
                .build();
    }
    @Override
    public CommentResponse addVideoComment(Integer userId, CommentRequest commentRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        VideoPost videoPost = videoPostRepository.findById(commentRequest.getVideoPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Comment comment = Comment.builder()
                .user(user)
                .content(commentRequest.getContent())
                .videoPost(videoPost)
                .dateCreated(new Date())
                .dateUpdated(new Date())
                .build();

        commentRepository.save(comment);

        return CommentResponse.builder()
                .name(user.getLastName() + " " + user.getFirstName())
                .content(comment.getContent())
                .build();
    }

    @Override
    public CommentResponse addGroupComment(Integer userId, CommentRequest commentRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        GroupPost groupPost = groupPostRepository.findById(commentRequest.getGroupPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Comment comment = Comment.builder()
                .user(user)
                .content(commentRequest.getContent())
                .groupPost(groupPost)
                .dateCreated(new Date())
                .dateUpdated(new Date())
                .build();

        commentRepository.save(comment);

        return CommentResponse.builder()
                .name(user.getLastName() + " " + user.getFirstName())
                .content(comment.getContent())
                .build();
    }


    @Override
    public CommentResponse editComment(Integer userId, CommentRequest commentRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Comment comment = commentRepository.findById(commentRequest.getCommentId())
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        if (!comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("User not authorized to edit this comment");
        }

        comment.setContent(commentRequest.getContent());
        commentRepository.save(comment);

        return CommentResponse.builder()
                .name(user.getLastName() + " " + user.getFirstName())
                .content(comment.getContent())
                .build();
    }

    @Override
    public void deleteComment(Integer commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentResponse> getCommentsByUserId(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Comment> comments = commentRepository.findByUserId(userId);
        return comments.stream()
                .map(comment -> CommentResponse.builder()
                        .name(user.getLastName() + " " + user.getFirstName())
                        .content(comment.getContent())
                        .build())
                .toList();
    }

    @Override
    public List<CommentResponse> getCommentsByUserPostId(CommentRequest commentRequest) {
        List<Comment> comments = commentRepository.findByUserPostId(commentRequest.getUserPostId());
        return comments.stream()
                .map(comment -> CommentResponse.builder()
                        .name(comment.getUser().getLastName() + " " + comment.getUser().getFirstName())
                        .content(comment.getContent())
                        .build())
                .toList();
    }

    @Override
    public List<CommentResponse> getCommentsByGroupPostId(CommentRequest commentRequest) {
        List<Comment> comments = commentRepository.findByGroupPostId(commentRequest.getGroupPostId());
        return comments.stream()
                .map(comment -> CommentResponse.builder()
                        .name(comment.getUser().getLastName() + " " + comment.getUser().getFirstName())
                        .content(comment.getContent())
                        .build())
                .toList();
    }

    @Override
    public List<CommentResponse> getCommentsByVideoPostId(CommentRequest commentRequest) {
        List<Comment> comments = commentRepository.findByVideoPostId(commentRequest.getVideoPostId());
        return comments.stream()
                .map(comment -> CommentResponse.builder()
                        .name(comment.getUser().getLastName() + " " + comment.getUser().getFirstName())
                        .content(comment.getContent())
                        .build())
                .toList();
    }
}
