package com.spring.service;

import com.spring.dto.Request.PageRequest;
import com.spring.dto.Response.CommonResponse;
import com.spring.entities.Page;

import java.util.List;

public interface PageService {
    CommonResponse createPage(Integer userId, PageRequest pageRequest);
    List<Page> getAllPages();
    Page getPage(Integer id);
    CommonResponse updatePage(Integer id, PageRequest pageRequest);
    CommonResponse deletePage(Integer id);
    Integer countFollowsByPageId(Integer id);
    Integer countLikesByPageId(Integer id);
}
