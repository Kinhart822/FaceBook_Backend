package com.spring.service.impl;

import com.spring.dto.Request.User.PostRequest;
import com.spring.dto.response.User.PostResponse;
import com.spring.dto.response.User.UserResponse;
import com.spring.entities.*;
import com.spring.enums.ActionPerformed;
import com.spring.enums.PostStatus;
import com.spring.repository.*;
import com.spring.service.NotificationService;
import com.spring.service.UserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
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
    private PhotoRepository photoRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private VideoRepository videoRepository;

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
        newPost.setActionPerformed(postRequest.getPostStatus() == PostStatus.Draft
                ? ActionPerformed.CreatedDraftPost
                : ActionPerformed.CreatedPost);

        newPost = userPostRepository.save(newPost);

        List<Photo> photos = new ArrayList<>();
        if (postRequest.getImageUrl() != null) {
            for (String imageUrl : postRequest.getImageUrl()) {
                Photo photo = new Photo();
                photo.setUserId(userId);
                photo.setImageUrl(imageUrl);
                photo.setUploadDate(Instant.now());
                photo.setUserPost(newPost);
                photos.add(photo);
                photoRepository.save(photo);
            }
        }

        newPost.setPhotos(photos);

        List<Video> videoList = new ArrayList<>();
        if (postRequest.getVideoUrl() != null) {
            for (String videoUrl : postRequest.getVideoUrl()) {
                Video video = new Video();
                video.setCreatedBy(userId);
                video.setUpdatedBy(userId);
                video.setVideoUrl(videoUrl);
                video.setDateCreated(Instant.now());
                video.setDateUpdated(Instant.now());
                video.setUserPost(newPost);
                videoList.add(video);
                videoRepository.save(video);
            }
        }

        newPost.setVideoList(videoList);

        String message = user.getLastName() + " " + user.getFirstName() + " successfully created a new post!";
        notificationService.sendNotification(userId, newPost.getId(), message);

        return PostResponse.builder()
                .name(user.getLastName() + " " + user.getFirstName())
                .content(newPost.getContent())
                .imageUrl(postRequest.getImageUrl())
                .videoUrl(postRequest.getVideoUrl())
                .postStatus(newPost.getPostStatus().toString())
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

        photoRepository.deleteByUserPost(existingPost);

        List<Photo> photos = new ArrayList<>();
        if (postRequest.getImageUrl() != null) {
            for (String imageUrl : postRequest.getImageUrl()) {
                Photo photo = new Photo();
                photo.setUserId(userId);
                photo.setImageUrl(imageUrl);
                photo.setUploadDate(Instant.now());
                photo.setUserPost(existingPost);
                photos.add(photo);
                photoRepository.save(photo);
            }
        }

        existingPost.setPhotos(photos);

        String message = user.getLastName() + " " + user.getFirstName() + " successfully updated post!";
        notificationService.sendNotification(userId, existingPost.getId(), message);

        // Trả về PostResponse
        return PostResponse.builder()
                .name(user.getLastName() + " " + user.getFirstName())
                .content(existingPost.getContent())
                .imageUrl(postRequest.getImageUrl())
                .postStatus(existingPost.getPostStatus().toString())
                .actionPerformed(existingPost.getActionPerformed().toString())
                .build();
    }

    @Override
    public void deletePost(Integer userId, PostRequest postRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        UserPost post = userPostRepository.findById(postRequest.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        String message = user.getLastName() + " " + user.getFirstName() + " successfully deleted post!";
        notificationService.sendNotification(userId, post.getId(), message);

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
                .content(post.getContent())
                .imageUrl(post.getPhotos().stream().map(Photo::getImageUrl).toList())
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
                        .imageUrl(post.getPhotos().stream().map(Photo::getImageUrl).toList())
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

    @Override
    public List<String> getAllImageUrlByUserPostAndPostStatus(PostRequest postRequest) {
        UserPost post = userPostRepository.findById(postRequest.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!post.getPostStatus().equals(postRequest.getPostStatus())) {
            throw new IllegalArgumentException("Post status does not match");
        }

        return post.getPhotos().stream()
                .map(Photo::getImageUrl)
                .toList();
    }
}
