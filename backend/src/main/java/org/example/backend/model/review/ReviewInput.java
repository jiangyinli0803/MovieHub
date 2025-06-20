package org.example.backend.model.review;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewInput {
    private int rating;
    private String title;
    private String comment;

}
