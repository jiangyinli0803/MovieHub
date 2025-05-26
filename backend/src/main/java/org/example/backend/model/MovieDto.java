package org.example.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MovieDto(
        int id,
        String title,
        @JsonProperty("original_language")String language,
        @JsonProperty("release_date") String releaseDate,
        @JsonProperty("overview") String description,
        @JsonProperty("vote_average") double rating,
        @JsonProperty("vote_count") int ratingCount,
        @JsonProperty("poster_path") String posterUrl

        //@JsonProperty重命名字段

) {
}
