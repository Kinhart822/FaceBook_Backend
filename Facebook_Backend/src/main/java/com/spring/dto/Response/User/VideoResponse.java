package com.spring.dto.Response.User;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoResponse {
    private String name;
    private String description;
    private String videoUrl;
}
