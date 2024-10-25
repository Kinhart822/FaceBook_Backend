package com.spring.service;

import com.spring.dto.Request.User.UserAboutRequest;
import com.spring.dto.Response.User.SearchUserByUserNameResponse;
import com.spring.dto.Response.User.UserAboutResponse;

import java.util.List;

public interface UserAboutService {
    List<UserAboutResponse> findAll();
    UserAboutResponse findById(Integer id);
    UserAboutResponse save(UserAboutRequest userAboutRequest, Integer userId);
    void deleteById(Integer id);
}
