package org.example.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@Document("watchlist")
@Builder
public class UserWatchlist {
    @Id
    String id;
    String userId;
    List<Movie> movies;
}
