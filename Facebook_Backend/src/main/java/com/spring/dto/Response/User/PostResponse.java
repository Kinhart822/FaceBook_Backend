package com.spring.dto.Response.User;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
    private String name;
    private String content;
    private String postStatus;
    private String actionPerformed;
}
