package com.spring.service.impl;

import com.spring.dto.Request.PageCategoryRequest;
import com.spring.dto.Response.CommonResponse;
import com.spring.entities.PageCategory;
import com.spring.repository.PageCategoryRepository;
import com.spring.service.PageCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PageCategoryServiceImpl implements PageCategoryService {

    private final PageCategoryRepository pageCategoryRepository;

    private PageCategory findById(Integer id) {
        return pageCategoryRepository.findById(id).orElseThrow(
                () -> new RuntimeException(CommonResponse.entityNotFound().toString()));
    }

    public CommonResponse createPageCategory(PageCategoryRequest pageCategoryRequest) {
        PageCategory pageCategory = new PageCategory();
        pageCategory.setName(pageCategoryRequest.getName());
        pageCategoryRepository.save(pageCategory);
        return CommonResponse.success();
    }

    public List<PageCategory> getAllPageCategories() {
        return (List<PageCategory>) pageCategoryRepository.findAll();
    }

    public PageCategory getPageCategory(Integer id) {
        return this.findById(id);
    }

    public CommonResponse updatePageCategory(Integer id, PageCategoryRequest pageCategoryRequest) {
        PageCategory pageCategory = this.findById(id);
        pageCategory.setName(pageCategoryRequest.getName());
        pageCategoryRepository.save(pageCategory);
        return CommonResponse.success();
    }

    public CommonResponse deletePageCategory(Integer id) {
        PageCategory pageCategory = this.findById(id);
        pageCategoryRepository.delete(pageCategory);
        return CommonResponse.success();
    }
}
