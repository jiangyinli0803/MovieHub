package org.example.backend.repository;

import org.example.backend.model.UserWatchlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWatchlistRepository extends MongoRepository<UserWatchlist,String> {
    UserWatchlist findByUserId(String userId);

}
