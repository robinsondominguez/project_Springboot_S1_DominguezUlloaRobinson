package com.logitrack.service.impl;

import com.logitrack.model.Bodega;
import com.logitrack.repository.BodegaRepository;
import com.logitrack.service.BodegaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BodegaServiceImpl implements BodegaService {

    private final BodegaRepository bodegaRepository;

    public BodegaServiceImpl(BodegaRepository bodegaRepository) {
        this.bodegaRepository = bodegaRepository;
    }

    @Override
    public List<Bodega> listarBodegas() {
        return bodegaRepository.findAll();
    }

    @Override
    public Bodega obtenerPorId(Long id) {
        Optional<Bodega> bodega = bodegaRepository.findById(id);
        return bodega.orElse(null);
    }

    @Override
    public Bodega guardar(Bodega bodega) {
        return bodegaRepository.save(bodega);
    }

    @Override
    public Bodega actualizar(Long id, Bodega bodega) {

        Bodega bodegaExistente = bodegaRepository.findById(id).orElse(null);

        if (bodegaExistente != null) {
            bodegaExistente.setNombre(bodega.getNombre());
            bodegaExistente.setUbicacion(bodega.getUbicacion());
            bodegaExistente.setCapacidad(bodega.getCapacidad());
            bodegaExistente.setEncargado(bodega.getEncargado());

            return bodegaRepository.save(bodegaExistente);
        }

        return null;
    }

    @Override
    public void eliminar(Long id) {
        bodegaRepository.deleteById(id);
    }
}