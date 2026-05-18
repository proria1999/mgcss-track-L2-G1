package com.mgcss.service;

import com.mgcss.domain.EspecialidadTecnico;
import com.mgcss.domain.TipoCliente;
import com.mgcss.infrastructure.persistence.ClienteEntity;
import com.mgcss.infrastructure.persistence.JpaTecnicoRepository;
import com.mgcss.infrastructure.persistence.SolicitudEntity;
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
    
    public TecnicoEntity crearTecnico(String nombre, EspecialidadTecnico especialidad) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre del tecnico no puede estar vacío");
        }

        TecnicoEntity tecnico = new TecnicoEntity();
        tecnico.setNombre(nombre);
        tecnico.setEspecialidad(EspecialidadTecnico.MANTENIMIENTO);
        
        return jpaTecnicoRepository.save(tecnico);
    }
}