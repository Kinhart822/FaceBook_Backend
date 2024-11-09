package com.spring.dto.response.Group;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupMemberResponse {
    private String title;
    private String description;
    private String avatarImage;
    private String notes;
}
