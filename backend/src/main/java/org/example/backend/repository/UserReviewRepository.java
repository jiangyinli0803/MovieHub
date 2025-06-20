package org.example.backend.repository;

import org.example.backend.model.review.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserReviewRepository extends MongoRepository<Review, String> {
    List<Review> findAllByTmdbId(long tmdbId);
}
