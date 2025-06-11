package org.example.backend.model.user;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;


@Document("users")
@Builder
@Getter
@Setter
public class User {
    @Id
    private String id;

    // for Form Login
    private String username;
    private String email;
    private String password;

    // OAuth Login
    private AuthProvider authProvider;
    private String providerId;
    private String avatarUrl;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
