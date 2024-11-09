package com.spring.service;

import com.spring.dto.Request.User.UserAboutRequest;
import com.spring.dto.Response.User.UserAboutResponse;

import java.util.List;

public interface UserAboutService {
    List<UserAboutResponse> findAll();
    UserAboutResponse findById(Integer id);
    UserAboutResponse findByUser(Integer userId);
    UserAboutResponse save(UserAboutRequest userAboutRequest, Integer userId);
}
