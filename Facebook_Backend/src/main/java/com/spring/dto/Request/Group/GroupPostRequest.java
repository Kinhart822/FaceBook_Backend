package com.spring.dto.Request.Group;

import com.spring.enums.PostStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupPostRequest {
    private Integer selectedGroupId;
    private Integer postId;
    private String content;
    private List<String> imageUrl;
    private List<String> videoUrl;
    private PostStatus postStatus;
}
