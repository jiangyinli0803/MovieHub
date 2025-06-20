package org.example.backend.security;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.user.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class CustomUserController {
    //为了解决循环依赖的问题，所以将以下两个service从之前的CustomUserDetailService中分离出来
    private final CustomUserCommandService customUserCommandService;
    private final CustomUserAuthService customUserAuthService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        System.out.println("Received SignUpRequest: " + signUpRequest);
        if (customUserCommandService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body("This email is already registered");
        }

        customUserCommandService.registerUser(signUpRequest);
        return ResponseEntity.ok("Successfully registered. Please log in.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> userLogin(@RequestBody LoginDto loginDto) {
        try {
            return ResponseEntity.ok(customUserAuthService.login(loginDto));
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }
    }
}


