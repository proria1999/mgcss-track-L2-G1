package com.mgcss.infrastructure.persistence;

import java.time.LocalDate;
import com.mgcss.domain.Cliente;
import com.mgcss.domain.EstadoSolicitud;
import com.mgcss.domain.Tecnico;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "solicitudes")
@Getter 
@Setter 
@NoArgsConstructor // Necesario para JPA
@AllArgsConstructor // Genera el de 7 parámetros
public class SolicitudEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private String descripcion;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estado;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnicoAsignado;

    @Column(name = "fecha_cierre")
    private LocalDate fechaCierre;

    // EL CONSTRUCTOR QUE TUS TESTS RECLAMAN:
    // Lo escribimos manualmente porque tiene lógica personalizada (LocalDate.now())
    public SolicitudEntity(long id, Cliente cliente, String descripcion) {
        this.id = id;
        this.cliente = cliente;
        this.descripcion = descripcion;
        this.fechaCreacion = LocalDate.now();
        this.estado = EstadoSolicitud.ABIERTA;
    }
}