package org.example.backend.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            String token = jwtTokenUtil.generateToken(userPrincipal);

            String redirectUri = UriComponentsBuilder
                    .fromUriString("http://localhost:5173/oauth-success")
                    .queryParam("token", token)
                    .build().toUriString();

            response.sendRedirect(redirectUri);

    }
}
