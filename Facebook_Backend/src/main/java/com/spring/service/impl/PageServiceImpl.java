package com.spring.service.impl;

import com.spring.dto.Request.PageRequest;
import com.spring.dto.Response.CommonResponse;
import com.spring.dto.Response.User.UserProjection;
import com.spring.entities.Page;
import com.spring.repository.PageFollowerRepository;
import com.spring.repository.PageRepository;
import com.spring.service.PageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;
    private final PageFollowerRepository pageFollowerRepository;

    private Page findById(Integer id) {
        return pageRepository.findById(id).orElseThrow(
                () -> new RuntimeException(CommonResponse.entityNotFound().toString()));
    }

    public CommonResponse createPage(Integer userId, PageRequest pageRequest) {
        Page page = new Page();
        page.setName(pageRequest.getName());
        page.setCategoryId(pageRequest.getCategoryId());
        page.setOwnedBy(userId);
        page.setAvatarUrl(pageRequest.getAvatarUrl());
        page.setBackgroundUrl(pageRequest.getBackgroundUrl());
        page.setAddress(pageRequest.getAddress());
        page.setMobile(pageRequest.getMobile());
        page.setEmail(pageRequest.getEmail());
        page.setInstagram(pageRequest.getInstagram());
        pageRepository.save(page);
        return CommonResponse.success();
    }
    public List<Page> getAllPages() {
        return pageRepository.findAll();
    }
    public Page getPage(Integer id) {
        return this.findById(id);
    }
    public CommonResponse updatePage(Integer id, PageRequest pageRequest) {
        Page page = this.findById(id);
        page.setName(pageRequest.getName());
        page.setCategoryId(pageRequest.getCategoryId());
        page.setAvatarUrl(pageRequest.getAvatarUrl());
        page.setBackgroundUrl(pageRequest.getBackgroundUrl());
        page.setAddress(pageRequest.getAddress());
        page.setMobile(pageRequest.getMobile());
        page.setEmail(pageRequest.getEmail());
        page.setInstagram(pageRequest.getInstagram());
        pageRepository.save(page);
        return CommonResponse.success();
    }
    public CommonResponse deletePage(Integer id) {
        Page page = this.findById(id);
        pageRepository.delete(page);
        return CommonResponse.success();
    }
    public Integer countFollowsByPageId(Integer id) {
        return pageFollowerRepository.countFollowsByPageId(id);
    }
    public Integer countLikesByPageId(Integer id) {
        return pageFollowerRepository.countLikesByPageId(id);
    }
    public List<UserProjection> getPageFollowers(Integer id) {
        return pageFollowerRepository.getPageFollowers(id);
    }
    public List<UserProjection> getPageLikers(Integer id) {
        return pageFollowerRepository.getPageLikers(id);
    }
}
