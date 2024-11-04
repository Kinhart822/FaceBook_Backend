package com.spring.controller;

import com.spring.dto.Request.PageCategoryRequest;
import com.spring.dto.Response.CommonResponse;
import com.spring.entities.PageCategory;
import com.spring.service.PageCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PageCategoryController {
    private final PageCategoryService pageCategoryService;

    @PostMapping("/page-category")
    public ResponseEntity<CommonResponse> createPageCategory(@RequestBody PageCategoryRequest pageCategoryRequest) {
        return ResponseEntity.ok(pageCategoryService.createPageCategory(pageCategoryRequest));
    }

    @GetMapping("/page-category")
    public List<PageCategory> getAllPageCategories() {
        return pageCategoryService.getAllPageCategories();
    }

    @GetMapping("/page-category/{id}")
    public PageCategory getPageCategory(@PathVariable Integer id) {
        return pageCategoryService.getPageCategory(id);
    }

    @PutMapping("/page-category/{id}")
    public ResponseEntity<CommonResponse> updatePageCategory(
            @PathVariable Integer id, @RequestBody PageCategoryRequest pageCategoryRequest) {
        return ResponseEntity.ok(pageCategoryService.updatePageCategory(id, pageCategoryRequest));
    }

    @DeleteMapping("/page-category/{id}")
    public ResponseEntity<CommonResponse> deletePageCategory(@PathVariable Integer id) {
        return ResponseEntity.ok(pageCategoryService.deletePageCategory(id));
    }
}
