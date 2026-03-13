package com.logitrack.service;

import com.logitrack.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> listarUsuarios();

    Optional<Usuario> buscarPorUsername(String username);

    Usuario guardar(Usuario usuario);

}