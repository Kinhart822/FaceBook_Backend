package com.spring.dto.response.User;

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
