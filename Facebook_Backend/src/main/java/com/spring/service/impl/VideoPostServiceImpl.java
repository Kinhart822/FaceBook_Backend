package com.spring.service.impl;

import com.spring.dto.Request.User.VideoPostRequest;
import com.spring.dto.Response.User.UserResponse;
import com.spring.dto.Response.User.VideoPostResponse;
import com.spring.entities.*;
import com.spring.enums.ActionPerformed;
import com.spring.enums.PostStatus;
import com.spring.repository.*;
import com.spring.service.NotificationService;
import com.spring.service.VideoPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class VideoPostServiceImpl implements VideoPostService {
    @Autowired
    private VideoPostRepository videoPostRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLikePostRepository userLikePostRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public VideoPostResponse addPost(Integer userId, VideoPostRequest videoPostRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Video video = new Video();
        video.setName(videoPostRequest.getName());
        video.setDescription(videoPostRequest.getDescription());
        video.setLength(videoPostRequest.getLength());
        video.setVideoUrl(videoPostRequest.getVideoUrl());
        video.setCreatedBy(userId);
        video.setUpdatedBy(userId);
        video.setDateCreated(Instant.now());
        video.setDateUpdated(Instant.now());
        videoRepository.save(video);

        VideoPost newPost = new VideoPost();
        newPost.setUser(user);
        newPost.setPostStatus(videoPostRequest.getPostStatus());
        newPost.setActionPerformed(videoPostRequest.getPostStatus() == PostStatus.Draft
                ? ActionPerformed.CreatedDraftPost
                : ActionPerformed.CreatedPost);
        newPost.setVideo(video);
        newPost = videoPostRepository.save(newPost);

        video.setVideoPost(newPost);
        videoRepository.save(video);

        return VideoPostResponse.builder()
                .name(user.getLastName() + " " + user.getFirstName())
                .content(video.getName())
                .description(video.getDescription())
                .length(video.getLength())
                .videoUrl(video.getVideoUrl())
                .postStatus(newPost.getPostStatus().toString())
                .actionPerformed(newPost.getActionPerformed().toString())
                .build();
    }

    @Override
    public VideoPostResponse editPost(Integer userId, VideoPostRequest videoPostRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        VideoPost existingPost = videoPostRepository.findById(videoPostRequest.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!existingPost.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Unauthorized edit attempt");
        }

        if (videoPostRequest.getName() != null) {
            existingPost.getVideo().setName(videoPostRequest.getName());
        }
        if (videoPostRequest.getDescription() != null) {
            existingPost.getVideo().setDescription(videoPostRequest.getDescription());
        }
        if (videoPostRequest.getLength() != null) {
            existingPost.getVideo().setLength(videoPostRequest.getLength());
        }
        existingPost.getVideo().setUpdatedBy(userId);
        existingPost.getVideo().setDateUpdated(Instant.now());
        if (videoPostRequest.getVideoUrl() != null) {
            existingPost.getVideo().setVideoUrl(videoPostRequest.getVideoUrl());
        }
        if (videoPostRequest.getPostStatus() == PostStatus.Draft){
            existingPost.setActionPerformed(ActionPerformed.UpdatedDraftPost);
        } else if (videoPostRequest.getPostStatus() == PostStatus.Published){
            existingPost.setActionPerformed(ActionPerformed.UpdatedPost);
        }
        existingPost.setPostStatus(videoPostRequest.getPostStatus());

        videoPostRepository.save(existingPost);
        videoRepository.save(existingPost.getVideo());

        return VideoPostResponse.builder()
                .name(user.getLastName() + " " + user.getFirstName())
                .content(existingPost.getVideo().getName())
                .description(existingPost.getVideo().getDescription())
                .length(existingPost.getVideo().getLength())
                .videoUrl(existingPost.getVideo().getVideoUrl())
                .postStatus(existingPost.getPostStatus().toString())
                .actionPerformed(existingPost.getActionPerformed().toString())
                .build();
    }

    @Override
    public void deletePost(VideoPostRequest videoPostRequest) {
        VideoPost post = videoPostRepository.findById(videoPostRequest.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        videoPostRepository.delete(post);
    }

    @Override
    public VideoPostResponse getPostById(Integer userId, VideoPostRequest videoPostRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        VideoPost post = videoPostRepository.findById(videoPostRequest.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        return VideoPostResponse.builder()
                .name(user.getLastName() + " " + user.getFirstName())
                .content(post.getVideo().getName())
                .description(post.getVideo().getDescription())
                .length(post.getVideo().getLength())
                .videoUrl(post.getVideo().getVideoUrl())
                .postStatus(post.getPostStatus().toString())
                .actionPerformed(post.getActionPerformed().toString())
                .build();
    }

    @Override
    public List<VideoPostResponse> getVideoPosts(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<VideoPost> posts = videoPostRepository.findByUser(user);
        return posts.stream()
                .map(post -> VideoPostResponse.builder()
                        .name(user.getLastName() + " " + user.getFirstName())
                        .content(post.getVideo().getName())
                        .description(post.getVideo().getDescription())
                        .length(post.getVideo().getLength())
                        .videoUrl(post.getVideo().getVideoUrl())
                        .postStatus(post.getPostStatus().toString())
                        .actionPerformed(post.getActionPerformed().toString())
                        .build()
                )
                .toList();    }

    @Override
    public Boolean likePost(Integer userId, VideoPostRequest videoPostRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        VideoPost post = videoPostRepository.findById(videoPostRequest.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Optional<UserLikePost> existingLike = userLikePostRepository.findByUserAndVideoPost(user, post);

        if (existingLike.isPresent()) {
            userLikePostRepository.delete(existingLike.get());
            return false; // Unlike
        } else {
            UserLikePost newLike = new UserLikePost();
            newLike.setUser(user);
            newLike.setVideoPost(post);
            userLikePostRepository.save(newLike);

            // cannot send it to yourself
            if (!post.getUser().getId().equals(userId)) {
                String message = user.getLastName() + " " + user.getFirstName() + " liked your post!";
                notificationService.sendVideoNotification(userId, videoPostRequest.getPostId(), message);
                return true; // Like
            }

            return true; // Like, but without sending a notification
        }    }

    @Override
    public List<UserResponse> getUsersWhoLikedPost(VideoPostRequest videoPostRequest) {
        VideoPost post = videoPostRepository.findById(videoPostRequest.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        List<UserLikePost> likes = userLikePostRepository.findByVideoPost(post);

        return likes.stream()
                .map(like -> {
                    User user = like.getUser();
                    return new UserResponse(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber(), user.getGender().toString());
                })
                .toList();    }

    @Override
    public String getVideoUrlByVideoPostAndPostStatus(VideoPostRequest videoPostRequest) {
        VideoPost post = videoPostRepository.findById(videoPostRequest.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!post.getPostStatus().equals(videoPostRequest.getPostStatus())) {
            throw new IllegalArgumentException("Post status does not match");
        }

        return post.getVideo().getVideoUrl();
    }
}
