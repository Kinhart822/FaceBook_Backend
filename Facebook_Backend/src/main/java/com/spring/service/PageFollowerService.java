package com.spring.service;

import com.spring.dto.Request.PageFollowerRequest;
import com.spring.dto.Response.CommonResponse;
import com.spring.entities.PageFollower;

import java.util.List;

public interface PageFollowerService {
    CommonResponse createPageFollower(Integer pageId, Integer userId, PageFollowerRequest pageFollowerRequest);
    List<PageFollower> getAllPageFollowers();
    PageFollower getPageFollower(Integer pageId, Integer userId);
    CommonResponse updatePageFollower(Integer pageId, Integer userId, PageFollowerRequest pageFollowerRequest);
    CommonResponse deletePageFollower(Integer pageId, Integer userId);
}
