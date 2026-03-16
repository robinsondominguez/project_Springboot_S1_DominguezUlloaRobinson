package com.logitrack.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    // 1. Clave de al menos 32 caracteres (256 bits)
    private final String SECRET_KEY = "esta_es_una_clave_secreta_para_logitrack_2026_muy_larga";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generarToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 día
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Usar la Key generada
                .compact();
    }

    public String extraerUsername(String token) {
        return extraerClaims(token).getSubject();
    }

    public boolean validarToken(String token, String username) {
        final String usernameToken = extraerUsername(token);
        return (usernameToken.equals(username) && !tokenExpirado(token));
    }

    private Claims extraerClaims(String token) {
        // 3. Usar parserBuilder() para versiones modernas de JJWT
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean tokenExpirado(String token) {
        return extraerClaims(token).getExpiration().before(new Date());
    }
}