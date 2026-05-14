package com.mgcss.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.mgcss.domain.TipoCliente;
import com.mgcss.infrastructure.persistence.ClienteEntity;
import com.mgcss.infrastructure.persistence.JpaClienteRepository;

@Service
public class ClienteService {

    private final JpaClienteRepository jpaClienteRepository;

    // Constructor para Inyección de Dependencias
    public ClienteService(JpaClienteRepository jpaClienteRepository) {
        this.jpaClienteRepository = jpaClienteRepository;
    }

    public ClienteEntity crearCliente(String nombre, String tipo) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío");
        }

        ClienteEntity cliente = new ClienteEntity();
        cliente.setNombre(nombre);
        cliente.setTipo(TipoCliente.STANDARD); 
        
        return jpaClienteRepository.save(cliente);
    }

    //Lista todos los clientes registrados.
    public List<ClienteEntity> listarTodos() {
        return jpaClienteRepository.findAll();
    }

    
    //Método de ayuda para buscar un cliente o lanzar error si no existe.
    public ClienteEntity buscarClienteOError(Long clienteId, String mensajeError) {
        return jpaClienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException(mensajeError));
    }

    // Obtiene un cliente por su ID.
    public ClienteEntity obtenerPorId(Long id) {
        return buscarClienteOError(id, "Cliente no encontrado con ID: " + id);
    }
}