package org.example.backend.security;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.user.AuthProvider;
import org.example.backend.model.user.User;
import org.example.backend.repository.UserRepository;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
       String registrationId = userRequest.getClientRegistration().getRegistrationId();
        AuthProvider provider = AuthProvider.valueOf(registrationId.toUpperCase());

        Map<String, Object> attributes;
        OAuth2User delegateUser;

        // 根据 provider 判断是普通 OAuth2 还是 OIDC（Google）
        if ("google".equalsIgnoreCase(registrationId)) {
            // Google 使用的是 OIDC 流程
            OidcUserService oidcUserService = new OidcUserService();
            OidcUser oidcUser = oidcUserService.loadUser((OidcUserRequest) userRequest);
            attributes = oidcUser.getAttributes();
        } else {
            OAuth2User oauth2User = super.loadUser(userRequest);
            attributes = oauth2User.getAttributes();
        }

       String providerId = getProviderId(attributes, provider);
       String email = getEmail(attributes, provider);

        String username = getUsername(attributes, provider);
        String avatarUrl = getAvatarUrl(attributes, provider);

        Optional<User> userOpt = userRepository.findByAuthProviderAndProviderId(provider, providerId);
        User user;
        if (userOpt.isPresent()) {
            user = userOpt.get();
            user.setUsername(username);
            user.setAvatarUrl(avatarUrl);
            user.setEmail(email);
        } else {
            //数据库中不存在就保存
            user = User.builder()
                    .authProvider(provider)
                    .providerId(providerId)
                    .username(username)
                    .email(email)
                    .avatarUrl(avatarUrl)
                    .build();
        }
        userRepository.save(user);
        return UserPrincipal.create(user, attributes);
    }

    private String getProviderId(Map<String, Object> attributes, AuthProvider provider) {
        return switch (provider) {
            case GOOGLE -> (String) attributes.get("sub");
            case GITHUB -> String.valueOf(attributes.get("id"));
            default -> throw new RuntimeException("Unsupported provider: " + provider);
        };
    }
    private String getEmail(Map<String, Object> attributes, AuthProvider provider) {
        return switch (provider) {
            case GOOGLE -> (String) attributes.get("email");
            case GITHUB -> {
                // 临时返回一个默认邮箱用于测试
                yield "github_user_" + attributes.get("id") + "@example.com";
            }
            default -> null;
        };
    }

    private String getUsername(Map<String, Object> attributes, AuthProvider provider) {
        return switch (provider) {
            case GOOGLE -> Optional.ofNullable((String) attributes.get("name"))
                    .or(() -> Optional.ofNullable((String) attributes.get("email")))
                    .orElse("google_user");
            case GITHUB -> (String) attributes.get("login");
            default -> null;
        };
    }

    private String getAvatarUrl(Map<String, Object> attributes, AuthProvider provider) {
        return switch (provider) {
            case GOOGLE -> (String) attributes.get("picture");
            case GITHUB -> (String) attributes.get("avatar_url");
            default -> null;
        };
    }



}
