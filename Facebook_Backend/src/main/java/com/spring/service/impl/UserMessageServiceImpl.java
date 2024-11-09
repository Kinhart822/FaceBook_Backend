package com.spring.service.impl;

import com.spring.dto.Request.User.UserMessageRequest;
import com.spring.dto.response.User.UserMessageResponse;
import com.spring.entities.User;
import com.spring.entities.UserMessage;
import com.spring.repository.UserMessageRepository;
import com.spring.repository.UserRepository;
import com.spring.service.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserMessageServiceImpl implements UserMessageService {
    @Autowired
    private UserMessageRepository userMessageRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserMessageResponse sendMessage(Integer sourceId, UserMessageRequest userMessageRequest) {
        User sourceUser = userRepository.findById(sourceId)
                .orElseThrow(() -> new IllegalArgumentException("Source user not found"));
        User targetUser = userRepository.findById(userMessageRequest.getTargetUserId())
                .orElseThrow(() -> new IllegalArgumentException("Target user not found"));

        UserMessage message = UserMessage.builder()
                .sourceUser(sourceUser)
                .targetUser(targetUser)
                .messageContent(userMessageRequest.getContent())
                .dateCreated(new Date())
                .dateUpdated(new Date())
                .build();
        userMessageRepository.save(message);

        return UserMessageResponse.builder()
                .sourceName(message.getSourceUser().getLastName() + " " + message.getTargetUser().getFirstName())
                .targetName(message.getTargetUser().getLastName() + " " + message.getTargetUser().getFirstName())
                .content(message.getMessageContent())
                .build();
    }

    @Override
    public UserMessageResponse editMessage(Integer sourceId, Integer messageId, UserMessageRequest userMessageRequest) {
        UserMessage existingMessage = userMessageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));

        if (!existingMessage.getSourceUser().getId().equals(sourceId)) {
            throw new IllegalArgumentException("You do not have permission to edit this message");
        }

        existingMessage.setMessageContent(userMessageRequest.getContent());
        existingMessage.setDateUpdated(new Date());
        userMessageRepository.save(existingMessage);

        return UserMessageResponse.builder()
                .sourceName(existingMessage.getSourceUser().getLastName() + " " + existingMessage.getSourceUser().getFirstName())
                .targetName(existingMessage.getTargetUser().getLastName() + " " + existingMessage.getTargetUser().getFirstName())
                .content(existingMessage.getMessageContent())
                .build();
    }

    @Override
    public void deleteMessage(Integer sourceId, Integer messageId) {
        UserMessage existingMessage = userMessageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));

        if (!existingMessage.getSourceUser().getId().equals(sourceId)) {
            throw new IllegalArgumentException("You do not have permission to delete this message");
        }

        userMessageRepository.delete(existingMessage);
    }

    @Override
    public List<UserMessageResponse> findBySourceId(Integer userId) {
        List<UserMessage> messages = userMessageRepository.findBySourceId(userId);
        return messages.stream()
                .map(message -> UserMessageResponse.builder()
                        .sourceName(message.getSourceUser().getFirstName() + " " + message.getSourceUser().getLastName())
                        .targetName(message.getTargetUser().getFirstName() + " " + message.getTargetUser().getLastName())
                        .content(message.getMessageContent())
                        .build())
                .toList();
    }

    @Override
    public List<UserMessageResponse> findByTargetId(UserMessageRequest userMessageRequest) {
        List<UserMessage> messages = userMessageRepository.findByTargetId(userMessageRequest.getTargetUserId());
        return messages.stream()
                .map(message -> UserMessageResponse.builder()
                        .sourceName(message.getSourceUser().getFirstName() + " " + message.getSourceUser().getLastName())
                        .targetName(message.getTargetUser().getFirstName() + " " + message.getTargetUser().getLastName())
                        .content(message.getMessageContent())
                        .build())
                .toList();
    }
}
