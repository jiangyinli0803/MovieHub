package org.example.backend.security;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.user.SignUpRequest;
import org.example.backend.model.user.User;
import org.example.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserCommandService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;//使用 PasswordEncoder 是为了注册用户（编码密码）

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void registerUser(SignUpRequest request) {
        if(request.getPassword() != null) {
            User user = User.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build();

            userRepository.save(user);
        }
    }
}