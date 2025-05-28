package org.example.backend.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("movies")
public class Movie{
    @Id String id;
    long tmdbId;
    String title;
    String language;
    double rating;
    int ratingCount;
    String releaseDate;
    List<String> actors;
    String posterUrl;
    String description;
    List<String> category;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime created_at;
}
