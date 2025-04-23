package com.itevents.main.controllers;

import com.itevents.main.models.RegistrationModel;
import com.itevents.main.models.UserModel;
import com.itevents.main.models.EventModel;
import com.itevents.main.services.RegistrationService;
import com.itevents.main.repositories.EventRepository;
import com.itevents.main.repositories.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public RegistrationController(RegistrationService registrationService, UserRepository userRepository, EventRepository eventRepository) {
        this.registrationService = registrationService;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    // Create a new registration
    @PostMapping
    public ResponseEntity<?> create(@RequestBody RegistrationModel registration) {
        Optional<UserModel> user = userRepository.findById(registration.getUser().getId());
        Optional<EventModel> event = eventRepository.findById(registration.getEvent().getId());

        if (user.isEmpty() || event.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuario o evento no encontrado");
        }

        if (registrationService.existsByUserAndEvent(user.get(), event.get())) {
            return ResponseEntity.ok("Ya existe una reserva para este evento");
        }

        if (event.get().isRequiresTicket() && (event.get().getAvailableTickets() == null || event.get().getAvailableTickets() <= 0)) {
            return ResponseEntity.badRequest().body("No hay entradas disponibles para este evento");
        }

        int ticketsReserved = registration.getTicketsReserved();

        return ResponseEntity.ok(registrationService.createReservation(user.get(), event.get(), ticketsReserved));
    }

    // Delete a registration
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = registrationService.deleteRegistration(id);
        if (deleted) {
            return ResponseEntity.ok("Reserva eliminada");
        }
        return ResponseEntity.notFound().build();
    }

    // Get all registrations for a user
    @GetMapping("/user/{userId}")
    public List<EventModel> getByUser(@PathVariable Long userId) {
        return registrationService.getEventsByUserId(userId);
    }

    // Get all registrations for an event
    @GetMapping("/count/event/{eventId}")
    public Long getReservationCount(@PathVariable Long eventId) {
        return registrationService.countByEventId(eventId);
    }
}
