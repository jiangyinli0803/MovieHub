package org.example.backend.controller;
import org.example.backend.model.Movie;
import org.example.backend.service.TmdbService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class TmdbController {

    private final TmdbService tmdbService;

    public TmdbController(TmdbService tmdbService) {
        this.tmdbService = tmdbService;
    }

    @GetMapping
    public List<Movie> getMovies(@RequestParam(defaultValue = "1") int page){
        return tmdbService.getMovies(page);
    }
}
