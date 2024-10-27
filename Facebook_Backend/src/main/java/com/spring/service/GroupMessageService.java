package com.spring.service;

import com.spring.dto.Request.Group.GroupMessageRequest;
import com.spring.dto.Response.Group.GroupMessageResponse;
import com.spring.entities.GroupMessage;
import java.util.List;

public interface GroupMessageService {
    GroupMessageResponse sendMessage(Integer sourceId, GroupMessageRequest groupMessageRequest);
    GroupMessageResponse editMessage(Integer sourceId, Integer messageId, GroupMessageRequest groupMessageRequest);
    void deleteMessage(Integer sourceId, Integer messageId);
    List<GroupMessageResponse> findBySourceId(Integer userId);
    List<GroupMessageResponse> findByTargetId(GroupMessageRequest groupMessageRequest);
    List<GroupMessageResponse> getMessagesByGroupId(GroupMessageRequest groupMessageRequest);
}

