package com.spring.service.impl;

import com.spring.dto.Response.CommonResponse;
import com.spring.repository.PhotoTagRepository;
import com.spring.service.PhotoTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PhotoTagServiceImpl implements PhotoTagService {

    private final PhotoTagRepository photoTagRepository;

    private PhotoTag findById(PhotoTagPK id) {
        return photoTagRepository.findById(id).orElseThrow(
                () -> new RuntimeException(CommonResponse.entityNotFound().toString()));
    }

    public CommonResponse createPhotoTag(Integer photoId, Integer taggedUserId) {
        PhotoTag photoTag = new PhotoTag();
        photoTag.setPhotoTagPK(new PhotoTagPK(photoId, taggedUserId));
        photoTagRepository.save(photoTag);
        return CommonResponse.success();
    }

    public List<PhotoTag> getAllPhotoTags() {
        return photoTagRepository.findAll();
    }

    public PhotoTag getPhotoTag(Integer photoId, Integer taggedUserId) {
        return this.findById(new PhotoTagPK(photoId, taggedUserId));
    }

    public CommonResponse deletePhotoTag(Integer photoId, Integer taggedUserId) {
        PhotoTag photoTag = this.findById(new PhotoTagPK(photoId, taggedUserId));
        photoTagRepository.delete(photoTag);
        return CommonResponse.success();
    }
}
