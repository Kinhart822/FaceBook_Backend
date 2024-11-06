package com.spring.service.impl;

import com.spring.dto.Request.User.NotificationRequest;
import com.spring.dto.Response.User.NotificationResponse;
import com.spring.entities.*;
import com.spring.repository.*;
import com.spring.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserPostRepository userPostRepository;

    @Autowired
    private VideoPostRepository videoPostRepository;

    @Autowired
    private GroupPostRepository groupPostRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void sendNotification(Integer userId, Integer postId,  String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        UserPost userPost = userPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("User Post not found"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setUserPost(userPost);
        notification.setMessage(message);
        notification.setDateCreated(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    @Override
    public void sendVideoNotification(Integer userId, Integer postId, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        VideoPost videoPost = videoPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("User Post not found"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setVideoPost(videoPost);
        notification.setMessage(message);
        notification.setDateCreated(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    @Override
    public void sendGroupNotification(Integer userId, Integer postId, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        GroupPost groupPost = groupPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("User Post not found"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setGroupPost(groupPost);
        notification.setMessage(message);
        notification.setDateCreated(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    @Override
    public NotificationResponse getNotificationsByUserId(Integer userId) {
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        List<String> messages = notifications.stream()
                .map(Notification::getMessage)
                .toList();

        List<String> elapsedTimes = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (Notification notification : notifications) {
            // Calculate elapsed time
            Duration duration = Duration.between(notification.getDateCreated(), now);
            long days = duration.toDays();
            long hours = duration.toHours() % 24;
            long minutes = duration.toMinutes() % 60;

            String elapsedTime;
            if (days > 0) {
                elapsedTime = days + " days ago";
            } else if (hours > 0) {
                elapsedTime = hours + " hours ago";
            } else {
                elapsedTime = minutes + " minutes ago";
            }
            elapsedTimes.add(elapsedTime);
        }

        return new NotificationResponse(messages, elapsedTimes);
    }

    @Override
    public void markNotificationAsRead(NotificationRequest notificationRequest) {
        Notification notification = notificationRepository.findById(notificationRequest.getNotificationId())
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));

        notification.setRead(true);
        notificationRepository.save(notification);
    }

}
