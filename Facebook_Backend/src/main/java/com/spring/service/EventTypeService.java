package com.spring.service;

import com.spring.dto.Request.EventTypeRequest;
import com.spring.dto.Response.CommonResponse;
import com.spring.entities.EventType;

import java.util.List;

public interface EventTypeService {
    CommonResponse createEventType(EventTypeRequest eventTypeRequest);
    List<EventType> getAllEventTypes();
    EventType getEventType(Integer id);
    CommonResponse updateEventType(Integer id, EventTypeRequest eventTypeRequest);
    CommonResponse deleteEventType(Integer id);
}
