package com.itevents.main.controllers;

import com.itevents.main.models.FavoriteModel;
import com.itevents.main.models.UserModel;
import com.itevents.main.models.EventModel;
import com.itevents.main.repositories.FavoriteRepository;
import com.itevents.main.repositories.UserRepository;
import com.itevents.main.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    // Get all favorites by user
    @GetMapping("/user/{userId}")
    public List<FavoriteModel> getFavoritesByUser(@PathVariable Long userId) {
        return favoriteRepository.findByUserId(userId);
    }

    // Add a favorite event to a user
    @PostMapping
    public ResponseEntity<?> addFavorite(@RequestBody FavoriteModel fav) {
        Optional<UserModel> user = userRepository.findById(fav.getUser().getId());
        Optional<EventModel> event = eventRepository.findById(fav.getEvent().getId());

        if (user.isPresent() && event.isPresent()) {
            if (favoriteRepository.existsByUserIdAndEventId(user.get().getId(), event.get().getId())) {
                return ResponseEntity.ok("Ya existe en favoritos");
            }

            FavoriteModel newFav = new FavoriteModel();
            newFav.setUser(user.get());
            newFav.setEvent(event.get());
            return ResponseEntity.ok(favoriteRepository.save(newFav));
        }
        return ResponseEntity.badRequest().body("Usuario o evento no encontrado");
    }

    // Delete a favorite event from a user
    @DeleteMapping("/user/{userId}/event/{eventId}")
    public ResponseEntity<?> deleteFavorite(@PathVariable Long userId, @PathVariable Long eventId) {
        Optional<FavoriteModel> fav = favoriteRepository.findByUserIdAndEventId(userId, eventId);
        if (fav.isPresent()) {
            favoriteRepository.delete(fav.get());
            return ResponseEntity.ok("Favorito eliminado");
        }
        return ResponseEntity.notFound().build();
    }

    // Check if an event is a favorite
    @GetMapping("/check")
    public boolean isFavorite(@RequestParam Long userId, @RequestParam Long eventId) {
        return favoriteRepository.existsByUserIdAndEventId(userId, eventId);
    }
}
