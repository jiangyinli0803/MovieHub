package org.example.backend.controller;

import org.example.backend.model.HandleMovieRequest;
import org.example.backend.model.Movie;
import org.example.backend.service.UserWatchlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
public class UserWatchlistController {
    private final UserWatchlistService userWatchlistService;
    public UserWatchlistController(UserWatchlistService userWatchlistService) {
        this.userWatchlistService = userWatchlistService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToWatchlist(@RequestBody HandleMovieRequest request) {
        return userWatchlistService.addToWatchlist(request);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFromWatchlist(@RequestBody HandleMovieRequest request) {
        return userWatchlistService.removeFromWatchlist(request);
    }

    @GetMapping("/{userId}")
    public List<Movie> getWatchlist(@PathVariable String userId) {
        return userWatchlistService.getWatchlist(userId);
    }
}
