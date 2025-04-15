package com.itevents.main.controllers;

import com.itevents.main.models.FavoriteModel;
import com.itevents.main.services.FavoriteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    public FavoriteModel create(@RequestBody FavoriteModel favorite) {
        return favoriteService.saveFavorite(favorite);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        favoriteService.deleteFavorite(id);
    }

    @GetMapping("/user/{userId}")
    public List<FavoriteModel> getByUser(@PathVariable Long userId) {
        return favoriteService.getFavoritesByUserId(userId);
    }
}
