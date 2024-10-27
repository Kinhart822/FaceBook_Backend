package com.spring.dto.Response.Group;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupPostResponse {
    private String name;
    private String content;
    private String postStatus;
    private String actionPerformed;
}
