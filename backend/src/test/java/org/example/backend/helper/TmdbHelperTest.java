package org.example.backend.helper;
import org.example.backend.model.MovieDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;


@AutoConfigureMockRestServiceServer
@SpringBootTest(properties="tmdb.api.key=123")
class TmdbHelperTest {
    @Autowired
    private TmdbHelper tmdbHelper;

    @Autowired
    private MockRestServiceServer server;

    @Test
    void getCategoriesByIds_shouldReturnListOfCategories(){
        String responseJson = """
        {
          "genres": [
            {
              "id": 28,
              "name": "Action"
            },
            {
              "id": 12,
              "name": "Adventure"
            }
          ]
        }
        """;

        server.expect(requestTo("https://api.themoviedb.org/3/genre/movie/list?language=en"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));

        List<String> categories = tmdbHelper.getCategoriesByIds(List.of(28, 12));

        assertThat(categories).containsExactly("Action", "Adventure");
        server.verify();
    }

    @Test
    void getMovieDtos_shouldReturnList() {
        String responseJson = """
        {
          "results": [
            {
              "id": 123,
              "title": "Test Movie",
              "original_language": "en",
              "release_date": "2025-04-12",
              "overview": "A great movie",
              "vote_average": 8.7,
              "vote_count": 300,
              "poster_path": "/abc.jpg",
              "genre_ids": [28]
            }
          ]
        }
        """;
        server.expect(requestTo("https://api.themoviedb.org/3/discover/movie?page=1&sort_by=popularity.desc"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));

        // when
        List<MovieDto> result = tmdbHelper.getMovieDtos(1);
        server.verify();

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().title()).isEqualTo("Test Movie");
    }

@Test
void getActorsByMovieId(){
    String responseJson = """
        {
          "cast": [
            { "name": "Anna" },
            { "name": "Tom" }
          ]
        }
        """;

    server.expect(requestTo("https://api.themoviedb.org/3/movie/123/credits"))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));

    List<String> actors = tmdbHelper.getActorsByMovieId(123);

    assertThat(actors).containsExactly("Anna", "Tom");
    server.verify();
}
}