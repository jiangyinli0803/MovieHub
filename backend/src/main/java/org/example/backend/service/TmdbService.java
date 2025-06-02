package org.example.backend.service;

import org.example.backend.helper.TmdbHelper;
import org.example.backend.mapper.MovieMapper;
import org.example.backend.model.*;
import org.example.backend.repository.MovieRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

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
    private static final Map<String, String> LANGUAGE_MAP = Map.ofEntries(
            Map.entry("en", "English"),
            Map.entry("fr", "French"),
            Map.entry("ja", "Japanese"),
            Map.entry("zh", "Chinese"),
            Map.entry("de", "German"),
            Map.entry("es", "Spanish"),
            Map.entry("ko", "Korean"),
            Map.entry("it", "Italian"),
            Map.entry("hi", "Hindi"),
            Map.entry("ru", "Russian")
    );

    private String getLanguageName(String code) {
        return LANGUAGE_MAP.getOrDefault(code, code); // fallback to original if not found
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
            for (Movie movie : movies) {
                String correctedLang = getLanguageName(movie.getLanguage());
                movie.setLanguage(correctedLang);
                if (movieRepository.existsByTmdbId(movie.getTmdbId())) {
                    movieRepository.save(movie); // 更新旧数据（改 language）
                } else {
                    movieRepository.save(movie); // 保存新数据
                }
            }
            return movies;

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch movies from TMDB", e);
        }
    }
}
