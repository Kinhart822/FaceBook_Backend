package com.spring.service.impl;

import com.spring.dto.Response.User.SearchUserByNameResponse;
import com.spring.dto.Response.User.SearchUserByUserNameResponse;
import com.spring.entities.User;
import com.spring.enums.EducationLevel;
import com.spring.enums.Gender;
import com.spring.enums.Relationship;
import com.spring.repository.UserRepository;
import com.spring.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService(){
            @Override
            public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) {
                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(user.getUserType().name()));

                return new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        authorities
                );
            }
        };
    }

    @Override
    public List<SearchUserByUserNameResponse> getUsersByUserName(String userName, Integer currentUserId, Integer limit, Integer offset) {
        List<Object[]> results = userRepository.getAllUsersByUserName(userName, currentUserId, limit, offset);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        return results.stream()
                .map(result -> new SearchUserByUserNameResponse(
                        (String) result[0],     // firstName
                        (String) result[1],     // lastName
                        (String) result[2],     // email
                        (String) result[3],     // phone
                        (Date) result[4],       // dateOfBirth
                        (String) result[5]      // userName
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<SearchUserByNameResponse> getUsersByFirstName(String firstName, Integer currentUserId, Integer limit, Integer offset) {
        List<Object[]> results = userRepository.getAllUsersByFirstName(firstName, currentUserId, limit, offset);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        return results.stream()
                .map(result -> new SearchUserByNameResponse(
                        (String) result[0],     // firstName
                        (String) result[1],     // lastName
                        (String) result[2],     // email
                        (String) result[3],     // phone
                        (Date) result[4]        // dateOfBirth
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<SearchUserByNameResponse> getUsersByLastName(String lastName, Integer currentUserId, Integer limit, Integer offset) {
        List<Object[]> results = userRepository.getAllUsersByLastName(lastName, currentUserId, limit, offset);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        return results.stream()
                .map(result -> new SearchUserByNameResponse(
                        (String) result[0],     // firstName
                        (String) result[1],     // lastName
                        (String) result[2],     // email
                        (String) result[3],     // phone
                        (Date) result[4]        // dateOfBirth
                ))
                .collect(Collectors.toList());
    }
}
