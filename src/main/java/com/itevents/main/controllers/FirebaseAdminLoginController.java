package com.itevents.main.controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.itevents.main.models.UserModel;
import com.itevents.main.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Tag(name = "Firebase Admin", description = "Endpoint para autenticación de administradores")
@RestController
@RequestMapping("/api/admin")
public class FirebaseAdminLoginController {

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Autenticación de administradores")
    @PostMapping("/login")
    public ResponseEntity<?> loginWithFirebase(@RequestHeader("Authorization") String authorization) {
        try {
            String idToken = authorization.replace("Bearer ", "");
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String email = decodedToken.getEmail();
            String uid = decodedToken.getUid();

            Optional<UserModel> userOpt = userRepository.findUserByFirebaseUID(uid);

            if (userOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado en el sistema.");
            }

            UserModel user = userOpt.get();

            if (!"admin".equals(user.getRole())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acceso restringido a administradores.");
            }

            return ResponseEntity.ok(user);

        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o caducado.");
        }
    }
}
