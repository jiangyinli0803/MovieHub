package org.example.backend.security;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.user.LoginDto;
import org.example.backend.model.user.LoginResponseDto;
import org.example.backend.model.user.User;
import org.example.backend.model.user.UserResponseDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserAuthService {
    private final AuthenticationManager authenticationManager;
    private final CustomUserQueryService customUserQueryService;  // 用于拿完整User实体
    private final JwtTokenUtil jwtTokenUtil;

    public LoginResponseDto login(LoginDto loginDto) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );

            User user = customUserQueryService.findByEmail(loginDto.getEmail());

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            // 使用 UserPrincipal 的 id 生成 token
            String token = jwtTokenUtil.generateToken(userPrincipal);

            UserResponseDto userResponse = new UserResponseDto(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getAvatarUrl() != null ? user.getAvatarUrl() : "/assets/movie-logo.PNG"
            );

            return new LoginResponseDto(token, userResponse);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Login failed: " + e.getMessage());
        }

    }

}
