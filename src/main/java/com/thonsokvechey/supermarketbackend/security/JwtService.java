package com.thonsokvechey.supermarketbackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class JwtService {

    private final SecretKey signingKey;
    private final long accessTokenExpirationMs;
    private final long refreshTokenExpirationMs;
    private final Map<String, Date> blacklistedTokens = new ConcurrentHashMap<>();

    public JwtService(
            @Value("${post.free.app.jwtSecret}") String jwtSecret,
            @Value("${post.free.app.jwtExpirationMs:86400000}") long accessTokenExpirationMs,
            @Value("${post.free.app.jwtRefreshExpirationMs:604800000}") long refreshTokenExpirationMs
    ) {
        this.signingKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpirationMs = accessTokenExpirationMs;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
    }

    public String generateAccessToken(CustomUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userDetails.getId().toString());
        claims.put("email", userDetails.getEmail());
        claims.put("roles", userDetails.getRoles());
        claims.put("permissions", userDetails.getPermissions());
        claims.put("tokenType", "ACCESS");

        return buildToken(claims, userDetails.getUsername(), accessTokenExpirationMs);
    }

    public String generateRefreshToken(CustomUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userDetails.getId().toString());
        claims.put("tokenType", "REFRESH");

        return buildToken(claims, userDetails.getUsername(), refreshTokenExpirationMs);
    }

    private String buildToken(Map<String, Object> extraClaims, String subject, long expirationMs) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .claims(extraClaims)
                .subject(subject)
                .issuedAt(new Date(now))
                .expiration(new Date(now + expirationMs))
                .signWith(signingKey)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public UUID extractUserId(String token) {
        String userId = extractAllClaims(token).get("userId", String.class);
        return UUID.fromString(userId);
    }

    public String extractTokenType(String token) {
        return extractAllClaims(token).get("tokenType", String.class);
    }

    public void blacklistToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            blacklistedTokens.put(token, claims.getExpiration());
            blacklistedTokens.entrySet().removeIf(entry -> entry.getValue().before(new Date()));
        } catch (Exception ignored) {
            // Token already expired or invalid
        }
    }

    public boolean isTokenBlacklisted(String token) {
        Date exp = blacklistedTokens.get(token);
        if (exp == null) {
            return false;
        }
        if (exp.before(new Date())) {
            blacklistedTokens.remove(token);
            return false;
        }
        return true;
    }

    public boolean isTokenValid(String token, String expectedTokenType) {
        if (isTokenBlacklisted(token)) {
            return false;
        }
        Claims claims = extractAllClaims(token);
        String tokenType = claims.get("tokenType", String.class);
        Date expiration = claims.getExpiration();
        return expectedTokenType.equals(tokenType) && expiration.after(new Date());
    }

    public long getAccessTokenExpirationMs() {
        return accessTokenExpirationMs;
    }
}
