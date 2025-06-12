package org.example.backend.controller;

import org.example.backend.model.HandleMovieRequest;
import org.example.backend.model.Movie;
import org.example.backend.service.UserWatchlistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDateTime;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(UserWatchlistController.class)
@AutoConfigureMockMvc
class UserWatchlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserWatchlistService service;

    private final HandleMovieRequest request = new HandleMovieRequest(344L, "user123");

    @Test
    @WithMockUser(username = "tester")
    void addToWatchlist() throws Exception {

        when(service.addToWatchlist(request)).thenReturn(ResponseEntity.ok("Movie added to watchlist"));

        //!!!!Spring Security 默认对 POST、PUT、DELETE 等“修改状态”的请求启用了 CSRF 保护
        mockMvc.perform(MockMvcRequestBuilders.post("/api/watchlist/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"tmdbId\": 344, \"userId\": \"user123\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Movie added to watchlist"));
    }

    @Test
    @WithMockUser(username = "tester")
    void getWatchlist() throws Exception {
        LocalDateTime fixedTime =LocalDateTime.parse("2025-06-12T12:00:00");
        Movie addedMovie =new Movie("123", 344L, "test title", "en", 7.45, 123, "12.04.2025", List.of("Anna", "Tom"), "https", "This is a super cool movie", List.of("Action"), fixedTime);
        List<Movie> mockMovies = List.of(addedMovie);
        when(service.getWatchlist("user123")).thenReturn(mockMovies);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/watchlist/user123"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("""
[{"id":"123","tmdbId":344,"title":"test title","language":"en","rating":7.45,"ratingCount":123,"releaseDate":"12.04.2025","actors":["Anna","Tom"],"posterUrl":"https","description":"This is a super cool movie","category":["Action"],"created_at":"2025-06-12T12:00:00"}
]"""
                ));
    }

    @Test
    @WithMockUser(username = "tester")
    void deleteFromWatchlist() throws Exception {

        when(service.removeFromWatchlist(request)).thenReturn(ResponseEntity.ok("Movie removed from Watchlist"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/watchlist/delete").with(csrf()).contentType(MediaType.APPLICATION_JSON)
                .content("{\"tmdbId\": 344, \"userId\": \"user123\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Movie removed from Watchlist"));
    }


}