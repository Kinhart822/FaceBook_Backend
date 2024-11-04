package com.spring.service.impl;

import com.spring.dto.Response.CommonResponse;
import com.spring.entities.PageFollower;
import com.spring.entities.PageFollowerPK;
import com.spring.repository.PageFollowerRepository;
import com.spring.service.PageFollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PageFollowerServiceImpl implements PageFollowerService {

    private final PageFollowerRepository pageFollowerRepository;

    private PageFollower findById(Integer pageId, Integer userId) {
        PageFollowerPK pageFollowerPK = new PageFollowerPK(pageId, userId);
        return pageFollowerRepository.findById(pageFollowerPK).orElseThrow(
                () -> new RuntimeException(CommonResponse.entityNotFound().toString()));
    }
    private PageFollower findByIdOrCreateNew(Integer pageId, Integer userId) {
        PageFollowerPK pageFollowerPK = new PageFollowerPK(pageId, userId);
        PageFollower pageFollower = new PageFollower();
        pageFollower.setPageFollowerPK(pageFollowerPK);
        Optional<PageFollower> optPageFollower = pageFollowerRepository.findById(pageFollowerPK);
        if (optPageFollower.isPresent()) {
            pageFollower = optPageFollower.get();
        }
        return pageFollower;
    }
    public CommonResponse followPage(Integer pageId, Integer userId) {
        PageFollower pageFollower = findByIdOrCreateNew(pageId, userId);
        pageFollower.setFollowed(true);
        pageFollowerRepository.save(pageFollower);
        return CommonResponse.success();
    }
    public CommonResponse likePage(Integer pageId, Integer userId) {
        PageFollower pageFollower = findByIdOrCreateNew(pageId, userId);;
        pageFollower.setLiked(true);
        pageFollowerRepository.save(pageFollower);
        return CommonResponse.success();
    }
    public CommonResponse unfollowPage(Integer pageId, Integer userId) {
        PageFollower pageFollower = findById(pageId, userId);
        pageFollower.setFollowed(false);
        pageFollowerRepository.save(pageFollower);
        return CommonResponse.success();
    }
    public CommonResponse unlikePage(Integer pageId, Integer userId) {
        PageFollower pageFollower = findById(pageId, userId);;
        pageFollower.setLiked(false);
        pageFollowerRepository.save(pageFollower);
        return CommonResponse.success();
    }
}
