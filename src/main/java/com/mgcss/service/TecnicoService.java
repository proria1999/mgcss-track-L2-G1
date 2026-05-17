package com.mgcss.service;

import com.mgcss.infrastructure.persistence.JpaTecnicoRepository;
import com.mgcss.infrastructure.persistence.TecnicoEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TecnicoService {

    private final JpaTecnicoRepository jpaTecnicoRepository;

    public TecnicoService(JpaTecnicoRepository jpaTecnicoRepository) {
        this.jpaTecnicoRepository = jpaTecnicoRepository;
    }

    public TecnicoEntity buscarTecnicoOError(Long id, String mensajeError) {
        return jpaTecnicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(mensajeError));
    }

    public List<TecnicoEntity> listarTodos() {
        return jpaTecnicoRepository.findAll();
    }
}