package org.example.backend.service;

import org.example.backend.exeption.MovieNotFoundException;
import org.example.backend.model.Movie;
import org.example.backend.repository.MovieRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SearchMovieService {
    private final MovieRepository movieRepository;


    public SearchMovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getFilteredMovies(String searchText) throws MovieNotFoundException {
        String keyword = searchText.trim().toLowerCase();
        List<Movie> movies = movieRepository.findAll();
        return movies.stream().filter(
                movie -> movie.getTitle().toLowerCase().contains(keyword)
                || movie.getActors().stream().anyMatch(a -> a.toLowerCase().contains(keyword))
                ).toList();
    }
}
