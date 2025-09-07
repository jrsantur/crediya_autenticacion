package pe.crediya.autenticacion.r2dbc.rol.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("roles")
public class RolEntity {
    @Id
    private int id;

    @Column("codigo")
    private String codigo;

    @Column("nombre")
    private String nombre;

    @Column("descripcion")
    private String descripcion;

    @Column("activo")
    private boolean activo;

    @Column("fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column("fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
}
