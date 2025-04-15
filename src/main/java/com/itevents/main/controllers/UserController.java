package com.itevents.main.controllers;

import com.itevents.main.models.UserModel;
import com.itevents.main.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserModel> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<UserModel> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public UserModel createUser(@RequestBody UserModel user) {
        return userService.saveUser(user);
    }

    @PutMapping("/{id}")
    public Optional<UserModel> updateUser(@PathVariable Long id, @RequestBody UserModel updated) {
        return userService.getUserById(id).map(user -> {
            user.setUsername(updated.getUsername());
            user.setEmail(updated.getEmail());
            user.setPassword(updated.getPassword());
            user.setLoginProvider(updated.getLoginProvider());
            user.setRegistrationDate(updated.getRegistrationDate());
            user.setRole(updated.getRole());
            return userService.saveUser(user);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
