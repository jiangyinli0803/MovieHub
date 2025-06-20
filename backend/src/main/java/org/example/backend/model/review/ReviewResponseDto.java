package org.example.backend.model.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReviewResponseDto {
    private int rating;
    private String title;
    private String comment;
    private String username;
    private String movieTitle;
    private String posterUrl;
    private double tmdbRating;
    @JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss")
    private LocalDateTime createdAt;
}
