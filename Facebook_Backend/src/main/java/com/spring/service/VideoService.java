package com.spring.service;

import com.spring.dto.Request.VideoRequest;
import com.spring.dto.response.CommonResponse;
import com.spring.dto.response.User.VideoResponse;
import com.spring.entities.Video;

import java.util.List;

public interface VideoService {
    CommonResponse createVideo(Integer userId, VideoRequest videoRequest);
    List<VideoResponse> getAllVideos();
    Video getVideo(Integer id);
    CommonResponse updateVideo(Integer id, Integer userId, VideoRequest videoRequest);
    CommonResponse deleteVideo(Integer id);
}
