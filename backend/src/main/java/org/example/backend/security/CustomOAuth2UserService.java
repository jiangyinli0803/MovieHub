package org.example.backend.security;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.user.AuthProvider;
import org.example.backend.model.user.User;
import org.example.backend.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User appUser = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        AuthProvider provider = AuthProvider.valueOf(registrationId.toUpperCase());

        Map<String, Object> attributes = appUser.getAttributes();
        String providerId = getProviderId(attributes, provider);
        String email = getEmail(attributes, provider);
        String name = getName(attributes, provider);
        String avatarUrl = getAvatarUrl(attributes, provider);

        Optional<User> userOpt = userRepository.findByAuthProviderAndProviderId(provider, providerId);
        User user;
        if (userOpt.isPresent()) {
            user = userOpt.get();
            user.setName(name);
            user.setAvatarUrl(avatarUrl);
            user.setEmail(email);
        } else {
            //数据库中不存在就保存
            user = User.builder()
                    .authProvider(provider)
                    .providerId(providerId)
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
            case GITHUB -> (String) ((Map<?, ?>) attributes.get("email")).toString(); // GitHub 默认不返回 email，需另外请求
            default -> null;
        };
    }

    private String getName(Map<String, Object> attributes, AuthProvider provider) {
        return switch (provider) {
            case GOOGLE -> (String) attributes.get("name");
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
