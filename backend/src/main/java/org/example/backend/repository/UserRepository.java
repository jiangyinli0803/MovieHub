package org.example.backend.repository;

import org.example.backend.model.user.AuthProvider;
import org.example.backend.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByAuthProviderAndProviderId(AuthProvider provider, String providerId);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
