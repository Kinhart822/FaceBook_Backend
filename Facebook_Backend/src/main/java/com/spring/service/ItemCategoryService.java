package com.spring.service;

import com.spring.dto.Request.ItemCategoryRequest;
import com.spring.dto.Response.CommonResponse;
import com.spring.entities.ItemCategory;

import java.util.List;

public interface ItemCategoryService {
    CommonResponse createItemCategory(ItemCategoryRequest itemCategoryRequest);
    List<ItemCategory> getAllItemCategories();
    ItemCategory getItemCategory(Integer id);
    CommonResponse updateItemCategory(Integer id, ItemCategoryRequest itemCategoryRequest);
    CommonResponse deleteItemCategory(Integer id);
}
