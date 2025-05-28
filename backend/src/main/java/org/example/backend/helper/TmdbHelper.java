package org.example.backend.helper;
import org.example.backend.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.List;

@Component
public class TmdbHelper {

    //RestClient工具类,用于发出 HTTP 请求（访问外部 API，如 TMDB）
    private final RestClient restClient;

    public TmdbHelper(@Value("${tmdb.api.key}") String tmdbApiKey, RestClient.Builder restClientBuilder) {
            this.restClient =restClientBuilder
                .baseUrl("https://api.themoviedb.org/3")
                .defaultHeader("Authorization", "Bearer " + tmdbApiKey)
                .build();
    }

    public List<MovieDto> getMovieDtos(int page){
        MovieListResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/discover/movie")
                        .queryParam("page", page)
                        .queryParam("sort_by", "popularity.desc")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(MovieListResponse.class);
        if(response == null){
           return null;
        }
        return response.results();
    }

    public List<String> getCategoriesByIds(List<Integer> genreIds){
        Categories categoriesResponse =restClient.get()
                .uri("/genre/movie/list?language=en")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(Categories.class);

        assert categoriesResponse != null;
        return categoriesResponse.genres().stream()
                .filter(cat -> genreIds.contains(cat.id()))
                .map(Category::name)
                .toList();
    }

    public List<String> getActorsByMovieId(long movieId){
        CreditsDto credits = restClient.get()
                .uri("/movie/{id}/credits", movieId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(CreditsDto.class);

        assert credits != null;
        return credits.cast().stream()
                .limit(5)
                .map(CastMember::name)
                .toList();
    }
}
