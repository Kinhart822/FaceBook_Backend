package com.spring.service.impl;

import com.spring.dto.Request.User.PostRequest;
import com.spring.dto.Response.User.PostResponse;
import com.spring.dto.Response.User.UserAboutResponse;
import com.spring.dto.Response.User.UserResponse;
import com.spring.entities.*;
import com.spring.enums.ActionPerformed;
import com.spring.enums.PostStatus;
import com.spring.repository.UserLikePostRepository;
import com.spring.repository.UserPostRepository;
import com.spring.repository.UserRepository;
import com.spring.service.NotificationService;
import com.spring.service.UserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserPostServiceImpl implements UserPostService {
    @Autowired
    private UserPostRepository userPostRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLikePostRepository userLikePostRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public PostResponse addPost(Integer userId, PostRequest postRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        UserPost newPost = new UserPost();
        newPost.setUser(user);
        newPost.setContent(postRequest.getContent());
        newPost.setDateCreated(new Date());
        newPost.setDateUpdated(new Date());
        newPost.setPostStatus(postRequest.getPostStatus());
        if (postRequest.getPostStatus() == PostStatus.Draft){
            newPost.setActionPerformed(ActionPerformed.CreatedDraftPost);
        } else if (postRequest.getPostStatus() == PostStatus.Published){
            newPost.setActionPerformed(ActionPerformed.CreatedPost);
        }
        userPostRepository.save(newPost);

        return PostResponse.builder()
                .name(user.getLastName() + " " + user.getFirstName())
                .content(postRequest.getContent())
                .postStatus(postRequest.getPostStatus().toString())
                .actionPerformed(newPost.getActionPerformed().toString())
                .build();
    }

    @Override
    public PostResponse editPost(Integer userId, PostRequest postRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        UserPost existingPost = userPostRepository.findById(postRequest.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!existingPost.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Unauthorized edit attempt");
        }

        existingPost.setContent(postRequest.getContent());
        existingPost.setDateUpdated(new Date());
        if (postRequest.getPostStatus() == PostStatus.Draft){
            existingPost.setActionPerformed(ActionPerformed.UpdatedDraftPost);
        } else if (postRequest.getPostStatus() == PostStatus.Published){
            existingPost.setActionPerformed(ActionPerformed.UpdatedPost);
        }
        userPostRepository.save(existingPost);

        return PostResponse.builder()
                .name(user.getLastName() + " " + user.getFirstName())
                .content(postRequest.getContent())
                .postStatus(postRequest.getPostStatus().toString())
                .actionPerformed(existingPost.getActionPerformed().toString())
                .build();
    }

    @Override
    public void deletePost(PostRequest postRequest) {
        UserPost post = userPostRepository.findById(postRequest.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        userPostRepository.delete(post);
    }

    @Override
    public PostResponse getPostById(Integer userId, PostRequest postRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        UserPost post = userPostRepository.findById(postRequest.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        return PostResponse.builder()
                .name(user.getLastName() + " " + user.getFirstName())
                .content(postRequest.getContent())
                .postStatus(post.getPostStatus().toString())
                .actionPerformed(post.getActionPerformed().toString())
                .build();
    }

    @Override
    public List<PostResponse> getUserPosts(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<UserPost> posts = userPostRepository.findByUserId(userId);
        return posts.stream()
                .map(post -> PostResponse.builder()
                        .name(user.getLastName() + " " + user.getFirstName())
                        .content(post.getContent())
                        .postStatus(post.getPostStatus().toString())
                        .actionPerformed(post.getActionPerformed().toString())
                        .build()
                )
                .toList();
    }

    @Override
    public Boolean likePost(Integer userId, PostRequest postRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        UserPost post = userPostRepository.findById(postRequest.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Optional<UserLikePost> existingLike = userLikePostRepository.findByUserAndUserPost(user, post);

        if (existingLike.isPresent()) {
            userLikePostRepository.delete(existingLike.get());
            return false; // Unlike
        } else {
            UserLikePost newLike = new UserLikePost();
            newLike.setUser(user);
            newLike.setUserPost(post);
            userLikePostRepository.save(newLike);

            // cannot send it to yourself
            if (!post.getUser().getId().equals(userId)) {
                String message = user.getLastName() + " " + user.getFirstName() + " liked your post!";
                notificationService.sendNotification(userId, postRequest.getPostId(), message);
                return true; // Like
            }

            return true; // Like, but without sending a notification
        }
    }

    @Override
    public List<UserResponse> getUsersWhoLikedPost(PostRequest postRequest) {
        UserPost post = userPostRepository.findById(postRequest.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        List<UserLikePost> likes = userLikePostRepository.findByUserPost(post);

        return likes.stream()
                .map(like -> {
                    User user = like.getUser();
                    return new UserResponse(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber(), user.getGender().toString());
                })
                .toList();
    }
}
