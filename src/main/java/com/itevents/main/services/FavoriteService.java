package com.itevents.main.services;

import com.itevents.main.models.FavoriteModel;
import com.itevents.main.repositories.FavoriteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public List<FavoriteModel> getAllFavorites() {
        return favoriteRepository.findAll();
    }

    public Optional<FavoriteModel> getById(Long id) {
        return favoriteRepository.findById(id);
    }

    public FavoriteModel saveFavorite(FavoriteModel favorite) {
        return favoriteRepository.save(favorite);
    }

    public void deleteFavorite(Long id) {
        favoriteRepository.deleteById(id);
    }

    public List<FavoriteModel> getFavoritesByUserId(Long userId) {
        return favoriteRepository.findByUserId(userId);
    }
}
