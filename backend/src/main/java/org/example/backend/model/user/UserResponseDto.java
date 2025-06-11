package org.example.backend.model.user;

import lombok.Data;

@Data
public class UserResponseDto {
    private String id;
    private String username;
    private String email;
    private String avatarUrl;

    public UserResponseDto(String id, String username, String email, String avatarUrl) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }
}
