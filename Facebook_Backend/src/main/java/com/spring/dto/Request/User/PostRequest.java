package com.spring.dto.Request.User;

import com.spring.enums.PostStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {
    private Integer postId;
    private String content;

    private PostStatus postStatus;
}
