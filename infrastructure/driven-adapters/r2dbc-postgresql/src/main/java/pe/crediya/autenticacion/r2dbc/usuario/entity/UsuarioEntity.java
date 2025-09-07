package pe.crediya.autenticacion.r2dbc.usuario.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("usuario")
public class UsuarioEntity {

    @Id
    @Column("idusuario")
    private long id;

    @Column("correo_electronico")
    private String email;

    @Column("password")
    private String password;

    @Column("documento_identidad")
    private String documentoIdentidad;

    @Column("nombres")
    private String nombres;

    @Column("apellidos")
    private String apellidos;

    @Column("direccion")
    private String direccion;

    @Column("salario_base")
    private double salarioBase;

    @Column("fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column("rol")
    private String rol;

    @Column("estado")
    private String estado;

    @Column("create_at")
    private LocalDateTime fechaCreacion;

    @Column("update_at")
    private LocalDateTime fechaActualizacion;
}