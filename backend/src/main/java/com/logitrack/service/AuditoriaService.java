package com.logitrack.service;

import com.logitrack.model.Auditoria;
import java.util.List;

public interface AuditoriaService {

    List<Auditoria> listarAuditorias();

    Auditoria guardar(Auditoria auditoria);

    List<Auditoria> buscarPorUsuario(String usuario);

    List<Auditoria> buscarPorOperacion(String operacion);

}