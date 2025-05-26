package org.example.backend.service;

import org.example.backend.model.MovieDto;
import org.example.backend.model.MovieListResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


import java.util.List;

@Service
public class TmdbService {
    private final String TMDB_BASE_URL = "https://api.themoviedb.org/3";

    //RestClient工具类,用于发出 HTTP 请求（访问外部 API，如 TMDB）
    private final RestClient restClient;
    //@Autowired
    //private ObjectMapper objectMapper;

    public TmdbService(@Value("${TMDB_API_KEY}") String tmdbApiKey) {
        this.restClient = RestClient.builder()
                .baseUrl(TMDB_BASE_URL)
                .defaultHeader("Authorization", "Bearer " + tmdbApiKey)
                .build();
    }

    public List<MovieDto> getMovies(int page) throws Exception {
        MovieListResponse response = restClient.get()
                                    .uri(uriBuilder -> uriBuilder.path("/discover/movie").queryParam("page", page).queryParam("sort_by", "popularity.dec").build())
                                    .accept(MediaType.APPLICATION_JSON)
                                    .retrieve()
                                    .body(MovieListResponse.class);
        return response.results();
    }

}
