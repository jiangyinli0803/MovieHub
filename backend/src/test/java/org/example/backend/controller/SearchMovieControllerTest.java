package org.example.backend.controller;
import org.example.backend.model.Movie;
import org.example.backend.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class SearchMovieControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void getFilteredMovies() throws Exception {
        LocalDateTime fixedTime =LocalDateTime.parse("2025-06-03T12:00:00");
        Movie movie =new Movie("123", 344, "test title", "en", 7.45, 123, "12.04.2025", List.of("Anna", "Tom"), "https", "This is a super cool movie", List.of("Action"), fixedTime);              ;

        movieRepository.save(movie);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/search").param("query", "test"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                [{"id": "123", 
                "tmdbId": 344, 
                "title": "test title", 
                "language":"en", 
                "rating": 7.45, "ratingCount": 123, 
                "releaseDate": "12.04.2025", 
                "actors": ["Anna", "Tom"], 
                "posterUrl": "https", "description": "This is a super cool movie", 
                "category": ["Action"],
                "created_at": "2025-06-03T12:00:00"
                }]
                """
                ));
    }
}