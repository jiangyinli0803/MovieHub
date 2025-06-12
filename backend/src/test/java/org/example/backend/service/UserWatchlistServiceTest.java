package org.example.backend.service;
import org.example.backend.model.HandleMovieRequest;
import org.example.backend.model.Movie;
import org.example.backend.model.UserWatchlist;
import org.example.backend.repository.MovieRepository;
import org.example.backend.repository.UserWatchlistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserWatchlistServiceTest {

    @Mock
    private MovieRepository mockMovieRepo;
    @Mock
    private UserWatchlistRepository mockWatchlistRepo;
    @InjectMocks
    private UserWatchlistService service;

    private final HandleMovieRequest request = new HandleMovieRequest(344L, "user123");

     @Test
    void addToWatchlist_alreadyExists_whenCalledWithId() {
        //given
        LocalDateTime fixedTime =LocalDateTime.parse("2025-06-12T12:00:00");
        Movie addedMovie =new Movie("123", 344L, "test title", "en", 7.45, 123, "12.04.2025", List.of("Anna", "Tom"), "https", "This is a super cool movie", List.of("Action"), fixedTime);
        UserWatchlist watchlist = new UserWatchlist("1", "user123", List.of(addedMovie));
        when(mockMovieRepo.findByTmdbId(request.getTmdbId())).thenReturn(addedMovie);
        when(mockWatchlistRepo.findByUserId(request.getUserId())).thenReturn(watchlist);
        //when
        ResponseEntity<String> response = service.addToWatchlist(request);

        assertEquals("Movie already in watchlist", response.getBody());
    }

    @Test
    void addToWatchlist_newUser_whenCalledWithId() {
        //given
        LocalDateTime fixedTime =LocalDateTime.parse("2025-06-12T12:00:00");
        Movie addedMovie =new Movie("123", 344L, "test title", "en", 7.45, 123, "12.04.2025", List.of("Anna", "Tom"), "https", "This is a super cool movie", List.of("Action"), fixedTime);

        when(mockMovieRepo.findByTmdbId(request.getTmdbId())).thenReturn(addedMovie);
        when(mockWatchlistRepo.findByUserId(request.getUserId())).thenReturn(null);
        //when
        ResponseEntity<String> response = service.addToWatchlist(request);

        assertEquals("Movie added to watchlist", response.getBody());

    }

    @Test
    void removeFromWatchlist() {
        //given
        LocalDateTime fixedTime =LocalDateTime.parse("2025-06-12T12:00:00");
        Movie deleteMovie =new Movie("123", 344L, "test title", "en", 7.45, 123, "12.04.2025", List.of("Anna", "Tom"), "https", "This is a super cool movie", List.of("Action"), fixedTime);
         UserWatchlist item = new UserWatchlist("1", "user123", List.of(deleteMovie));
        when(mockWatchlistRepo.findByUserId(request.getUserId())).thenReturn(item);

        ResponseEntity<String> response = service.removeFromWatchlist(request);

        assertEquals("Movie removed from watchlist", response.getBody());

    }

    @Test
    void getWatchlist_shouldReturnWatchlist_whenCalledWithUserId() {
         LocalDateTime fixedTime =LocalDateTime.parse("2025-06-12T12:00:00");
        Movie movie =new Movie("123", 344L, "test title", "en", 7.45, 123, "12.04.2025", List.of("Anna", "Tom"), "https", "This is a super cool movie", List.of("Action"), fixedTime);
        UserWatchlist item = new UserWatchlist("1", "user123", List.of(movie));
        when(mockWatchlistRepo.findByUserId(request.getUserId())).thenReturn(item);

        List<Movie> actual = service.getWatchlist(request.getUserId());
        List<Movie> expected = List.of(movie);
        assertEquals(expected, actual);
    }
}