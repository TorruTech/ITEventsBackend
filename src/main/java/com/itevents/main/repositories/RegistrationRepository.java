package com.itevents.main.repositories;

import com.itevents.main.models.RegistrationModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<RegistrationModel, Long> {
    List<RegistrationModel> findByUserId(Long userId);
}
