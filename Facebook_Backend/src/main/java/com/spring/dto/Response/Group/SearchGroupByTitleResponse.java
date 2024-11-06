package com.spring.dto.Response.Group;

import lombok.*;

import java.util.Date;

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
