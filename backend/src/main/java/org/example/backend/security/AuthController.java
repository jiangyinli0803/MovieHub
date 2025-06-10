package org.example.backend.security;

import org.example.backend.model.user.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping
    public AuthUser getMe(@AuthenticationPrincipal OAuth2User user) {
        if (user == null) return null;

        String name = Optional.ofNullable(user.getAttribute("login"))   // GitHub
                .or(() -> Optional.ofNullable(user.getAttribute("name")))  // Google
                .map(Object::toString)
                .orElse("Unknown user");

        String avatarUrl = Optional.ofNullable(user.getAttribute("avatar_url"))// GitHub
                .or(() -> Optional.ofNullable(user.getAttribute("picture")))// Google
                .map(Object::toString)
                .orElse(null);
        return new AuthUser(name, avatarUrl);
    }
}
