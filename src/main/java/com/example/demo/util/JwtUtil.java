package com.example.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtUtil {
    private final static String key = "JWT_SIGN_KEY";
    private final static long expireInMs = 60 * 1000 * 10;
    public String generate(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expireInMs))
                .signWith(SignatureAlgorithm.HS256, key.getBytes(StandardCharsets.UTF_8))
                .compact();
    }
    public boolean validate(String token) {
        if (getUsername(token) != null && isExpired(token)) {
            return true;
        }
        return false;
    }
    public String getUsername(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }
    public Boolean isExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }
}
