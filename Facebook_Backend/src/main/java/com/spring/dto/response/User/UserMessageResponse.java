package com.spring.dto.response.User;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMessageResponse {
    private String sourceName;
    private String content;
    private String targetName;
}
