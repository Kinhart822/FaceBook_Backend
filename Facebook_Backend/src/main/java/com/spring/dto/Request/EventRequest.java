package com.spring.dto.Request;

import lombok.Data;

import java.time.Instant;

@Data
public class EventRequest {
    private String name;
    private String description;
    private Integer typeId;
    private Integer photoId;
    private Integer videoId;
    private Instant startDate;
    private Instant endDate;
}
