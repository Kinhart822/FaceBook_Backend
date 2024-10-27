package com.spring.dto.Request.Group;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupRequest {
    private Integer groupId;
    private String title;
    private String description;
}
