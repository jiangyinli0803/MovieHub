package org.example.backend.repository;
import org.example.backend.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
    boolean existsByTmdbId(Long tmdbId);
}
