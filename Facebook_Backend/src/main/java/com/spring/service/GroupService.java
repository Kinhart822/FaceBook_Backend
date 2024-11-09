package com.spring.service;

import com.spring.dto.Request.Group.GroupRequest;
import com.spring.dto.response.Group.GroupResponse;
import com.spring.dto.response.Group.SearchGroupByTitleResponse;

import java.util.List;

public interface GroupService {
    GroupResponse createGroup(Integer userId, GroupRequest groupRequest);
    GroupResponse getGroupById(GroupRequest groupRequest);
    List<GroupResponse> getAllGroups();
    GroupResponse updateGroup(Integer userId, GroupRequest groupRequest);
    void deleteGroup(Integer id);

    // Search Group
    List<SearchGroupByTitleResponse> getGroupByTitle(String title, Integer limit, Integer offset);

}

