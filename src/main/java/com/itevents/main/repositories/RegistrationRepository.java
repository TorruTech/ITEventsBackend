package com.itevents.main.repositories;

import com.itevents.main.models.RegistrationModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<RegistrationModel, Long> {
}
