package com.itevents.main.services;

import com.itevents.main.models.EventModel;
import com.itevents.main.models.RegistrationModel;
import com.itevents.main.models.UserModel;
import com.itevents.main.repositories.RegistrationRepository;
import com.itevents.main.repositories.EventRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final EventRepository eventRepository;

    public RegistrationService(RegistrationRepository registrationRepository, EventRepository eventRepository) {
        this.registrationRepository = registrationRepository;
        this.eventRepository = eventRepository;
    }

    public boolean existsByUserAndEvent(UserModel user, EventModel event) {
        return registrationRepository.existsByUserAndEvent(user, event);
    }

    public RegistrationModel saveRegistration(RegistrationModel registration) {
        return registrationRepository.save(registration);
    }

    public RegistrationModel createReservation(UserModel user, EventModel event, int ticketsReserved) {
        if (registrationRepository.existsByUserAndEvent(user, event)) {
            throw new IllegalStateException("Ya estás registrado en este evento.");
        }

        if (ticketsReserved < 1 || ticketsReserved > 3) {
            throw new IllegalArgumentException("Puedes reservar entre 1 y 3 entradas.");
        }

        if (event.isRequiresTicket()) {
            Integer available = event.getAvailableTickets();
            if (available == null || available < ticketsReserved) {
                throw new IllegalStateException("No hay suficientes entradas disponibles.");
            }

            event.setAvailableTickets(available - ticketsReserved);
            eventRepository.save(event);
        }

        RegistrationModel registration = new RegistrationModel();
        registration.setUser(user);
        registration.setEvent(event);
        registration.setTicketsReserved(ticketsReserved);

        return registrationRepository.save(registration);
    }

    public boolean deleteRegistration(Long id) {
        Optional<RegistrationModel> regOpt = registrationRepository.findById(id);
        if (regOpt.isPresent()) {
            RegistrationModel reg = regOpt.get();
            EventModel event = reg.getEvent();

            if (event.isRequiresTicket()) {
                int toReturn = reg.getTicketsReserved();
                event.setAvailableTickets(event.getAvailableTickets() + toReturn);
                eventRepository.save(event);
            }

            registrationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean deleteByUserAndEvent(Long userId, Long eventId) {
        Optional<RegistrationModel> regOpt = registrationRepository.findByUserIdAndEventId(userId, eventId);
        if (regOpt.isPresent()) {
            RegistrationModel reg = regOpt.get();
            EventModel event = reg.getEvent();

            if (event.isRequiresTicket()) {
                event.setAvailableTickets(event.getAvailableTickets() + reg.getTicketsReserved());
                eventRepository.save(event);
            }

            registrationRepository.delete(reg);
            return true;
        }
        return false;
    }

    public List<EventModel> getEventsByUserId(Long userId) {
        return registrationRepository.findByUser_Id(userId)
                .stream()
                .map(RegistrationModel::getEvent)
                .collect(Collectors.toList());
    }

    public Long countByEventId(Long eventId) {
        return registrationRepository.countByEvent_Id(eventId);
    }

    public boolean isUserRegisteredForEvent(Long userId, Long eventId) {
        return registrationRepository.existsByUserIdAndEventId(userId, eventId);
    }

}
