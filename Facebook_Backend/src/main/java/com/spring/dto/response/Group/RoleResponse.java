package com.spring.dto.response.Group;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponse {
    private String roleName;
    private String roleDescription;
}
