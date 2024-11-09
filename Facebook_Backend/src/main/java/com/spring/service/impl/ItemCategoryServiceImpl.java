package com.spring.service.impl;

import com.spring.dto.Request.ItemCategoryRequest;
import com.spring.dto.response.CommonResponse;
import com.spring.entities.ItemCategory;
import com.spring.repository.ItemCategoryRepository;
import com.spring.service.ItemCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemCategoryServiceImpl implements ItemCategoryService {

    private final ItemCategoryRepository itemCategoryRepository;

    private ItemCategory findById(Integer id) {
        return itemCategoryRepository.findById(id).orElseThrow(
                () -> new RuntimeException(CommonResponse.entityNotFound().toString()));
    }

    public CommonResponse createItemCategory(ItemCategoryRequest itemCategoryRequest) {
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setName(itemCategoryRequest.getName());
        itemCategoryRepository.save(itemCategory);
        return CommonResponse.success();
    }

    public List<ItemCategory> getAllItemCategories() {
        return itemCategoryRepository.findAll();
    }

    public ItemCategory getItemCategory(Integer id) {
        return this.findById(id);
    }

    public CommonResponse updateItemCategory(Integer id, ItemCategoryRequest itemCategoryRequest) {
        ItemCategory itemCategory = this.findById(id);
        itemCategory.setName(itemCategoryRequest.getName());
        itemCategoryRepository.save(itemCategory);
        return CommonResponse.success();
    }

    public CommonResponse deleteItemCategory(Integer id) {
        ItemCategory itemCategory = this.findById(id);
        itemCategoryRepository.delete(itemCategory);
        return CommonResponse.success();
    }
}
