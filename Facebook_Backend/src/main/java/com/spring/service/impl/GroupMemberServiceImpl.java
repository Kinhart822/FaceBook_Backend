package com.spring.service.impl;

import com.spring.dto.Request.Group.GroupMemberRequest;
import com.spring.dto.response.Group.GroupMemberResponse;
import com.spring.entities.Group;
import com.spring.entities.GroupMember;
import com.spring.entities.Role;
import com.spring.entities.User;
import com.spring.repository.GroupMemberRepository;
import com.spring.repository.GroupRepository;
import com.spring.repository.RoleRepository;
import com.spring.repository.UserRepository;
import com.spring.service.GroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GroupMemberServiceImpl implements GroupMemberService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public GroupMemberResponse addMemberToGroup(Integer userId, GroupMemberRequest groupMemberRequest) {
        Group selectedGroup = groupRepository.findById(groupMemberRequest.getSelectedGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        if (!userId.equals(selectedGroup.getCreatedBy().getId())) {
            throw new IllegalArgumentException("You do not have permission to add member to this group");
        }

        User member = userRepository.findById(groupMemberRequest.getSelectedMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        boolean isMemberExists = groupMemberRepository.existsByGroupIdAndUserId(
                groupMemberRequest.getSelectedGroupId(), groupMemberRequest.getSelectedMemberId());

        if (isMemberExists) {
            throw new IllegalArgumentException("Member is already in the group");
        }

        Role selectedRole = roleRepository.findByTitle(groupMemberRequest.getRoleTitle());
        if (selectedRole == null) {
            selectedRole = Role.builder()
                    .name(groupMemberRequest.getRoleTitle())
                    .description(groupMemberRequest.getRoleDescription())
                    .dateCreated(new Date())
                    .dateUpdated(new Date())
                    .build();
            roleRepository.save(selectedRole);
        }

        GroupMember addMember = new GroupMember();
        addMember.setGroup(selectedGroup);
        addMember.setUser(member);
        addMember.setRole(selectedRole);
        addMember.setDateCreated(new Date());
        addMember.setDateUpdated(new Date());
        addMember.setNotes(groupMemberRequest.getNotes());
        groupMemberRepository.save(addMember);

        String avatarImage = null;
        if (member.getUserAbout() != null && member.getUserAbout().getAvatar() != null) {
            avatarImage = member.getUserAbout().getAvatar().getImageUrl();
        }

        return GroupMemberResponse.builder()
                .title(selectedGroup.getTitle())
                .description(selectedGroup.getDescription())
                .avatarImage(avatarImage)
                .notes(groupMemberRequest.getNotes())
                .build();
    }

    @Override
    public List<GroupMemberResponse> getMembersByGroupId(GroupMemberRequest groupMemberRequest) {
        Group selectedGroup = groupRepository.findById(groupMemberRequest.getSelectedGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        List<GroupMember> groupMemberList = groupMemberRepository.findByGroupId(groupMemberRequest.getSelectedGroupId());
        return groupMemberList.stream()
                .map(groupMember -> {
                    String avatarImage = null;
                    if (groupMember.getUser().getUserAbout() != null && groupMember.getUser().getUserAbout().getAvatar() != null) {
                        avatarImage = groupMember.getUser().getUserAbout().getAvatar().getImageUrl();
                    }
                    return GroupMemberResponse.builder()
                            .title(selectedGroup.getTitle())
                            .description(selectedGroup.getDescription())
                            .avatarImage(avatarImage)
                            .notes(groupMember.getNotes())
                            .build();
                })
                .toList();
    }

    @Override
    public void removeMemberFromGroup(Integer userId, GroupMemberRequest groupMemberRequest) {
        Group selectedGroup = groupRepository.findById(groupMemberRequest.getSelectedGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        if (!userId.equals(selectedGroup.getCreatedBy().getId())) {
            throw new IllegalArgumentException("You do not have permission to remove member from this group");
        }
        GroupMember memberToRemove = groupMemberRepository.findById(groupMemberRequest.getSelectedMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        if ("Admin".equalsIgnoreCase(memberToRemove.getRole().getName())) {
            throw new IllegalArgumentException("Cannot remove an Admin member from the group");
        }

        groupMemberRepository.deleteById(groupMemberRequest.getSelectedMemberId());
    }
}
