package com.spring.service;

import com.spring.dto.Response.CommonResponse;

public interface PageFollowerService {
    CommonResponse followPage(Integer pageId, Integer userId);
    CommonResponse likePage(Integer pageId, Integer userId);
    CommonResponse unfollowPage(Integer pageId, Integer userId);
    CommonResponse unlikePage(Integer pageId, Integer userId);
}
