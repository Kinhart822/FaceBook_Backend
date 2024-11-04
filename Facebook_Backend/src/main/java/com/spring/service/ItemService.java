package com.spring.service;

import com.spring.dto.Request.ItemRequest;
import com.spring.dto.Response.CommonResponse;
import com.spring.entities.Item;

import java.util.List;

public interface ItemService {
    CommonResponse createItem(Integer userId, ItemRequest itemRequest);
    List<Item> getAllItems();
    Item getItem(Integer id);
    CommonResponse updateItem(Integer id, Integer userId, ItemRequest itemRequest);
    CommonResponse deleteItem(Integer id);
}
