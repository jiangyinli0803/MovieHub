package org.example.backend.service;

import org.example.backend.helper.TmdbHelper;
import org.example.backend.mapper.MovieMapper;
import org.example.backend.model.*;
import org.example.backend.repository.MovieRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TmdbService {

    private final TmdbHelper tmdbHelper;
    private final MovieMapper movieMapper;
    private final MovieRepository movieRepository;

    public TmdbService(TmdbHelper tmdbHelper, MovieMapper movieMapper, MovieRepository movieRepository) {
        this.tmdbHelper = tmdbHelper;
        this.movieMapper = movieMapper;
        this.movieRepository = movieRepository;
    }

    public List<Movie> getMovies(int page){
        try {
            List<Movie> movies = tmdbHelper.getMovieDtos(page).stream()
                    .map(dto -> {
                        try {
                            return movieMapper.mapToMovie(dto);
                        } catch (Exception e) {
                            throw new RuntimeException("Error mapping movie DTO", e);
                        }
                    }).toList();
            for (Movie movie : movies) {  // 保存到 MongoDB（去重）
                if (!movieRepository.existsByTmdbId(movie.getTmdbId())) {
                    movieRepository.save(movie);
                }
            }
            return movies;

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch movies from TMDB", e);
        }
    }
}
