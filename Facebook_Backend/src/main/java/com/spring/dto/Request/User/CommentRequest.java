package com.spring.dto.Request.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    private String content;
    private Integer commentId;
    private Integer userPostId;
    private Integer videoPostId;
    private Integer groupPostId;
}
