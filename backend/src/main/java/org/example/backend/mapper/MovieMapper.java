package org.example.backend.mapper;

import org.example.backend.helper.TmdbHelper;
import org.example.backend.model.Movie;
import org.example.backend.model.MovieDto;
import org.example.backend.service.IdService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MovieMapper {
    private final IdService idService;
    private final TmdbHelper tmdbHelper;

    public MovieMapper(IdService idService, TmdbHelper tmdbHelper) {
        this.idService = idService;
        this.tmdbHelper = tmdbHelper;
    }

    public Movie mapToMovie(MovieDto movieDto) throws Exception {
        Movie movie = new Movie();
        movie.setId(idService.randomId());
        movie.setTitle(movieDto.title());
        movie.setTmdbId(movieDto.id());
        movie.setLanguage(movieDto.original_language());
        movie.setRating(movieDto.vote_average());
        movie.setRatingCount(movieDto.vote_count());
        movie.setReleaseDate(movieDto.release_date());
        movie.setActors(tmdbHelper.getActorsByMovieId(movieDto.id()));
        movie.setPosterUrl(movieDto.poster_path());
        movie.setDescription(movieDto.overview());
        movie.setCategory(tmdbHelper.getCategoriesByIds(movieDto.genre_ids()));
        movie.setCreated_at(LocalDateTime.now());

        return movie;
    }
}
