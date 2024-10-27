package com.spring.service;

import com.spring.dto.Request.Group.GroupRequest;
import com.spring.dto.Response.Group.GroupResponse;
import java.util.List;

public interface GroupService {
    GroupResponse createGroup(Integer userId, GroupRequest groupRequest);
    GroupResponse getGroupById(GroupRequest groupRequest);
    List<GroupResponse> getAllGroups();
    GroupResponse updateGroup(Integer userId, GroupRequest groupRequest);
    void deleteGroup(Integer id);
}

