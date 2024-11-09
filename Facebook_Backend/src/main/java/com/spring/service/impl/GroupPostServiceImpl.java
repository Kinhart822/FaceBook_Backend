package com.spring.service.impl;

import com.spring.dto.Request.Group.GroupPostRequest;
import com.spring.dto.response.Group.GroupPostResponse;
import com.spring.dto.response.User.UserResponse;
import com.spring.entities.*;
import com.spring.enums.ActionPerformed;
import com.spring.enums.PostStatus;
import com.spring.repository.*;
import com.spring.service.GroupPostService;
import com.spring.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GroupPostServiceImpl implements GroupPostService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupPostRepository groupPostRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLikePostRepository userLikePostRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Override
    public GroupPostResponse addPost(Integer userId, GroupPostRequest groupPostRequest) {
        Group selectedGroup = groupRepository.findById(groupPostRequest.getSelectedGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        GroupPost newPost = new GroupPost();
        newPost.setGroup(selectedGroup);
        newPost.setUser(user);
        newPost.setMessage(groupPostRequest.getContent());
        newPost.setDateCreated(new Date());
        newPost.setDateUpdated(new Date());
        newPost.setPostStatus(groupPostRequest.getPostStatus());
        if (groupPostRequest.getPostStatus() == PostStatus.Draft){
            newPost.setActionPerformed(ActionPerformed.CreatedDraftPost);
        } else if (groupPostRequest.getPostStatus() == PostStatus.Published){
            newPost.setActionPerformed(ActionPerformed.CreatedPost);
        }
        newPost = groupPostRepository.save(newPost);

        List<Photo> photos = new ArrayList<>();
        if (groupPostRequest.getImageUrl() != null) {
            for (String imageUrl : groupPostRequest.getImageUrl()) {
                Photo photo = new Photo();
                photo.setUserId(userId);
                photo.setImageUrl(imageUrl);
                photo.setUploadDate(Instant.now());
                photo.setGroupPost(newPost);
                photos.add(photo);
                photoRepository.save(photo);
            }
        }
        newPost.setPhotos(photos);

        List<Video> videoList = new ArrayList<>();
        if (groupPostRequest.getVideoUrl() != null) {
            for (String videoUrl : groupPostRequest.getVideoUrl()) {
                Video video = new Video();
                video.setVideoUrl(videoUrl);
                video.setCreatedBy(userId);
                video.setDateCreated(Instant.now());
                video.setGroupPost(newPost);
                videoList.add(video);
                videoRepository.save(video);
            }
        }
        newPost.setVideoList(videoList);

        String message = user.getLastName() + " " + user.getFirstName() + " successfully created a new group post!";
        notificationService.sendNotification(userId, newPost.getId(), message);

        return GroupPostResponse.builder()
                .name(user.getLastName() + " " + user.getFirstName())
                .content(newPost.getMessage())
                .imageUrl(groupPostRequest.getImageUrl())
                .videoUrl(groupPostRequest.getVideoUrl())
                .postStatus(newPost.getPostStatus().toString())
                .actionPerformed(newPost.getActionPerformed().toString())
                .build();
    }

    @Override
    public GroupPostResponse editPost(Integer userId, GroupPostRequest groupPostRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        GroupPost existingPost = groupPostRepository.findById(groupPostRequest.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!existingPost.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Unauthorized edit attempt");
        }

        existingPost.setMessage(groupPostRequest.getContent());
        existingPost.setDateUpdated(new Date());
        if (groupPostRequest.getPostStatus() == PostStatus.Draft){
            existingPost.setActionPerformed(ActionPerformed.UpdatedDraftPost);
        } else if (groupPostRequest.getPostStatus() == PostStatus.Published){
            existingPost.setActionPerformed(ActionPerformed.UpdatedPost);
        }
        groupPostRepository.save(existingPost);

        photoRepository.deleteByGroupPost(existingPost);
        List<Photo> photos = new ArrayList<>();
        if (groupPostRequest.getImageUrl() != null) {
            for (String imageUrl : groupPostRequest.getImageUrl()) {
                Photo photo = new Photo();
                photo.setUserId(userId);
                photo.setImageUrl(imageUrl);
                photo.setUploadDate(Instant.now());
                photo.setGroupPost(existingPost);
                photos.add(photo);
                photoRepository.save(photo);
            }
        }
        existingPost.setPhotos(photos);

        videoRepository.deleteByGroupPost(existingPost);
        List<Video> videoList = new ArrayList<>();
        if (groupPostRequest.getVideoUrl()!= null) {
            for (String videoUrl : groupPostRequest.getVideoUrl()) {
                Video video = new Video();
                video.setVideoUrl(videoUrl);
                video.setCreatedBy(userId);
                video.setUpdatedBy(userId);
                video.setDateCreated(Instant.now());
                video.setDateUpdated(Instant.now());
                video.setGroupPost(existingPost);
                videoList.add(video);
                videoRepository.save(video);
            }
        }
        existingPost.setVideoList(videoList);

        String message = user.getLastName() + " " + user.getFirstName() + " successfully updated group post!";
        notificationService.sendNotification(userId, existingPost.getId(), message);

        return GroupPostResponse.builder()
                .name(user.getLastName() + " " + user.getFirstName())
                .content(existingPost.getMessage())
                .imageUrl(groupPostRequest.getImageUrl())
                .videoUrl(groupPostRequest.getVideoUrl())
                .postStatus(existingPost.getPostStatus().toString())
                .actionPerformed(existingPost.getActionPerformed().toString())
                .build();
    }

    @Override
    public void deletePost(Integer userId, GroupPostRequest groupPostRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        GroupPost post = groupPostRepository.findById(groupPostRequest.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        String message = user.getLastName() + " " + user.getFirstName() + " successfully deleted post!";
        notificationService.sendNotification(userId, post.getId(), message);

        groupPostRepository.delete(post);
    }

    @Override
    public GroupPostResponse getPostById(Integer userId, GroupPostRequest groupPostRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        GroupPost post = groupPostRepository.findById(groupPostRequest.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        return GroupPostResponse.builder()
                .name(user.getLastName() + " " + user.getFirstName())
                .content(post.getMessage())
                .imageUrl(post.getPhotos().stream().map(Photo::getImageUrl).toList())
                .videoUrl(post.getVideoList().stream().map(Video::getVideoUrl).toList())
                .postStatus(post.getPostStatus().toString())
                .actionPerformed(post.getActionPerformed().toString())
                .build();
    }

    @Override
    public List<GroupPostResponse> getUserPosts(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<GroupPost> posts = groupPostRepository.findByUserId(userId);
        return posts.stream()
                .map(post -> GroupPostResponse.builder()
                        .name(user.getLastName() + " " + user.getFirstName())
                        .content(post.getMessage())
                        .imageUrl(post.getPhotos().stream().map(Photo::getImageUrl).toList())
                        .videoUrl(post.getVideoList().stream().map(Video::getVideoUrl).toList())
                        .postStatus(post.getPostStatus().toString())
                        .actionPerformed(post.getActionPerformed().toString())
                        .build()
                )
                .toList();
    }

    @Override
    public List<GroupPostResponse> getPostsByGroupId(Integer userId, GroupPostRequest groupPostRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<GroupPost> posts = groupPostRepository.findByGroupId(groupPostRequest.getSelectedGroupId());
        return posts.stream()
                .map(post -> GroupPostResponse.builder()
                        .name(user.getLastName() + " " + user.getFirstName())
                        .content(post.getMessage())
                        .imageUrl(post.getPhotos().stream().map(Photo::getImageUrl).toList())
                        .videoUrl(post.getVideoList().stream().map(Video::getVideoUrl).toList())
                        .postStatus(post.getPostStatus().toString())
                        .actionPerformed(post.getActionPerformed().toString())
                        .build()
                )
                .toList();
    }

    @Override
    public Boolean likePost(Integer userId, GroupPostRequest groupPostRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        GroupPost post = groupPostRepository.findById(groupPostRequest.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Optional<UserLikePost> existingLike = userLikePostRepository.findByUserAndGroupPost(user, post);

        if (existingLike.isPresent()) {
            userLikePostRepository.delete(existingLike.get());
            return false; // Unlike
        } else {
            UserLikePost newLike = new UserLikePost();
            newLike.setUser(user);
            newLike.setGroupPost(post);
            userLikePostRepository.save(newLike);

            // cannot send it to yourself
            if (!post.getUser().getId().equals(userId)) {
                String message = user.getLastName() + " " + user.getFirstName() + " liked your post!";
                notificationService.sendGroupNotification(userId, groupPostRequest.getPostId(), message);
                return true; // Like
            }

            return true; // Like, but without sending a notification
        }
    }

    @Override
    public List<UserResponse> getUsersWhoLikedPost(GroupPostRequest groupPostRequest) {
        GroupPost post = groupPostRepository.findById(groupPostRequest.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        List<UserLikePost> likes = userLikePostRepository.findByGroupPost(post);

        return likes.stream()
                .map(like -> {
                    User user = like.getUser();
                    return new UserResponse(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber(), user.getGender().toString());
                })
                .toList();
    }
}
