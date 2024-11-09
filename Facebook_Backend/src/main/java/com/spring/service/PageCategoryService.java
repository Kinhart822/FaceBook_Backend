package com.spring.service;

import com.spring.dto.Request.PageCategoryRequest;
import com.spring.dto.response.CommonResponse;
import com.spring.entities.PageCategory;

import java.util.List;

public interface PageCategoryService {
    CommonResponse createPageCategory(PageCategoryRequest pageCategoryRequest);
    List<PageCategory> getAllPageCategories();
    PageCategory getPageCategory(Integer id);
    CommonResponse updatePageCategory(Integer id, PageCategoryRequest pageCategoryRequest);
    CommonResponse deletePageCategory(Integer id);
}
