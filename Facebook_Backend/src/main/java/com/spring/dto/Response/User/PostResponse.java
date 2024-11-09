package com.spring.dto.Response.User;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
    private String name;
    private String content;
    private List<String> imageUrl;
    private List<String> videoUrl;
    private String postStatus;
    private String actionPerformed;
}
