package com.spring.service.impl;

import com.spring.dto.response.CommonResponse;
import com.spring.entities.AlbumPhoto;
import com.spring.entities.AlbumPhotoPK;
import com.spring.repository.AlbumPhotoRepository;
import com.spring.service.AlbumPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AlbumPhotoServiceImpl implements AlbumPhotoService {

    private final AlbumPhotoRepository albumPhotoRepository;

    private AlbumPhoto findById(AlbumPhotoPK id) {
        return albumPhotoRepository.findById(id).orElseThrow(
                () -> new RuntimeException(CommonResponse.entityNotFound().toString()));
    }

    public CommonResponse createAlbumPhoto(Integer albumId, Integer photoId) {
        AlbumPhoto albumPhoto = new AlbumPhoto();
        albumPhoto.setAlbumPhotoPK(new AlbumPhotoPK(albumId, photoId));
        albumPhotoRepository.save(albumPhoto);
        return CommonResponse.success();
    }

    public List<AlbumPhoto> getAllAlbumPhotos() {
        return albumPhotoRepository.findAll();
    }

    public AlbumPhoto getAlbumPhoto(Integer albumId, Integer photoId) {
        return this.findById(new AlbumPhotoPK(albumId, photoId));
    }

    public CommonResponse deleteAlbumPhoto(Integer albumId, Integer photoId) {
        AlbumPhoto albumPhoto = this.findById(new AlbumPhotoPK(albumId, photoId));
        albumPhotoRepository.delete(albumPhoto);
        return CommonResponse.success();
    }
    public Integer countByAlbumId(Integer id){
        return albumPhotoRepository.countByAlbumId(id);
    }
}
