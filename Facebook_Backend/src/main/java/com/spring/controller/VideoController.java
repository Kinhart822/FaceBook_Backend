package com.spring.controller;

import com.spring.config.JwtUtil;
import com.spring.dto.Request.VideoRequest;
import com.spring.dto.response.CommonResponse;
import com.spring.dto.Response.User.VideoResponse;
import com.spring.entities.Video;
import com.spring.service.VideoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;
    private final JwtUtil jwtUtil;

    @PostMapping("/video")
    public ResponseEntity<CommonResponse> createVideo(@RequestBody VideoRequest videoRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        return ResponseEntity.ok(videoService.createVideo(userId, videoRequest));
    }

    @GetMapping("/video")
    public ResponseEntity<List<VideoResponse>> getAllVideos() {
        return ResponseEntity.ok(videoService.getAllVideos());
    }

    @GetMapping("/video/{id}")
    public Video getVideo(@PathVariable Integer id) {
        return videoService.getVideo(id);
    }

    @PutMapping("/video/{id}")
    public ResponseEntity<CommonResponse> updateVideo(@PathVariable Integer id, @RequestBody VideoRequest videoRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        return ResponseEntity.ok(videoService.updateVideo(id, userId, videoRequest));
    }

    @DeleteMapping("/video/{id}")
    public ResponseEntity<CommonResponse> deleteVideo(@PathVariable Integer id) {
        return ResponseEntity.ok(videoService.deleteVideo(id));
    }
}
