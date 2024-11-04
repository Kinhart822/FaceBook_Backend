package com.spring.service.impl;

import com.spring.dto.Request.EventRequest;
import com.spring.dto.Response.CommonResponse;
import com.spring.entities.Event;
import com.spring.repository.EventRepository;
import com.spring.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private Event findById(Integer id) {
        return eventRepository.findById(id).orElseThrow(
                () -> new RuntimeException(CommonResponse.entityNotFound().toString()));
    }

    public CommonResponse createEvent(Integer userId, EventRequest eventRequest) {
        Event event = new Event();
        event.setName(eventRequest.getName());
        event.setDescription(eventRequest.getDescription());
        event.setTypeId(eventRequest.getTypeId());
        event.setPhotoId(eventRequest.getPhotoId());
        event.setVideoId(eventRequest.getVideoId());
        event.setStartDate(eventRequest.getStartDate());
        event.setEndDate(eventRequest.getEndDate());
        event.setCreatedBy(userId);
        event.setUpdatedBy(userId);
        eventRepository.save(event);
        return CommonResponse.success();
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEvent(Integer id) {
        return this.findById(id);
    }

    public CommonResponse updateEvent(Integer id, Integer userId, EventRequest eventRequest) {
        Event event = this.findById(id);
        event.setName(eventRequest.getName());
        event.setDescription(eventRequest.getDescription());
        event.setTypeId(eventRequest.getTypeId());
        event.setPhotoId(eventRequest.getPhotoId());
        event.setVideoId(eventRequest.getVideoId());
        event.setStartDate(eventRequest.getStartDate());
        event.setEndDate(eventRequest.getEndDate());
        event.setUpdatedBy(userId);
        eventRepository.save(event);
        return CommonResponse.success();
    }

    public CommonResponse deleteEvent(Integer id) {
        Event event = this.findById(id);
        eventRepository.delete(event);
        return CommonResponse.success();
    }
}
