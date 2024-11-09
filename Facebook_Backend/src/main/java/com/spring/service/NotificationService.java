package com.spring.service;

import com.spring.dto.Request.User.NotificationRequest;
import com.spring.dto.response.User.NotificationResponse;

public interface NotificationService {
    void sendNotification(Integer userId, Integer postId, String message);
    void sendVideoNotification(Integer userId, Integer postId, String message);
    void sendGroupNotification(Integer userId, Integer postId, String message);
    void markNotificationAsRead(NotificationRequest notificationRequest);
    NotificationResponse getNotificationsByUserId(Integer userId);
}
