package com.spring.dto.Response.User;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoPostResponse {
    private String name;
    private String content;
    private String description;
    private Integer length;
    private String videoUrl;
    private String postStatus;
    private String actionPerformed;
}
