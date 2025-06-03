package org.example.backend.controller;

import org.example.backend.exeption.MovieNotFoundException;
import org.example.backend.model.Movie;
import org.example.backend.service.SearchMovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchMovieController {
    private final SearchMovieService searchMovieService;
    public SearchMovieController(SearchMovieService searchMovieService) {
        this.searchMovieService = searchMovieService;
    }

    @GetMapping
    public List<Movie> getFilteredMovies(@RequestParam String query) throws MovieNotFoundException {
        return searchMovieService.getFilteredMovies(query);
    }
}

