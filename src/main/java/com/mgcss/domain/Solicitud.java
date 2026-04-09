package com.mgcss.domain;

import java.time.LocalDate;
//
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
    
	public void asignarTecnico(Tecnico tecnico) {
	    if (!tecnico.isActivo()) {
	        throw new IllegalArgumentException("Solo se puede asignar un técnico activo.");
	    }
		this.tecnicoAsignado = tecnico;
		this.estado = EstadoSolicitud.EN_PROCESO;
	}
	
	public void cerrarSolicitud() {
	    if (this.estado != EstadoSolicitud.EN_PROCESO) {
	        throw new IllegalStateException("Solo se puede cerrar una solicitud si está EN_PROCESO.");
	    }
		this.estado = EstadoSolicitud.CERRADA;
		this.fechaCierre = LocalDate.now();
	}

	public EstadoSolicitud getEstado() {
		return estado;
	}

	public Tecnico getTecnicoAsignado() {
		return tecnicoAsignado;
	}
	
	public int getId() {
		return id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public LocalDate getFechaCierre() {
		return fechaCierre;
	}
	
}