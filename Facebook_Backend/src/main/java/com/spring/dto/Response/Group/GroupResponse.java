package com.spring.dto.Response.Group;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupResponse {
    private String title;
    private String description;
    private String background;
}
