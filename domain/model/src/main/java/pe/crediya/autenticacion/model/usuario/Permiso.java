package pe.crediya.autenticacion.model.usuario;

import lombok.*;
import pe.crediya.autenticacion.model.usuario.exception.DomainValidationException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Permiso {
    private int id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String recurso;
    private String accion;
    private boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public Permiso(String codigo, String nombre, String descripcion, String recurso, String accion, boolean activo, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.recurso = recurso;
        this.accion = accion;
        this.activo = activo;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    private void validarDatos() {
        if (codigo == null || codigo.trim().isEmpty()) {
            Mono.error(new DomainValidationException("El código del permiso es obligatorio"));
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            Mono.error(new DomainValidationException("El nombre del permiso es obligatorio"));
        }

        if (codigo.trim().length() < 2) {
            Mono.error(new DomainValidationException("El código del permiso debe tener al menos 2 caracteres"));
        }
    }

}
