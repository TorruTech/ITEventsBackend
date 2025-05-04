package com.itevents.main.repositories;

import com.itevents.main.models.RegistrationModel;
import com.itevents.main.models.UserModel;
import com.itevents.main.models.EventModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<RegistrationModel, Long> {

    boolean existsByUserAndEvent(UserModel user, EventModel event);

    boolean existsByUserIdAndEventId(Long userId, Long eventId);

    Optional<RegistrationModel> findByUserIdAndEventId(Long userId, Long eventId);

    Optional<RegistrationModel> findByUserAndEvent(UserModel user, EventModel event);

    List<RegistrationModel> findByUser_Id(Long userId);

    Long countByEvent_Id(Long eventId);

    Long countByUser_Id(Long userId);
}
