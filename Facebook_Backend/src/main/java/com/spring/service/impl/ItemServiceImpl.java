package com.spring.service.impl;

import com.spring.dto.Request.ItemRequest;
import com.spring.dto.response.CommonResponse;
import com.spring.entities.Item;
import com.spring.repository.ItemRepository;
import com.spring.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private Item findById(Integer id) {
        return itemRepository.findById(id).orElseThrow(
                () -> new RuntimeException(CommonResponse.entityNotFound().toString()));
    }

    public CommonResponse createItem(Integer userId, ItemRequest itemRequest) {
        Item item = new Item();
        item.setName(itemRequest.getName());
        item.setDescription(itemRequest.getDescription());
        item.setCategoryId(itemRequest.getCategoryId());
        item.setPhotoId(itemRequest.getPhotoId());
        item.setVideoId(itemRequest.getVideoId());
        item.setCreatedBy(userId);
        item.setUpdatedBy(userId);
        Instant now = Instant.now();
        item.setDateCreated(now);
        item.setDateUpdated(now);
        itemRepository.save(item);
        return CommonResponse.success();
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItem(Integer id) {
        return this.findById(id);
    }

    public CommonResponse updateItem(Integer id, Integer userId, ItemRequest itemRequest) {
        Item item = this.findById(id);
        item.setName(itemRequest.getName());
        item.setDescription(itemRequest.getDescription());
        item.setCategoryId(itemRequest.getCategoryId());
        item.setPhotoId(itemRequest.getPhotoId());
        item.setVideoId(itemRequest.getVideoId());
        item.setUpdatedBy(userId);
        item.setDateUpdated(Instant.now());
        itemRepository.save(item);
        return CommonResponse.success();
    }

    public CommonResponse deleteItem(Integer id) {
        Item item = this.findById(id);
        itemRepository.delete(item);
        return CommonResponse.success();
    }
}
