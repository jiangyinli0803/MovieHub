package org.example.backend.controller;
import org.example.backend.model.Movie;
import org.example.backend.repository.MovieRepository;
import org.example.backend.service.TmdbService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class TmdbController {
    //fetch data from MongoDB
    private final MovieRepository movieRepository;

    public TmdbController( MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public List<Movie> getMovies(){
        return movieRepository.findAll();
    }
}
