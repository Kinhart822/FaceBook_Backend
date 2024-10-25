package com.spring.dto.Response.User;

import lombok.*;

import java.util.Date;

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
