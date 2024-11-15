package com.spring.service;

import com.spring.dto.response.CommonResponse;
import com.spring.entities.AlbumPhoto;

import java.util.List;

public interface AlbumPhotoService {
    CommonResponse createAlbumPhoto(Integer albumId, Integer photoId);
    List<AlbumPhoto> getAllAlbumPhotos();
    AlbumPhoto getAlbumPhoto(Integer albumId, Integer photoId);
    CommonResponse deleteAlbumPhoto(Integer albumId, Integer photoId);
    Integer countByAlbumId(Integer id);
}
