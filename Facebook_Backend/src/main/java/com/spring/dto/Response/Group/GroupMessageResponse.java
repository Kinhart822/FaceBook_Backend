
package com.spring.dto.Response.Group;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupMessageResponse {
    private String sourceName;
    private String content;
    private String targetName;
}
