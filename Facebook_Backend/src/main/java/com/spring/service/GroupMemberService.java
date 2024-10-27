package com.spring.service;

import com.spring.dto.Request.Group.GroupMemberRequest;
import com.spring.dto.Response.Group.GroupMemberResponse;

import java.util.List;

public interface GroupMemberService {
    GroupMemberResponse addMemberToGroup(Integer userId, GroupMemberRequest groupMemberRequest);
    List<GroupMemberResponse> getMembersByGroupId(GroupMemberRequest groupMemberRequest);
    void removeMemberFromGroup(Integer userId, GroupMemberRequest groupMemberRequest);
}
