package com.spring.service;

import com.spring.dto.Request.User.UserMessageRequest;
import com.spring.dto.response.User.UserMessageResponse;

import java.util.List;

public interface UserMessageService {
    UserMessageResponse sendMessage(Integer sourceId, UserMessageRequest userMessageRequest);
    UserMessageResponse editMessage(Integer sourceId, Integer messageId, UserMessageRequest userMessageRequest);
    void deleteMessage(Integer sourceId, Integer messageId);
    List<UserMessageResponse> findBySourceId(Integer userId);
    List<UserMessageResponse> findByTargetId(UserMessageRequest userMessageRequest);
}
