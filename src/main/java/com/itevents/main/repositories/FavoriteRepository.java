package com.itevents.main.repositories;

import com.itevents.main.models.FavoriteModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<FavoriteModel, Long> {
    List<FavoriteModel> findByUserId(Long userId);
}
