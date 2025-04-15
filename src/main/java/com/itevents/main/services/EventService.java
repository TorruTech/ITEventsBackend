package com.itevents.main.services;

import com.itevents.main.models.EventModel;
import com.itevents.main.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<EventModel> getEvents() {
        return eventRepository.findAll();
    }

    public Optional<EventModel> getById(Long id) {
        return eventRepository.findById(id);
    }

    public List<EventModel> getEventsByLocationId(Long locationId) {
        return eventRepository.findByLocation_Id(locationId);
    }

    public EventModel saveEvent(EventModel event) {
        return eventRepository.save(event);
    }

    public Optional<EventModel> updateById(Long id, EventModel request) {
        return eventRepository.findById(id).map(event -> {
            event.setName(request.getName());
            event.setDate(request.getDate());
            event.setDescription(request.getDescription());
            event.setLabels(request.getLabels());
            event.setDateDescription(request.getDateDescription());
            event.setImageUrl(request.getImageUrl());
            event.setCategory(request.getCategory());
            event.setLocation(request.getLocation());
            return eventRepository.save(event);
        });
    }

    public boolean deleteEvent(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
