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
import lombok.Builder;

@Entity
@Table(name = "solicitudes")
@Getter // Genera todos los getters
@Setter // Genera todos los setters
@NoArgsConstructor // Genera el constructor vacío (obligatorio para JPA)
@AllArgsConstructor // Genera un constructor con todos los campos
@Builder // Permite crear objetos con un patrón de diseño fluido
public class SolicitudEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private String descripcion;

    @Column(name = "fecha_creacion")
    @Builder.Default
    private LocalDate fechaCreacion = LocalDate.now();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EstadoSolicitud estado = EstadoSolicitud.ABIERTA;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnicoAsignado;

    @Column(name = "fecha_cierre")
    private LocalDate fechaCierre;
}