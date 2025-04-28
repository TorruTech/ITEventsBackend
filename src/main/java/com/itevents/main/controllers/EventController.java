package com.itevents.main.controllers;

import com.itevents.main.models.EventModel;
import com.itevents.main.repositories.EventRepository;
import com.itevents.main.services.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Event", description = "Endpoints para gestionar eventos")
@RestController
@RequestMapping("/api/events")
@Validated
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(summary = "Devuelve todos los eventos")
    @GetMapping
    public List<EventModel> getEvents() {
        return eventService.getEvents();
    }

    @Operation(summary = "Devuelve un evento por id")
    @GetMapping("/{id}")
    public Optional<EventModel> getEventById(@PathVariable Long id) {
        return eventService.getById(id);
    }

    @Operation(summary = "Devuelve los eventos de una ubicacion")
    @GetMapping("/location/{locationId}")
    public List<EventModel> getEventsByLocation(@PathVariable Long locationId) {
        return eventService.getEventsByLocationId(locationId);
    }

    @Operation(summary = "Devuelve la cantidad de eventos por ubicacion")
    @GetMapping("/count-by-location")
    public List<EventRepository.LocationEventCount> countByLocation() {
        return eventService.getEventCountsByLocation();
    }

    @Operation(summary = "Inserta un evento")
    @PostMapping
    public EventModel createEvent(@Valid @RequestBody EventModel event) {
        return eventService.saveEvent(event);
    }

    @Operation(summary = "Actualiza un evento por id")
    @PutMapping("/{id}")
    public Optional<EventModel> updateEvent(@PathVariable Long id, @Valid @RequestBody EventModel event) {
        return eventService.updateById(id, event);
    }

    @Operation(summary = "Elimina un evento por id")
    @DeleteMapping("/{id}")
    public boolean deleteEvent(@PathVariable Long id) {
        return eventService.deleteEvent(id);
    }
}
