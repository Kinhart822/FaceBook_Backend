package com.spring.service;

import com.spring.dto.Response.User.SearchUserByNameResponse;
import com.spring.dto.Response.User.SearchUserByUserNameResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();
    // Search User
    List<SearchUserByUserNameResponse> getUsersByUserName(String userName, Integer currentUserId, Integer limit, Integer offset);
    List<SearchUserByNameResponse> getUsersByFirstName(String firstName, Integer currentUserId, Integer limit, Integer offset);
    List<SearchUserByNameResponse> getUsersByLastName(String lastName, Integer currentUserId, Integer limit, Integer offset);
}
