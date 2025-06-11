package org.example.backend.security;
import lombok.Data;
import org.example.backend.model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
public class UserPrincipal implements UserDetails, OAuth2User {

    private String id;
    private String username;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserPrincipal(String id, String username, String email, String password,
                         Collection<? extends GrantedAuthority> authorities,
                         Map<String, Object> attributes) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    //for Form User
    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return new UserPrincipal(
                user.getId(),
                user.getUsername(),
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
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities,
                attributes
        );
    }

    // Required for Spring Security
  @Override public boolean isAccountNonExpired() { return true; }
  @Override public boolean isAccountNonLocked() { return true; }
  @Override public boolean isCredentialsNonExpired() { return true; }
  @Override public boolean isEnabled() { return true; }

    public String getAvatarUrl() {
        if (attributes != null) {
            return Optional.ofNullable((String) attributes.get("avatar_url"))    // GitHub
                    .or(() -> Optional.ofNullable((String) attributes.get("picture"))) // Google
                    .orElse("/assets/movie-logo.PNG"); // 默认头像
        }
        return "/assets/movie-logo.PNG"; // 表单登录或其他情况
     }


    @Override
    public String getName() {
        if (attributes != null) {
            //System.out.println("OAuth2 Attributes: " + attributes);

            return Optional.ofNullable((String) attributes.get("name"))           // Google 有
                    .or(() -> Optional.ofNullable((String) attributes.get("login"))) // GitHub 有
                    .or(() -> Optional.ofNullable((String) attributes.get("email"))) // 补充
                    .orElse(username != null ? username : "Unknown user");
        }

        return username != null ? username : "Unknown user";

    }

}
