package com.spring.dto.Request.User;

import com.spring.enums.PostStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VideoPostRequest {
    private Integer postId;
    private String name;
    private String description;
    private Integer length;
    private String videoUrl;
    private PostStatus postStatus;
}
