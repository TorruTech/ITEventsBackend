package com.itevents.main.repositories;

import com.itevents.main.models.FavoriteModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<FavoriteModel, Long> {
}
