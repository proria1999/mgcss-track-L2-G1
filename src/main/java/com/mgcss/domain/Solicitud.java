package com.mgcss.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import com.mgcss.infrastructure.persistence.ClienteEntity;

@Getter
@Setter

public class Solicitud {

	private int id;
	private Cliente cliente;
	private String descripcion;
	private LocalDate fechaCreacion;
	private EstadoSolicitud estado;
	private Tecnico tecnicoAsignado; // opcional
	private LocalDate fechaCierre; // nullable
	
    public Solicitud(int id, Cliente cliente, String descripcion) {
        this.id = id;
        this.cliente = cliente;
        this.descripcion = descripcion;
        this.fechaCreacion = LocalDate.now();
        this.estado = EstadoSolicitud.ABIERTA;
        this.tecnicoAsignado = null;
        this.fechaCierre = null;
    }
}