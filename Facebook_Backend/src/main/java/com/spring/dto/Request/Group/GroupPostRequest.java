package com.spring.dto.Request.Group;

import com.spring.enums.PostStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupPostRequest {
    private Integer selectedGroupId;
    private Integer postId;
    private String content;
    private String imageUrl;
    private String videoUrl;
    private PostStatus postStatus;
}
