package org.example.backend.controller;

import org.example.backend.security.CustomOAuth2UserService;
import org.example.backend.security.CustomUserDetailService;
import org.example.backend.security.SecurityConfig;
import org.example.backend.service.TmdbService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.mock.mockito.MockBean;



@WebMvcTest(controllers = AdminMovieController.class)
@Import(SecurityConfig.class)
class AdminMovieControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TmdbService tmdbService;

    @MockBean
    private CustomUserDetailService customUserDetailService;

    @MockBean
    private CustomOAuth2UserService customOAuth2UserService;

    @TestConfiguration
    public static class TestConfig {
        @Bean
        public TmdbService tmdbService() {
            return Mockito.mock(TmdbService.class);
        }
    }

    @Test
    @WithMockUser
    void refreshMovies_shouldCallTmdbServiceFor20Pages_andReturnOk() throws Exception {
        mockMvc.perform(post("/admin/refresh-movies").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("Movies refreshed from TMDB"));

        // verify that tmdbService.getMovies(page) is called 20 times
        verify(tmdbService, times(20)).getMovies(Mockito.anyInt());
    }
}