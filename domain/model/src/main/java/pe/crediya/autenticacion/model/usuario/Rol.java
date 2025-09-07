package pe.crediya.autenticacion.model.usuario;

import lombok.*;
import pe.crediya.autenticacion.model.usuario.exception.DomainValidationException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Rol {

    private int id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private boolean activo;
    private Set<Permiso> permisos;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public Rol(String codigo, String nombre, String descripcion, boolean activo, Set<Permiso> permisos, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
        this.permisos = permisos;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    public boolean tienePermiso(String codigoPermiso) {
        if (!activo) {
            return false;
        }
        return permisos.stream()
                .anyMatch(permiso -> permiso.getCodigo().equals(codigoPermiso) && permiso.isActivo());
    }

    public boolean tienePermiso(AccionSistema accion) {
        return tienePermiso(accion.name());
    }

    public Set<String> getCodigosPermisos() {
        return permisos.stream()
                .filter(Permiso::isActivo)
                .map(Permiso::getCodigo)
                .collect(java.util.stream.Collectors.toSet());
    }

    private void validarDatos() {
        if (codigo == null || codigo.trim().isEmpty()) {
            Mono.error(new DomainValidationException("El código del rol es obligatorio"));
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            Mono.error(new DomainValidationException("El nombre del rol es obligatorio"));
        }

        if (codigo.trim().length() < 2) {
            Mono.error(new DomainValidationException("El código del rol debe tener al menos 2 caracteres"));
        }

        if (nombre.trim().length() < 2) {
            Mono.error(new DomainValidationException("El nombre del rol debe tener al menos 2 caracteres"));
        }
    }


}
/*
public enum Rol {
    ADMIN("Administrador", Set.of(
            AccionSistema.CREAR_USUARIO,
            AccionSistema.CONSULTAR_USUARIO,
            AccionSistema.ACTUALIZAR_USUARIO,
            AccionSistema.CREAR_SOLICITUD,
            AccionSistema.CONSULTAR_SOLICITUD,
            AccionSistema.APROBAR_SOLICITUD,
            AccionSistema.DESEMBOLSAR_SOLICITUD
    )),

    ASESOR("Asesor", Set.of(
            AccionSistema.CREAR_USUARIO,
            AccionSistema.CONSULTAR_USUARIO,
            AccionSistema.CREAR_SOLICITUD,
            AccionSistema.CONSULTAR_SOLICITUD,
            AccionSistema.APROBAR_SOLICITUD
    )),

    CLIENTE("Cliente", Set.of(
            AccionSistema.CREAR_SOLICITUD,
            AccionSistema.CONSULTAR_SOLICITUD
    ));

    private final String descripcion;
    private final Set<AccionSistema> permisos;

    Rol(String descripcion, Set<AccionSistema> permisos) {
        this.descripcion = descripcion;
        this.permisos = Set.copyOf(permisos); // Inmutable
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Set<AccionSistema> getPermisos() {
        return permisos;
    }

    public boolean tienePermiso(AccionSistema accion) {
        return permisos.contains(accion);
    }
}
*/