package com.spring.dto.Request.Group;

import lombok.*;

@Getter
@Setter
public class GroupMemberRequest {
    private Integer selectedGroupId;
    private Integer selectedMemberId;
    private String roleTitle;
    private String roleDescription;
    private String notes;
}
