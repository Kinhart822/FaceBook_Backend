package com.spring.service.impl;

import com.spring.dto.Request.EventTypeRequest;
import com.spring.dto.Response.CommonResponse;
import com.spring.entities.EventType;
import com.spring.repository.EventTypeRepository;
import com.spring.service.EventTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EventTypeServiceImpl implements EventTypeService {

    private final EventTypeRepository eventTypeRepository;

    private EventType findById(Integer id) {
        return eventTypeRepository.findById(id).orElseThrow(
                () -> new RuntimeException(CommonResponse.entityNotFound().toString()));
    }

    public CommonResponse createEventType(EventTypeRequest eventTypeRequest) {
        EventType eventType = new EventType();
        eventType.setName(eventTypeRequest.getName());
        eventTypeRepository.save(eventType);
        return CommonResponse.success();
    }

    public List<EventType> getAllEventTypes() {
        return eventTypeRepository.findAll();
    }

    public EventType getEventType(Integer id) {
        return this.findById(id);
    }

    public CommonResponse updateEventType(Integer id, EventTypeRequest eventTypeRequest) {
        EventType eventType = this.findById(id);
        eventType.setName(eventTypeRequest.getName());
        eventTypeRepository.save(eventType);
        return CommonResponse.success();
    }

    public CommonResponse deleteEventType(Integer id) {
        EventType eventType = this.findById(id);
        eventTypeRepository.delete(eventType);
        return CommonResponse.success();
    }
}
