package org.example.backend.model.review;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewRequest {
    private long tmdbId;
    private ReviewInput reviewInput;

}
