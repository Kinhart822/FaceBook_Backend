package com.spring.service;

import com.spring.dto.Request.Group.RoleRequest;
import com.spring.dto.response.Group.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse createRole(RoleRequest roleRequest);
    RoleResponse getRoleById(Integer id);
    List<RoleResponse> getAllRoles();
    RoleResponse updateRole(RoleRequest roleRequest);
    void deleteRole(Integer id);
}
