package com.spring.service;

import com.spring.dto.Response.CommonResponse;

import java.util.List;

public interface PhotoTagService {
    CommonResponse createPhotoTag(Integer photoId, Integer taggedUserId);
    List<PhotoTag> getAllPhotoTags();
    PhotoTag getPhotoTag(Integer photoId, Integer taggedUserId);
    CommonResponse deletePhotoTag(Integer photoId, Integer taggedUserId);
}
