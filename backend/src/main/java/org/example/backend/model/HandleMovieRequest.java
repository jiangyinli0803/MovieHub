package org.example.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
public class HandleMovieRequest {
    Long tmdbId;
    String userId;
}
