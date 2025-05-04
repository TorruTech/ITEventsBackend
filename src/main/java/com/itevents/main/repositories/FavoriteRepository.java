package com.itevents.main.repositories;

import com.itevents.main.models.EventModel;
import com.itevents.main.models.FavoriteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<FavoriteModel, Long> {
    List<FavoriteModel> findByUserId(Long userId);
    @Query("SELECT f.event FROM FavoriteModel f WHERE f.user.id = :userId")
    List<EventModel> findFavoriteEventsByUserId(@Param("userId") Long userId);
    Optional<FavoriteModel> findByUserIdAndEventId(Long userId, Long eventId);
    boolean existsByUserIdAndEventId(Long userId, Long eventId);
    Long countByUser_Id(Long userId);

}
