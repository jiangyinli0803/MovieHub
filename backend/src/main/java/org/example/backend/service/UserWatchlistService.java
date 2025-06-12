package org.example.backend.service;

import org.example.backend.model.HandleMovieRequest;
import org.example.backend.model.Movie;
import org.example.backend.model.UserWatchlist;
import org.example.backend.repository.MovieRepository;
import org.example.backend.repository.UserWatchlistRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserWatchlistService {

    private final MovieRepository movieRepo;
    private final UserWatchlistRepository watchlistRepo;

    public UserWatchlistService(MovieRepository movieRepo, UserWatchlistRepository watchlistRepo) {
        this.movieRepo = movieRepo;
        this.watchlistRepo = watchlistRepo;
    }

    public ResponseEntity<String> addToWatchlist(HandleMovieRequest request) {
        Movie movie = movieRepo.findByTmdbId(request.getTmdbId());
        if (movie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found");
        }
        UserWatchlist item = watchlistRepo.findByUserId(request.getUserId());

        if (item == null) {
            item = UserWatchlist.builder()
                    .userId(request.getUserId())
                    .movies(List.of(movie))
                    .build();
        } else {
            List<Movie> currentMovies = item.getMovies();
            if (currentMovies.stream().anyMatch(m -> m.getTmdbId() == request.getTmdbId())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Movie already in watchlist");
            }
            currentMovies.add(movie);
            item.setMovies(currentMovies);
        }
        watchlistRepo.update(item);
        return ResponseEntity.ok("Movie added to watchlist");
    }

    public ResponseEntity<String> removeFromWatchlist(HandleMovieRequest request) {
        UserWatchlist item = watchlistRepo.findByUserId(request.getUserId());
        if (item == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User watchlist not found");
        }
        List<Movie> updatedMovies = item.getMovies().stream().filter(m ->m.getTmdbId() != request.getTmdbId()).toList();
        item.setMovies(updatedMovies);
        watchlistRepo.update(item);
        return ResponseEntity.ok("Movie removed from watchlist");
    }

    public List<Movie> getWatchlist(String userId) {
        UserWatchlist item = watchlistRepo.findByUserId(userId);
        if (item == null || item.getMovies() == null) {
            return Collections.emptyList();
        }
        return item.getMovies();
    }
}

