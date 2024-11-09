package com.spring.service.impl;

import com.spring.dto.Request.AlbumRequest;
import com.spring.dto.response.CommonResponse;
import com.spring.entities.Album;
import com.spring.repository.AlbumRepository;
import com.spring.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;

    private Album findById(Integer id) {
        return albumRepository.findById(id).orElseThrow(
                () -> new RuntimeException(CommonResponse.entityNotFound().toString()));
    }

    public CommonResponse createAlbum(AlbumRequest albumRequest) {
        Album album = new Album();
        album.setName(albumRequest.getName());
        album.setDescription(albumRequest.getDescription());
        Instant now = Instant.now();
        album.setDateCreated(now);
        album.setDateUpdated(now);
        albumRepository.save(album);
        return CommonResponse.success();
    }

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    public Album getAlbum(Integer id) {
        return this.findById(id);
    }

    public CommonResponse updateAlbum(Integer id, AlbumRequest albumRequest) {
        Album album = this.findById(id);
        album.setName(albumRequest.getName());
        album.setDescription(albumRequest.getDescription());
        album.setDateUpdated(Instant.now());
        albumRepository.save(album);
        return CommonResponse.success();
    }

    public CommonResponse deleteAlbum(Integer id) {
        Album album = this.findById(id);
        albumRepository.delete(album);
        return CommonResponse.success();
    }
}
