package com.spring.service.impl;

import com.spring.dto.Request.PageFollowerRequest;
import com.spring.dto.Response.CommonResponse;
import com.spring.entities.PageFollower;
import com.spring.entities.PageFollowerPK;
import com.spring.repository.PageFollowerRepository;
import com.spring.service.PageFollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PageFollowerServiceImpl implements PageFollowerService {

    private final PageFollowerRepository pageFollowerRepository;

    private PageFollower findById(PageFollowerPK id) {
        return pageFollowerRepository.findById(id).orElseThrow(
                () -> new RuntimeException(CommonResponse.entityNotFound().toString()));
    }

    public CommonResponse createPageFollower(Integer pageId, Integer userId, PageFollowerRequest pageFollowerRequest) {
        PageFollower pageFollower = new PageFollower();
        pageFollower.setPageFollowerPK(new PageFollowerPK(pageId, userId));
        pageFollower.setLiked(pageFollowerRequest.isLiked());
        pageFollower.setFollowed(pageFollowerRequest.isFollowed());
        pageFollowerRepository.save(pageFollower);
        return CommonResponse.success();
    }

    public List<PageFollower> getAllPageFollowers() {
        return pageFollowerRepository.findAll();
    }

    public PageFollower getPageFollower(Integer pageId, Integer userId) {
        return this.findById(new PageFollowerPK(pageId, userId));
    }

    public CommonResponse updatePageFollower(Integer pageId, Integer userId, PageFollowerRequest pageFollowerRequest) {
        PageFollower pageFollower = this.findById(new PageFollowerPK(pageId, userId));
        pageFollower.setLiked(pageFollowerRequest.isLiked());
        pageFollower.setFollowed(pageFollowerRequest.isFollowed());
        pageFollowerRepository.save(pageFollower);
        return CommonResponse.success();
    }

    public CommonResponse deletePageFollower(Integer pageId, Integer userId) {
        PageFollower pageFollower = this.findById(new PageFollowerPK(pageId, userId));
        pageFollowerRepository.delete(pageFollower);
        return CommonResponse.success();
    }
}
