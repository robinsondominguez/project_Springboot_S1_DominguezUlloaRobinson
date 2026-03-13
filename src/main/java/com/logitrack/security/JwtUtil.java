package com.logitrack.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "logitrack_secret_key";

    public String generarToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 día
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
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

        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean tokenExpirado(String token) {
        return extraerClaims(token).getExpiration().before(new Date());
    }
}