package com.itevents.main.services;

import com.itevents.main.models.RegistrationModel;
import com.itevents.main.repositories.RegistrationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;

    public RegistrationService(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    public List<RegistrationModel> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    public Optional<RegistrationModel> getById(Long id) {
        return registrationRepository.findById(id);
    }

    public RegistrationModel saveRegistration(RegistrationModel registration) {
        return registrationRepository.save(registration);
    }

    public void deleteRegistration(Long id) {
        registrationRepository.deleteById(id);
    }

    public List<RegistrationModel> getRegistrationsByUserId(Long userId) {
        return registrationRepository.findByUserId(userId);
    }
}
