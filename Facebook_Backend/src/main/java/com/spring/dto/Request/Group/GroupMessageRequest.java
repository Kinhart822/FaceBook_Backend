package com.spring.dto.Request.Group;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupMessageRequest {
    private Integer selectedGroupId;
    private Integer targetUserId;
    private String content;
}
