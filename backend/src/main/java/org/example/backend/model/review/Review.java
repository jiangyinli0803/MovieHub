package org.example.backend.model.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("reviews")
public class Review {
    private String id;
    private String userId;
    private long tmdbId;
    private int rating;
    private String title;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
