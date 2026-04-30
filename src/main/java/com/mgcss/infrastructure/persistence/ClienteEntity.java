package com.mgcss.infrastructure.persistence;

import com.mgcss.domain.TipoCliente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clientes")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;

    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cliente")
    private TipoCliente tipo;
}