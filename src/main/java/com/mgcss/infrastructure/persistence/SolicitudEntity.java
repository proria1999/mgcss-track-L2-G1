package com.mgcss.infrastructure.persistence;

import java.time.LocalDate;
import com.mgcss.domain.EstadoSolicitud;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter 
@Setter 
@NoArgsConstructor // Necesario para JPA
@AllArgsConstructor // Genera el de 7 parámetros
@Entity
@Table(name = "solicitudes")

public class SolicitudEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;

    private String descripcion;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estado;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private TecnicoEntity tecnicoAsignado;

    @Column(name = "fecha_cierre")
    private LocalDate fechaCierre;

    public SolicitudEntity(long id, ClienteEntity cliente, String descripcion) {
        this.id = id;
        this.cliente = cliente;
        this.descripcion = descripcion;
        this.fechaCreacion = LocalDate.now();
        this.estado = EstadoSolicitud.ABIERTA;
    }
}