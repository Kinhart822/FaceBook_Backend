package com.spring.dto.Response.User;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFriendResponse {
    private String sourceUserName;
    private String targetUserName;
    private String friendRequestStatus;
}
