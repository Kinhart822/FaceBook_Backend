package com.spring.controller;

import com.spring.dto.Request.Group.RoleRequest;
import com.spring.dto.Response.Group.RoleResponse;
import com.spring.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/group")
@RequiredArgsConstructor
public class GroupController {
    @Autowired
    private RoleService roleService;

    // TODO: Role
    @PostMapping("/role/add")
    public ResponseEntity<RoleResponse> addRole(@RequestBody RoleRequest roleRequest) {
        RoleResponse roleResponse = roleService.createRole(roleRequest);
        return ResponseEntity.ok(roleResponse);
    }

    @GetMapping("/role/getRoleById")
    public ResponseEntity<RoleResponse> getRoleById(@RequestBody RoleRequest roleRequest) {
        RoleResponse roleResponse = roleService.getRoleById(roleRequest.getRoleId());
        return ResponseEntity.ok(roleResponse);
    }

    @GetMapping("/role/getAllRoles")
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        List<RoleResponse> roleResponses = roleService.getAllRoles();
        return ResponseEntity.ok(roleResponses);
    }

    @PutMapping("/role/edit")
    public ResponseEntity<RoleResponse> editRole(@RequestBody RoleRequest roleRequest) {
        RoleResponse roleResponse = roleService.updateRole(roleRequest);
        return ResponseEntity.ok(roleResponse);
    }

    @DeleteMapping("/role/delete/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok("Delete Role Successfully");
    }
}
