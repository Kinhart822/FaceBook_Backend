package com.spring.dto.Request.User;

import com.spring.enums.PostStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostRequest {
    private Integer postId;
    private String content;
    private List<String> imageUrl;
    private List<String> videoUrl;
    private PostStatus postStatus;
}
