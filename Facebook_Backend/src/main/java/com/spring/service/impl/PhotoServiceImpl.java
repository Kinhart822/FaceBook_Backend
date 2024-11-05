package com.spring.service.impl;

import com.spring.dto.Request.PhotoRequest;
import com.spring.dto.Response.CommonResponse;
import com.spring.entities.Photo;
import com.spring.repository.PhotoRepository;
import com.spring.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;

    private Photo findById(Integer id) {
        return photoRepository.findById(id).orElseThrow(
                () -> new RuntimeException(CommonResponse.entityNotFound().toString()));
    }

    public CommonResponse createPhoto(Integer userId, PhotoRequest photoRequest) {
        Photo photo = new Photo();
        photo.setUserId(userId);
        photo.setCaption(photoRequest.getCaption());
        photo.setImageUrl(photoRequest.getImageUrl());
        photo.setUploadDate(Instant.now());
        photoRepository.save(photo);
        return CommonResponse.success();
    }

    public List<Photo> getAllPhotos() {
        return (List<Photo>) photoRepository.findAll();
    }

    public Photo getPhoto(Integer id) {
        return this.findById(id);
    }

    public CommonResponse updatePhoto(Integer id, PhotoRequest photoRequest) {
        Photo photo = this.findById(id);
        photo.setCaption(photoRequest.getCaption());
        photo.setImageUrl(photoRequest.getImageUrl());
        photoRepository.save(photo);
        return CommonResponse.success();
    }

    public CommonResponse deletePhoto(Integer id) {
        Photo photo = this.findById(id);
        photoRepository.delete(photo);
        return CommonResponse.success();
    }
}
