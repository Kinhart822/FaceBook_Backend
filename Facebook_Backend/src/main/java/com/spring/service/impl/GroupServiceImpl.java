package com.spring.service.impl;

import com.spring.dto.Request.Group.GroupRequest;
import com.spring.dto.Response.Group.GroupResponse;
import com.spring.dto.Response.Group.SearchGroupByTitleResponse;
import com.spring.entities.*;
import com.spring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.service.GroupService;

import java.time.Instant;
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

    @Autowired
    private PhotoRepository photoRepository;

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

        if (groupRequest.getBackgroundImageUrl() != null) {
            Photo photo = new Photo();
            photo.setUserId(userId);
            photo.setImageUrl(groupRequest.getBackgroundImageUrl());
            photo.setUploadDate(Instant.now());
            photo.setBackgroundGroup(group);
            photoRepository.save(photo);
            group.setBackgroundGroup(photo);
        }
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
                .background(groupRequest.getBackgroundImageUrl())
                .build();
    }

    @Override
    public GroupResponse getGroupById(GroupRequest groupRequest) {
        Group group = groupRepository.findById(groupRequest.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        return GroupResponse.builder()
                .title(group.getTitle())
                .description(group.getDescription())
                .background(group.getBackgroundGroup()!= null? group.getBackgroundGroup().getImageUrl() : null)
                .build();
    }

    @Override
    public List<GroupResponse> getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream()
                .map(group -> GroupResponse.builder()
                        .title(group.getTitle())
                        .description(group.getDescription())
                        .background(group.getBackgroundGroup()!= null? group.getBackgroundGroup().getImageUrl() : null)
                        .build())
                .toList();
    }

    @Override
    public GroupResponse updateGroup(Integer userId, GroupRequest groupRequest) {
        Group group = groupRepository.findById(groupRequest.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        if (!group.getCreatedBy().getId().equals(userId)) {
            throw new IllegalArgumentException("User not authorized to edit this group");
        }

        group.setTitle(groupRequest.getTitle());
        group.setDescription(groupRequest.getDescription());
        if (groupRequest.getBackgroundImageUrl() != null) {
            if (group.getBackgroundGroup() != null) {
                photoRepository.deleteById(group.getBackgroundGroup().getId());
            }
            Photo photo = new Photo();
            photo.setUserId(userId);
            photo.setImageUrl(groupRequest.getBackgroundImageUrl());
            photo.setUploadDate(Instant.now());
            photo.setBackgroundGroup(group);
            photoRepository.save(photo);
            group.setBackgroundGroup(photo);
        }
        groupRepository.save(group);

        return GroupResponse.builder()
                .title(group.getTitle())
                .description(group.getDescription())
                .background(group.getBackgroundGroup() != null ? group.getBackgroundGroup().getImageUrl() : null)
                .build();
    }

    @Override
    public void deleteGroup(Integer id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        List<GroupMember> members = groupMemberRepository.findByGroupId(id);
        for (GroupMember member : members) {
            groupMemberRepository.delete(member);
        }

        if (group.getBackgroundGroup() != null) {
            photoRepository.deleteById(group.getBackgroundGroup().getId());
        }

        groupRepository.delete(group);
    }

    @Override
    public List<SearchGroupByTitleResponse> getGroupByTitle(String title, Integer limit, Integer offset) {
        List<Object[]> results = groupRepository.getAllGroupsByTitle(title, limit, offset);
        return results.stream()
                .map(result -> SearchGroupByTitleResponse.builder()
                        .title((String) result[0])
                        .description((String) result[1])
                        .background(result[2] != null ? (String) result[2] : null)
                        .build())
                .toList();
    }

}
