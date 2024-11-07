package com.spring.controller;

import com.spring.config.JwtUtil;
import com.spring.dto.Request.PhotoRequest;
import com.spring.dto.Response.CommonResponse;
import com.spring.entities.Photo;
import com.spring.service.PhotoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PhotoController {
    private final PhotoService photoService;
    private final JwtUtil jwtUtil;

    @PostMapping("/photo")
    public ResponseEntity<CommonResponse> createPhoto(@RequestBody PhotoRequest photoRequest, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        return ResponseEntity.ok(photoService.createPhoto(userId, photoRequest));
    }
    @GetMapping("/photo")
    public List<Photo> getAllPhotos() {
        return photoService.getAllPhotos();
    }
    @GetMapping("/photo/{id}")
    public Photo getPhoto(@PathVariable Integer id) {
        return photoService.getPhoto(id);
    }
    @DeleteMapping("/photo/{id}")
    public ResponseEntity<CommonResponse> deletePhoto(@PathVariable Integer id) {
        return ResponseEntity.ok(photoService.deletePhoto(id));
    }
}
