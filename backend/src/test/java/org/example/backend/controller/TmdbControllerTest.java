package org.example.backend.controller;
import org.example.backend.model.Movie;
import org.example.backend.service.TmdbService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDateTime;
import java.util.List;
import static org.mockito.Mockito.when;


@Import(TmdbControllerTest.TestConfig.class)
@WebMvcTest(TmdbController.class)
class TmdbControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TmdbService tmdbService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public TmdbService tmdbService() {
            return Mockito.mock(TmdbService.class);
        }
    }

    @Test
    void getMovies_shouldReturnMovieList_whenCalledWithPage() throws Exception {
        //given
        LocalDateTime fixedTime =LocalDateTime.parse("2025-05-28T12:00:00");
        List<Movie> movies = List.of(
                new Movie("123", 344, "title", "en", 7.45, 123, "12.04.2025", List.of("Anna", "Tom"), "https", "This is a super cool movie", List.of("Action"), fixedTime)
        );
        when(tmdbService.getMovies(1)).thenReturn(movies);

        //when //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/movies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].actors[0]").value("Anna"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].category[0]").value("Action"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].created_at").exists());
    }
}