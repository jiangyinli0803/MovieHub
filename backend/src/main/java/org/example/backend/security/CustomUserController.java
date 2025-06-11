package org.example.backend.security;

import org.example.backend.model.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/form")
public class CustomUserController {
    @Autowired
    private final CustomUserDetailService customUserDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public CustomUserController(CustomUserDetailService customUserDetailService, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.customUserDetailService = customUserDetailService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        System.out.println("Received SignUpRequest: " + signUpRequest);
        if (customUserDetailService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body("This email is already registered");
        }

        customUserDetailService.registerUser(signUpRequest);
        return ResponseEntity.ok("Successfully registered. Please log in.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> userLogin(@RequestBody LoginDto loginDto) {
        try {
            if (loginDto.getEmail() == null || loginDto.getPassword() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email and password must be provided.");
            }
            // 1. Authenticate the user credentials using Spring Security's AuthenticationManager
            // This manager will internally use your CustomUserDetailsService and PasswordEncoder
            // to verify the username and securely compare the password.
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );

            // If authentication is successful, get the UserDetails from the Authentication object
            // The UserDetails object returned by customUserDetailsService.loadUserByUsername()
            // should represent your authenticated user.
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // 2. Load the actual User object (if needed for additional properties like ID, etc.)
            // You might need to cast userDetails or load the user again if your UserDetails
            // doesn't directly expose all required User entity properties.
            // A better practice is often to have a custom UserDetails implementation that wraps your User entity.
            User user = customUserDetailService.findbyEmail(loginDto.getEmail()); // Assuming this returns your 'User' entity

            // 3. Generate JWT
            final String jwtToken = jwtTokenUtil.generateToken(String.valueOf(userDetails));

            // 4. Construct the response DTO
            // Return the JWT and relevant user information (e.g., ID, username, email)
            UserResponseDto userResponseDto = new UserResponseDto(user.getId(), user.getUsername(), user.getEmail(),
                    user.getAvatarUrl() != null ? user.getAvatarUrl() : "/assets/movie-logo.PNG");
            LoginResponseDto loginResponse = new LoginResponseDto(jwtToken, userResponseDto);

            return ResponseEntity.ok(loginResponse);

        } catch (BadCredentialsException e) {
            // This exception is thrown by AuthenticationManager if credentials are bad.
            // You can also add more specific logging here.
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            System.err.println("An unexpected error occurred during login: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred during login. Please try again later.");
        }
    }
}


