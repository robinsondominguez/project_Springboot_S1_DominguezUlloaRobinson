package com.logitrack.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> manejarNoEncontrado(ResourceNotFoundException ex) {

        Map<String, Object> error = new HashMap<>();

        error.put("fecha", LocalDateTime.now());
        error.put("error", "Recurso no encontrado");
        error.put("mensaje", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> manejarBadCredentials(BadCredentialsException ex) {

        Map<String, Object> error = new HashMap<>();

        error.put("fecha", LocalDateTime.now());
        error.put("error", "Credenciales incorrectas");
        error.put("mensaje", "Usuario o contraseña incorrectos");

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> manejarErrorGeneral(Exception ex) {

        Map<String, Object> error = new HashMap<>();

        error.put("fecha", LocalDateTime.now());
        error.put("error", "Error interno");
        error.put("mensaje", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
