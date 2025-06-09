package org.example.backend.security;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.backend.model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class UserPrincipal implements UserDetails, OAuth2User {

    private final String id;
    private final String email;
    private final String password; // 注意：OAuth2 登录可能为 null
    private final Collection<? extends GrantedAuthority> authorities;
    private final Map<String, Object> attributes; // 只对 OAuth2 有用

    //for Form User
    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities,
         null
        );
    }

    // for OAuth User
    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return   new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities,
                attributes
        );
    }

    // OAuth2User
    @Override
    public String getName() {
        return String.valueOf(id);
    }

    // UserDetails
    @Override
    public String getUsername() {
        return email;
    }
}
