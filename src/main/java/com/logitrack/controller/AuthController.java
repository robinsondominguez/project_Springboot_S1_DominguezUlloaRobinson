package com.logitrack.controller;

import com.logitrack.model.Usuario;
import com.logitrack.security.JwtUtil;
import com.logitrack.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "API para login y registro de usuarios")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UsuarioService usuarioService,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar nuevo usuario")
    public Usuario register(@RequestBody Usuario usuario) {

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return usuarioService.guardar(usuario);
    }

    @PostMapping("/login")
    @Operation(summary = "Login de usuario y generación de token JWT")
    public String login(@RequestBody Usuario usuario) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        usuario.getUsername(),
                        usuario.getPassword()
                )
        );

        return jwtUtil.generarToken(usuario.getUsername());
    }
}