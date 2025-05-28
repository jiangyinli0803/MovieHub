package org.example.backend.mapper;
import org.example.backend.helper.TmdbHelper;
import org.example.backend.model.Movie;
import org.example.backend.model.MovieDto;
import org.example.backend.service.IdService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieMapperTest {

    @InjectMocks
    private MovieMapper movieMapper;

    @Mock
    private TmdbHelper tmdbHelper;

    @Mock
    private IdService idService;

    @Test
    void mapToMovie_shouldMapCorrectly() throws Exception {
        //given
        MovieDto movieDto = new MovieDto(123, "test title", "en", "12.02.2022", "good film", 8.45, 234, "https", List.of(12));
        when(tmdbHelper.getActorsByMovieId(123)).thenReturn(List.of("actor A"));
        when(tmdbHelper.getCategoriesByIds(List.of(12))).thenReturn(List.of("category A"));
        when(idService.randomId()).thenReturn("abc123");
        //when
        Movie movie = movieMapper.mapToMovie(movieDto);
        //then
        assertNotNull(movie.getCreated_at());
        assertEquals("test title", movie.getTitle());
        assertEquals("abc123", movie.getId());
        assertEquals(List.of("actor A"), movie.getActors());
        assertEquals(List.of("category A"), movie.getCategory());
    }
}