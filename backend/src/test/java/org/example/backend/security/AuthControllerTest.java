package org.example.backend.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getMe() throws Exception {
        // 构造 UserPrincipal
        UserPrincipal userPrincipal = new UserPrincipal(
                "1",                                           // id
                "testUser",                                    // username
                "test@example.com",                            // email
                "password",                                    // password (通常在测试中无效用)
                List.of(new SimpleGrantedAuthority("ROLE_USER")), // authorities
                Map.of("avatar_url", "http://example.com/avatar.png") // attributes
        );

        TestingAuthenticationToken auth = new TestingAuthenticationToken(userPrincipal, null, "ROLE_USER");
        auth.setAuthenticated(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/auth")
                        .with(SecurityMockMvcRequestPostProcessors.authentication(auth)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("testUser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.avatarUrl").value("http://example.com/avatar.png"));
    }
}