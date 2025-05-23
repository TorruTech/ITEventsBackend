package com.itevents.main.repositories;

import com.itevents.main.models.EventModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventModel, Long> {
    List<EventModel> findByLocation_Id(Long locationId);

    @Query("SELECT e.location.name AS location, COUNT(e) AS count FROM EventModel e GROUP BY e.location.name")
    List<LocationEventCount> countEventsByLocation();

    public interface LocationEventCount {
        String getLocation();
        Long getCount();
    }

}

