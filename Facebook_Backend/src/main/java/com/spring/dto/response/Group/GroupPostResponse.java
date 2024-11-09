package com.spring.dto.response.Group;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupPostResponse {
    private String name;
    private String content;
    private List<String> imageUrl;
    private List<String> videoUrl;
    private String postStatus;
    private String actionPerformed;
}
