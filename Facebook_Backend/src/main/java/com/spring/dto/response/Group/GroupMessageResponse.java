
package com.spring.dto.response.Group;

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
