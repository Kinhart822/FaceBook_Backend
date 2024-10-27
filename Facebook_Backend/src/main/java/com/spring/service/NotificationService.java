package com.spring.service;

import com.spring.dto.Response.User.NotificationResponse;

public interface NotificationService {
    void sendNotification(Integer userId, Integer postId, String message);
    NotificationResponse getNotificationsByUserId(Integer userId);
}
