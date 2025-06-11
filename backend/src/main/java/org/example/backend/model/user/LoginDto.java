package org.example.backend.model.user;

import lombok.Data;

@Data
public class LoginDto{
    private String email;
    private String password;
}
