package com.logitrack.service.impl;

import com.logitrack.model.Auditoria;
import com.logitrack.repository.AuditoriaRepository;
import com.logitrack.service.AuditoriaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditoriaServiceImpl implements AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;

    public AuditoriaServiceImpl(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    @Override
    public List<Auditoria> listarAuditorias() {
        return auditoriaRepository.findAll();
    }

    @Override
    public Auditoria guardar(Auditoria auditoria) {
        return auditoriaRepository.save(auditoria);
    }

    @Override
    public List<Auditoria> buscarPorUsuario(String usuario) {
        return auditoriaRepository.findByUsuario(usuario);
    }

    @Override
    public List<Auditoria> buscarPorOperacion(String operacion) {
        return auditoriaRepository.findByOperacion(operacion);
    }
}