package com.hexaware.careerjobportal.restcontrollers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hexaware.careerjobportal.dto.AuthRequestDTO;
import com.hexaware.careerjobportal.dto.AuthResponseDTO;
import com.hexaware.careerjobportal.dto.UserRegistrationDTO;
import com.hexaware.careerjobportal.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegistrationDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
