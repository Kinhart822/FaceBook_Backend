package com.spring.controller;

import com.spring.config.JwtUtil;
import com.spring.dto.Request.PageRequest;
import com.spring.dto.response.CommonResponse;
import com.spring.dto.response.User.UserProjection;
import com.spring.entities.Page;
import com.spring.service.PageService;
import com.spring.service.impl.PageFollowerServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PageController {
    private final PageService pageService;
    private final JwtUtil jwtUtil;
    private final PageFollowerServiceImpl pageFollowerService;

    // create a new page
    @PostMapping("/page")
    public ResponseEntity<CommonResponse> createPage(
            @RequestBody PageRequest pageRequest,
            HttpServletRequest request) throws IOException {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        return ResponseEntity.ok(pageService.createPage(userId, pageRequest));
    }
    // list all page
    @GetMapping("/page")
    public List<Page> findAllByNameContains(@RequestParam(required = false) String name) {
        return (name == null) ? pageService.findAll() : pageService.findAllByNameContains(name);
    }
    // get a page
    @GetMapping("/page/{id}")
    public Page getPage(@PathVariable Integer id) {
        return pageService.getPage(id);
    }
    // update a page
    @PutMapping("/page/{id}")
    public ResponseEntity<CommonResponse> updatePage(@PathVariable Integer id,
                                                     @RequestBody PageRequest pageRequest) throws IOException {
        return ResponseEntity.ok(pageService.updatePage(id, pageRequest));
    }
    // delete a page
    @DeleteMapping("/page/{id}")
    public ResponseEntity<CommonResponse> deletePage(@PathVariable Integer id) {
        return ResponseEntity.ok(pageService.deletePage(id));
    }
    // count follow of a page
    @GetMapping("/page/{id}/follow/count")
    public Integer countFollowsByPageId(@PathVariable Integer id) {
        return pageService.countFollowsByPageId(id);
    }
    // count like of a page
    @GetMapping("/page/{id}/like/count")
    public Integer countLikesByPageId(@PathVariable Integer id) {
        return pageService.countLikesByPageId(id);
    }
    // current user follow page
    @PostMapping("/page/{id}/follow")
    public ResponseEntity<CommonResponse> followPage(
            @PathVariable Integer id, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        return ResponseEntity.ok(pageFollowerService.followPage(id, userId));
    }
    // current user like page
    @PostMapping("/page/{id}/like")
    public ResponseEntity<CommonResponse> likePage(
            @PathVariable Integer id, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        return ResponseEntity.ok(pageFollowerService.likePage(id, userId));
    }
    // current user unfollow page
    @PostMapping("/page/{id}/follow/delete")
    public ResponseEntity<CommonResponse> unfollowPage(
            @PathVariable Integer id, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        return ResponseEntity.ok(pageFollowerService.unfollowPage(id, userId));
    }
    // current user unlike page
    @PostMapping("/page/{id}/like/delete")
    public ResponseEntity<CommonResponse> unlikePage(
            @PathVariable Integer id, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        return ResponseEntity.ok(pageFollowerService.unlikePage(id, userId));
    }
    // list all page followers
    @GetMapping("/page/{id}/follow/all-users")
    public List<UserProjection> getPageFollowers(@PathVariable Integer id) {
        return pageService.getPageFollowers(id);
    }
    // list all page likers
    @GetMapping("/page/{id}/like/all-users")
    public List<UserProjection> getPageLikers(@PathVariable Integer id) {
        return pageService.getPageLikers(id);
    }
}
