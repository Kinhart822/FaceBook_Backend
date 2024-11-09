package com.spring.service;

import com.spring.dto.Request.PageRequest;
import com.spring.dto.response.CommonResponse;
import com.spring.dto.response.User.UserProjection;
import com.spring.entities.Page;

import java.util.List;

public interface PageService {
    CommonResponse createPage(Integer userId, PageRequest pageRequest);
    List<Page> findAll();
    List<Page> findAllByNameContains(String name);
    Page getPage(Integer id);
    CommonResponse updatePage(Integer id, PageRequest pageRequest);
    CommonResponse deletePage(Integer id);
    Integer countFollowsByPageId(Integer id);
    Integer countLikesByPageId(Integer id);
    List<UserProjection> getPageFollowers(Integer pageId);
    List<UserProjection> getPageLikers(Integer pageId);
}
