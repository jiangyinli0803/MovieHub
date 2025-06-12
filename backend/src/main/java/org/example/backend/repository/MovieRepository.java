package org.example.backend.repository;
import org.example.backend.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
    List<Movie> findByCategoryContainingIgnoreCase(String category);

    Movie findByTmdbId(Long tmdbId);
}
