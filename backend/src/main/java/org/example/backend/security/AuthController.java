package org.example.backend.security;

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
    public String getMe(@AuthenticationPrincipal OAuth2User user) {
        if (user == null) return "Not authenticated";

        //GitHub OAuth 返回的用户信息里，有 "login" 这个字段（如用户名）
        //Google OAuth 返回的是 "name", "email" 等，没有 "login"

        return Optional.ofNullable(user.getAttribute("login"))   // GitHub
                .or(() -> Optional.ofNullable(user.getAttribute("name")))  // Google
                .map(Object::toString)
                .orElse("Unknown user");
    }
}
