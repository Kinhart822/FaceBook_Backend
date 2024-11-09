package com.spring.service.impl;

import com.spring.dto.Request.VideoRequest;
import com.spring.dto.response.CommonResponse;
import com.spring.entities.Video;
import com.spring.repository.VideoRepository;
import com.spring.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;

    private Video findById(Integer id) {
        return videoRepository.findById(id).orElseThrow(
                () -> new RuntimeException(CommonResponse.entityNotFound().toString()));
    }

    public CommonResponse createVideo(Integer userId, VideoRequest videoRequest) {
        Video video = new Video();
        video.setName(videoRequest.getName());
        video.setDescription(videoRequest.getDescription());
        video.setLength(videoRequest.getLength());
        video.setVideoUrl(videoRequest.getVideoUrl());
        video.setCreatedBy(userId);
        video.setUpdatedBy(userId);
        Instant now = Instant.now();
        video.setDateCreated(now);
        video.setDateUpdated(now);
        videoRepository.save(video);
        return CommonResponse.success();
    }

    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    public Video getVideo(Integer id) {
        return this.findById(id);
    }

    public CommonResponse updateVideo(Integer id, Integer userId, VideoRequest videoRequest) {
        Video video = this.findById(id);
        video.setName(videoRequest.getName());
        video.setDescription(videoRequest.getDescription());
        video.setLength(videoRequest.getLength());
        video.setVideoUrl(videoRequest.getVideoUrl());
        video.setUpdatedBy(userId);
        video.setDateUpdated(Instant.now());
        videoRepository.save(video);
        return CommonResponse.success();
    }

    public CommonResponse deleteVideo(Integer id) {
        Video video = this.findById(id);
        videoRepository.delete(video);
        return CommonResponse.success();
    }
}
