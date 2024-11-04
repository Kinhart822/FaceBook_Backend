package com.spring.service;

import com.spring.dto.Request.EventRequest;
import com.spring.dto.Response.CommonResponse;
import com.spring.entities.Event;

import java.util.List;

public interface EventService {
    CommonResponse createEvent(Integer userId, EventRequest eventRequest);
    List<Event> getAllEvents();
    Event getEvent(Integer id);
    CommonResponse updateEvent(Integer id, Integer userId, EventRequest eventRequest);
    CommonResponse deleteEvent(Integer id);
}
