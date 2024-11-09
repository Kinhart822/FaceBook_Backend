package com.spring.service.impl;

import com.spring.dto.Request.Group.GroupMessageRequest;
import com.spring.dto.response.Group.GroupMessageResponse;
import com.spring.entities.Group;
import com.spring.entities.GroupMessage;
import com.spring.entities.User;
import com.spring.repository.GroupMessageRepository;
import com.spring.repository.GroupRepository;
import com.spring.repository.UserRepository;
import com.spring.service.GroupMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GroupMessageServiceImpl implements GroupMessageService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMessageRepository groupMessageRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public GroupMessageResponse sendMessage(Integer sourceId, GroupMessageRequest groupMessageRequest) {
        Group selectedGroup = groupRepository.findById(groupMessageRequest.getSelectedGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Selected group not found"));

        User sourceUser = userRepository.findById(sourceId)
                .orElseThrow(() -> new IllegalArgumentException("Source user not found"));
        User targetUser = userRepository.findById(groupMessageRequest.getTargetUserId())
                .orElseThrow(() -> new IllegalArgumentException("Target user not found"));

        GroupMessage message = GroupMessage.builder()
                .group(selectedGroup)
                .sourceUser(sourceUser)
                .targetUser(targetUser)
                .messageContent(groupMessageRequest.getContent())
                .dateCreated(new Date())
                .dateUpdated(new Date())
                .build();
        groupMessageRepository.save(message);

        return GroupMessageResponse.builder()
                .sourceName(message.getSourceUser().getLastName() + " " + message.getTargetUser().getFirstName())
                .targetName(message.getTargetUser().getLastName() + " " + message.getTargetUser().getFirstName())
                .content(message.getMessageContent())
                .build();
    }

    @Override
    public GroupMessageResponse editMessage(Integer sourceId, Integer messageId, GroupMessageRequest groupMessageRequest) {
        GroupMessage existingMessage = groupMessageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));

        if (!existingMessage.getSourceUser().getId().equals(sourceId)) {
            throw new IllegalArgumentException("You do not have permission to edit this message");
        }

        existingMessage.setMessageContent(groupMessageRequest.getContent());
        existingMessage.setDateUpdated(new Date());
        groupMessageRepository.save(existingMessage);

        return GroupMessageResponse.builder()
                .sourceName(existingMessage.getSourceUser().getLastName() + " " + existingMessage.getSourceUser().getFirstName())
                .targetName(existingMessage.getTargetUser().getLastName() + " " + existingMessage.getTargetUser().getFirstName())
                .content(existingMessage.getMessageContent())
                .build();
    }

    @Override
    public void deleteMessage(Integer sourceId, Integer messageId) {
        GroupMessage existingMessage = groupMessageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));

        if (!existingMessage.getSourceUser().getId().equals(sourceId)) {
            throw new IllegalArgumentException("You do not have permission to delete this message");
        }

        groupMessageRepository.delete(existingMessage);
    }

    @Override
    public List<GroupMessageResponse> findBySourceId(Integer userId) {
        List<GroupMessage> messages = groupMessageRepository.findBySourceId(userId);
        return messages.stream()
                .map(message -> GroupMessageResponse.builder()
                        .sourceName(message.getSourceUser().getFirstName() + " " + message.getSourceUser().getLastName())
                        .targetName(message.getTargetUser().getFirstName() + " " + message.getTargetUser().getLastName())
                        .content(message.getMessageContent())
                        .build())
                .toList();
    }

    @Override
    public List<GroupMessageResponse> findByTargetId(GroupMessageRequest groupMessageRequest) {
        List<GroupMessage> messages = groupMessageRepository.findByTargetId(groupMessageRequest.getTargetUserId());
        return messages.stream()
                .map(message -> GroupMessageResponse.builder()
                        .sourceName(message.getSourceUser().getFirstName() + " " + message.getSourceUser().getLastName())
                        .targetName(message.getTargetUser().getFirstName() + " " + message.getTargetUser().getLastName())
                        .content(message.getMessageContent())
                        .build())
                .toList();
    }

    @Override
    public List<GroupMessageResponse> getMessagesByGroupId(GroupMessageRequest groupMessageRequest) {
        List<GroupMessage> messages = groupMessageRepository.findByGroupId(groupMessageRequest.getSelectedGroupId());
        return messages.stream()
                .map(message -> GroupMessageResponse.builder()
                        .sourceName(message.getSourceUser().getFirstName() + " " + message.getSourceUser().getLastName())
                        .targetName(message.getTargetUser().getFirstName() + " " + message.getTargetUser().getLastName())
                        .content(message.getMessageContent())
                        .build())
                .toList();
    }
}
