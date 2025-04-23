package com.itevents.main.controllers;

import com.itevents.main.models.RegistrationModel;
import com.itevents.main.services.RegistrationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public RegistrationModel create(@RequestBody RegistrationModel registration) {
        return registrationService.saveRegistration(registration);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        registrationService.deleteRegistration(id);
    }

    @GetMapping("/user/{userId}")
    public List<RegistrationModel> getByUser(@PathVariable Long userId) {
        return registrationService.getRegistrationsByUserId(userId);
    }
}
