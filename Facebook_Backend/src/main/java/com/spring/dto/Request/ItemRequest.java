package com.spring.dto.Request;

import lombok.Data;

@Data
public class ItemRequest {
    private String name;
    private String description;
    private Integer categoryId;
    private Integer photoId;
    private Integer videoId;
}
