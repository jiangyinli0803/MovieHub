package org.example.backend.controller;
import org.example.backend.model.Movie;
import org.example.backend.repository.MovieRepository;
import org.example.backend.security.JwtAuthenticationFilter;
import org.example.backend.security.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;


@WebMvcTest(TmdbController.class)
@AutoConfigureMockMvc(addFilters = false)
class TmdbControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @TestConfiguration
    public static class TestConfig {
        @Bean
        public MovieRepository movieRepository() {
            return Mockito.mock(MovieRepository.class);
        }
    }

    @Test
    @WithMockUser
    void getMovies_shouldReturnMovieList_whenCalled() throws Exception {
        //given
        LocalDateTime fixedTime =LocalDateTime.parse("2025-05-28T12:00:00");
        List<Movie> movies = List.of(
                new Movie("123", 344, "title", "en", 7.45, 123, "12.04.2025", List.of("Anna", "Tom"), "https", "This is a super cool movie", List.of("Action"), fixedTime)
        );
        when(movieRepository.findAll()).thenReturn(movies);

        //when //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/movies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].actors[0]").value("Anna"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].category[0]").value("Action"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].created_at").exists());
    }

    @Test
    @WithMockUser
    void getMovieById_shouldReturnMovie_whenCalled() throws Exception {
        LocalDateTime fixedTime =LocalDateTime.parse("2025-05-28T12:00:00");
        Movie movie = new Movie("123", 344, "title", "en", 7.45, 123, "12.04.2025", List.of("Anna", "Tom"), "https", "This is a super cool movie", List.of("Action"), fixedTime);
        when(movieRepository.findById("123")).thenReturn(Optional.of(movie));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/123"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.actors[0]").value("Anna"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category[0]").value("Action"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.created_at").exists());
    }

    @Test
    @WithMockUser
    void getMoviesByCategory_shouldReturnMovieList_whenCalled() throws Exception {
        LocalDateTime fixedTime =LocalDateTime.parse("2025-05-28T12:00:00");
        Movie movie = new Movie("123", 344, "title", "en", 7.45, 123, "12.04.2025", List.of("Anna", "Tom"), "https", "This is a super cool movie", List.of("Action"), fixedTime);
        when(movieRepository.findByCategoryContainingIgnoreCase("action")).thenReturn(List.of(movie));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/category/action"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].actors[0]").value("Anna"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].category[0]").value("Action"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].created_at").exists());
    }
}