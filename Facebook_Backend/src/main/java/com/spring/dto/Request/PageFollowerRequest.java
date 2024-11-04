package com.spring.dto.Request;

import lombok.Data;

@Data
public class PageFollowerRequest {
    private boolean liked;
    private boolean followed;
}
