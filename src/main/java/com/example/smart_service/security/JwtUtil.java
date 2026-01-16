package com.example.smart_service.security;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.smart_service.entity.RoleEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private static final long EXPIRTION_MS = 36000000;
    private static final String SECRET = "my-very-strong-secret-key-that-is-long-enough-123456";
    private final Key key;

    public JwtUtil() {
        String base64Secret = Base64.getEncoder().encodeToString(SECRET.getBytes());
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Secret));
    }

    public String generateAccessToken(Long userId, List<RoleEntity> roles) {

        List<String> roleNames = roles.stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("roles", roleNames)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRTION_MS))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Long userId, List<RoleEntity> roles) {

        List<String> roleNames = roles.stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("roles", roleNames)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRTION_MS))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean valiadeAccessToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Extract userId
    public Long getUserIdFromToken(String token) {
        return Long.parseLong(parseClaims(token).getSubject());
    }

    // Extract roles
    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token) {
        return parseClaims(token).get("roles", List.class);
    }

}
