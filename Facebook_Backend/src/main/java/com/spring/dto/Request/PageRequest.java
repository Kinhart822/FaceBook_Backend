package com.spring.dto.Request;

import lombok.Data;

@Data
public class PageRequest {
    private Integer categoryId;
    private String avatarUrl;
    private String backgroundUrl;
    private String address;
    private String mobile;
    private String email;
    private String instagram;
}
