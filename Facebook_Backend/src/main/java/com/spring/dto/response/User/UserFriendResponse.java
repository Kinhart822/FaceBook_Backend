package com.spring.dto.response.User;

import lombok.*;

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
