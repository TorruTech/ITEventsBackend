package com.itevents.main.controllers;

import com.itevents.main.models.UserModel;
import com.itevents.main.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "User", description = "Endpoints para gestión de usuarios")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Devuelve todos los usuarios")
    @GetMapping
    public List<UserModel> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Devuelve un usuario por id")
    @GetMapping("/{id}")
    public Optional<UserModel> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Crea un nuevo usuario")
    @PostMapping
    public UserModel createUser(@RequestBody UserModel user) {
        return userService.saveUser(user);
    }

    @Operation(summary = "Actualiza un usuario por id")
    @PutMapping("/{id}")
    public Optional<UserModel> updateUser(@PathVariable Long id, @RequestBody UserModel updated) {
        return userService.getUserById(id).map(user -> {
            user.setUsername(updated.getUsername());
            user.setEmail(updated.getEmail());
            user.setFirebaseUID(updated.getFirebaseUID());
            user.setLoginProvider(updated.getLoginProvider());
            user.setRegistrationDate(updated.getRegistrationDate());
            user.setRole(updated.getRole());
            return userService.saveUser(user);
        });
    }

    @Operation(summary = "Elimina un usuario por id")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @Operation(summary = "Actualiza el avatar de un usuario por id")
    @PatchMapping("/{id}/avatar")
    public ResponseEntity<?> updateAvatar(@PathVariable Long id, @RequestBody UserModel userUpdate) {
        Optional<UserModel> optionalUser = userService.getUserById(id);
        if (optionalUser.isPresent()) {
            UserModel user = optionalUser.get();
            user.setAvatarUrl(userUpdate.getAvatarUrl());
            userService.saveUser(user);
            return ResponseEntity.ok("Avatar actualizado correctamente");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
