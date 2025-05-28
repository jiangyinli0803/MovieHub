package org.example.backend.controller;

import org.example.backend.service.TmdbService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminMovieController {
    private final TmdbService tmdbService;

    public AdminMovieController(TmdbService tmdbService) {
        this.tmdbService = tmdbService;
    }

    @PostMapping("/refresh-movies")
    public ResponseEntity<String> refreshMovies(@RequestParam(defaultValue = "1") int page) {
        int maxPages = 20;
        for (int i = 1; i<= maxPages; i++) {
            tmdbService.getMovies(i);
        }
        return ResponseEntity.ok("Movies refreshed from TMDB");
    }
}
