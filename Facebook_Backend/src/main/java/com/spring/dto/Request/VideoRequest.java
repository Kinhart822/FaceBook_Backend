package com.spring.dto.Request;

import lombok.Data;

@Data
public class VideoRequest {
    private String name;
    private String description;
    private Integer length;
    private String videoUrl;
}
