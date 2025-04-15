package com.itevents.main.controllers;

import com.itevents.main.models.EventModel;
import com.itevents.main.services.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventModel> getEvents() {
        return eventService.getEvents();
    }

    @GetMapping("/{id}")
    public Optional<EventModel> getEventById(@PathVariable Long id) {
        return eventService.getById(id);
    }

    @PostMapping
    public EventModel createEvent(@RequestBody EventModel event) {
        return eventService.saveEvent(event);
    }

    @PutMapping("/{id}")
    public Optional<EventModel> updateEvent(@PathVariable Long id, @RequestBody EventModel event) {
        return eventService.updateById(id, event);
    }

    @DeleteMapping("/{id}")
    public boolean deleteEvent(@PathVariable Long id) {
        return eventService.deleteEvent(id);
    }
}
