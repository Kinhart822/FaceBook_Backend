package com.spring.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiResponse {
    SUCCESS(200, "OK"),
    BAD_REQUEST(400, "Bad Request"),
    ENTITY_NOT_FOUND(404, "Entity Not Found"),;
    private final int status;
    private final String message;
}
