package com.itevents.main.controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.itevents.main.dtos.FirebaseUserRequest;
import com.itevents.main.models.UserModel;
import com.itevents.main.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class FirebaseAuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/firebase")
    public ResponseEntity<?> registerOrLoginUser(
            @RequestHeader("Authorization") String authorization,
            @RequestBody FirebaseUserRequest request
    ) {
        try {
            String idToken = authorization.replace("Bearer ", "");
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);

            // Check if email matches the one in the request
            if (!decodedToken.getEmail().equals(request.getEmail())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email mismatch.");
            }

            Optional<UserModel> existingUser = userRepository.findUserByFirebaseUID(request.getUid());

            if (existingUser.isPresent()) {
                return ResponseEntity.ok(existingUser.get());
            }

            // Create a new user
            UserModel newUser = new UserModel();
            newUser.setEmail(request.getEmail());
            newUser.setUsername(request.getUsername());
            newUser.setFirebaseUID(request.getUid());
            newUser.setLoginProvider(request.getLoginProvider());
            newUser.setRegistrationDate(new Date(System.currentTimeMillis()));
            newUser.setRole("user");

            UserModel savedUser = userRepository.save(newUser);
            return ResponseEntity.ok(savedUser);

        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token: " + e.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.getMessage());
        }
    }
}
