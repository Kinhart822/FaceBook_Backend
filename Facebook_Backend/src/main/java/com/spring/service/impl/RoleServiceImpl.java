package com.spring.service.impl;

import com.spring.dto.Request.Group.RoleRequest;
import com.spring.dto.response.Group.RoleResponse;
import com.spring.entities.GroupMember;
import com.spring.entities.Role;
import com.spring.repository.GroupMemberRepository;
import com.spring.repository.RoleRepository;
import com.spring.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Override
    public RoleResponse createRole(RoleRequest roleRequest) {
        Role existingRole = roleRepository.findByTitle(roleRequest.getRoleName());
        if (existingRole != null) {
            throw new IllegalStateException("Role " + roleRequest.getRoleName() + " already exists");
        }

        Role role = new Role();
        role.setName(roleRequest.getRoleName());
        role.setDescription(roleRequest.getRoleDescription());
        role.setDateCreated(new Date());
        role.setDateUpdated(new Date());
        roleRepository.save(role);

        return RoleResponse.builder()
                .roleName(role.getName())
                .roleDescription(role.getDescription())
                .build();
    }

    @Override
    public RoleResponse getRoleById(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        return RoleResponse.builder()
                .roleName(role.getName())
                .roleDescription(role.getDescription())
                .build();
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        List<Role> roles = roleRepository.findAll();

        return roles.stream()
                .map(role -> RoleResponse.builder()
                        .roleName(role.getName())
                        .roleDescription(role.getDescription())
                        .build()
                ).toList();
    }

    @Override
    public RoleResponse updateRole(RoleRequest roleRequest) {
        Role role = roleRepository.findById(roleRequest.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));;
        role.setName(roleRequest.getRoleName());
        role.setDescription(roleRequest.getRoleDescription());
        roleRepository.save(role);

        return RoleResponse.builder()
                .roleName(role.getName())
                .roleDescription(role.getDescription())
                .build();
    }

    @Override
    public void deleteRole(Integer id) {
        Role roleToDelete = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        List<GroupMember> groupMembersWithRole = groupMemberRepository.findByRole(roleToDelete);

        if (!groupMembersWithRole.isEmpty()) {
            for (GroupMember groupMember : groupMembersWithRole) {
                groupMember.setRole(null);
                groupMemberRepository.save(groupMember);
            }
        }

        roleRepository.deleteById(id);    }
}

