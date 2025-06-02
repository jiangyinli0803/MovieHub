package org.example.backend.service;
import org.example.backend.helper.TmdbHelper;
import org.example.backend.mapper.MovieMapper;
import org.example.backend.model.Movie;
import org.example.backend.model.MovieDto;
import org.example.backend.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TmdbServiceTest {

    private TmdbService tmdbService;

    @Mock
    private TmdbHelper tmdbHelper;
    @Mock
    private MovieMapper movieMapper;
    @Mock
    private MovieRepository movieRepository;

    @BeforeEach
    void setUp() {
        tmdbService = new TmdbService(tmdbHelper, movieMapper, movieRepository);
    }

    @Test
    void getMovies_shouldReturnMovies() throws Exception {
        //given
        MovieDto movieDto = new MovieDto(344, "title", "en", "12.04.2025", "This is a super cool movie", 7.45, 123,  "https",  List.of(12));

        LocalDateTime fixedTime =LocalDateTime.parse("2025-05-28T12:00:00");
        Movie mappedMovie = new Movie("123", 344, "title", "en", 7.45, 123, "12.04.2025", List.of("Anna", "Tom"), "https", "This is a super cool movie", List.of("Action"), fixedTime);
        Mockito.when(tmdbHelper.getMovieDtos(1)).thenReturn(List.of(movieDto));
        Mockito.when(movieMapper.mapToMovie(movieDto)).thenReturn(mappedMovie);

        //when
        List<Movie> result = tmdbService.getMovies(1);
        //then
        assertEquals(1, result.size());
        assertEquals("123", result.getFirst().getId());
        Mockito.verify(movieRepository).save(mappedMovie);
    }

}