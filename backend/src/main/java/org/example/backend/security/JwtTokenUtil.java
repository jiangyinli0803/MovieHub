package org.example.backend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenUtil {
    private final String secret = "mySecretKeymySecretKeymySecretKey1234"; // 至少 256 位
    private final long jwtExpirationMs = 3600000; // 1小时

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // 生成Token
    public String generateToken(String username) {
        Instant now = Instant.now();
        Instant expirationTime = now.plusSeconds(jwtExpirationMs / 1000); // 转换为秒

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
