package com.spring.service.impl;

import com.spring.dto.Request.Group.GroupRequest;
import com.spring.dto.Response.Group.GroupResponse;
import com.spring.entities.Group;
import com.spring.entities.GroupMember;
import com.spring.entities.Role;
import com.spring.entities.User;
import com.spring.repository.GroupMemberRepository;
import com.spring.repository.GroupRepository;
import com.spring.repository.RoleRepository;
import com.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.service.GroupService;

import java.util.Date;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService{
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public GroupResponse createGroup(Integer userId, GroupRequest groupRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Role selectedRole = roleRepository.findByTitle("Admin");
        if (selectedRole == null) {
            selectedRole = Role.builder()
                    .name("Admin")
                    .description("Administrator role for group management")
                    .dateCreated(new Date())
                    .dateUpdated(new Date())
                    .build();
            roleRepository.save(selectedRole);
        }

        Group group = Group.builder()
                .createdBy(user)
                .updatedBy(user)
                .title(groupRequest.getTitle())
                .description(groupRequest.getDescription())
                .dateCreated(new Date())
                .dateUpdated(new Date())
                .build();
        groupRepository.save(group);

        GroupMember addUserThatCreatedGroup = GroupMember.builder()
                .group(group)
                .user(user)
                .role(selectedRole)
                .notes("Admin of the group")
                .dateCreated(new Date())
                .dateUpdated(new Date())
                .build();
        groupMemberRepository.save(addUserThatCreatedGroup);

        return GroupResponse.builder()
                .title(groupRequest.getTitle())
                .description(groupRequest.getDescription())
                .build();
    }

    @Override
    public GroupResponse getGroupById(GroupRequest groupRequest) {
        Group group = groupRepository.findById(groupRequest.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        return GroupResponse.builder()
                .title(group.getTitle())
                .description(group.getDescription())
                .build();
    }

    @Override
    public List<GroupResponse> getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream()
                .map(group -> GroupResponse.builder()
                        .title(group.getTitle())
                        .description(group.getDescription())
                        .build())
                .toList();
    }

    @Override
    public GroupResponse updateGroup(Integer userId, GroupRequest groupRequest) {
        Group group = groupRepository.findById(groupRequest.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        if (!group.getCreatedBy().getId().equals(userId)) {
            throw new IllegalArgumentException("User not authorized to edit this comment");
        }

        group.setTitle(groupRequest.getTitle());
        group.setDescription(groupRequest.getDescription());
        groupRepository.save(group);

        return GroupResponse.builder()
                .title(group.getTitle())
                .description(group.getDescription())
                .build();
    }

    @Override
    public void deleteGroup(Integer id) {
        groupRepository.deleteById(id);
    }
}
