package com.spring.service;

import com.spring.dto.Request.AlbumRequest;
import com.spring.dto.response.CommonResponse;
import com.spring.entities.Album;

import java.util.List;

public interface AlbumService {
    CommonResponse createAlbum(AlbumRequest albumRequest);
    List<Album> getAllAlbums();
    Album getAlbum(Integer id);
    CommonResponse updateAlbum(Integer id, AlbumRequest albumRequest);
    CommonResponse deleteAlbum(Integer id);
}
