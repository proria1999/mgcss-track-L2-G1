package com.mgcss.domain;

import java.time.LocalDate;

public class Solicitud {

    private int id;
    private EstadoSolicitud estado;
    private LocalDate fechaCreacion;
    private Tecnico tecnicoAsignado;

    public Solicitud(EstadoSolicitud estado) {
        this.estado = estado;
        this.fechaCreacion = LocalDate.now();
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void cerrar() {
        if (this.estado != EstadoSolicitud.EN_PROCESO) {
            throw new IllegalStateException("No se puede cerrar si no está en proceso");
        }
        this.estado = EstadoSolicitud.CERRADA;
    }

    public void asignarTecnico(Tecnico tecnico) {
        if (!tecnico.isActivo()) {
            throw new IllegalArgumentException("El técnico no está activo");
        }
        this.tecnicoAsignado = tecnico;
    }
}