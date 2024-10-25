package com.spring.dto.Request.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMessageRequest {
    private Integer targetUserId;
    private String content;
}
