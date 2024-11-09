package com.spring.dto.response.Group;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchGroupByTitleResponse {
    private String title;
    private String description;
    private String background;
}
