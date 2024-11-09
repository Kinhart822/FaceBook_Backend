package com.spring.service;

import com.spring.dto.Request.PhotoRequest;
import com.spring.dto.response.CommonResponse;
import com.spring.entities.Photo;

import java.util.List;

public interface PhotoService {
    CommonResponse createPhoto(Integer userId, PhotoRequest photoRequest);
    List<Photo> getAllPhotos();
    Photo getPhoto(Integer id);
    CommonResponse deletePhoto(Integer id);
}
