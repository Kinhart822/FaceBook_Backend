package com.spring.dto.Response;

import com.spring.enums.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonResponse {
    private int status;
    private String message;
    public static CommonResponse success() {
        return new CommonResponse(
                ApiResponse.SUCCESS.getStatus(),
                ApiResponse.SUCCESS.getMessage());
    }
    public static CommonResponse entityNotFound() {
        return new CommonResponse(
                ApiResponse.ENTITY_NOT_FOUND.getStatus(),
                ApiResponse.ENTITY_NOT_FOUND.getMessage());
    }
}
