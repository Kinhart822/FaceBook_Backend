package com.spring.dto.Request.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFollowerRequest {
    private Integer targetUserId;
}