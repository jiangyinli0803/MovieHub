package org.example.backend.security;
import org.example.backend.model.user.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping
    public AuthUser getMe(@AuthenticationPrincipal Object principal) {
        System.out.println("Principal class: " + (principal == null ? "null" : principal.getClass().getName()));
        if (principal == null) return null;

        String id = "";
        String username ="Unknown user";
        String avatarUrl ="/assets/movie-logo.PNG";

        if (principal instanceof UserPrincipal userPrincipal) {
            id = userPrincipal.getId();
            username = userPrincipal.getName();
            avatarUrl = userPrincipal.getAvatarUrl();
        }

        return new AuthUser(id, username, avatarUrl);
    }
}
