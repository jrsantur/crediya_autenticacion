package pe.crediya.autenticacion.model.usuario;
import lombok.*;
//import lombok.NoArgsConstructor;
import pe.crediya.autenticacion.model.usuario.exception.DomainValidationException;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Usuario {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$"
    );
    private static final int MIN_PASSWORD_LENGTH = 6;

    private long id;
    private String email;
    private String password;
    private String documentoIdentidad;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String direccion;
    private double salarioBase;
    private String rol;
    @Builder.Default
    private EstadoUsuario estado = EstadoUsuario.ACTIVO;
    @Builder.Default
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    private LocalDateTime fechaActualizacion;

    public Usuario(String email, String password, String documentoIdentidad, String nombres, String apellidos, String direccio, LocalDate fechaNacimiento, double salarioBase, String rol) {
        this.email = email;
        this.password = password; // En un caso real, aquí se debería hashear la contraseña
        this.documentoIdentidad = documentoIdentidad;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.salarioBase = salarioBase;
        this.rol = rol;
        this.estado = EstadoUsuario.ACTIVO;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();

    }

    public Mono<Usuario> validarDatos() {
        return Mono.fromCallable(() -> {
            validarEmail();
            validarPassword();
            validarDocumentoIdentidad();
            validarNombres();
            validarApellidos();
            validarFechaNacimiento();
            validarSalarioBase();
            validarDireccion();
            return this;
        });
    }

    private void validarEmail() {
        if (email == null || email.trim().isEmpty()) {
            throw  new DomainValidationException("El email es obligatorio");
        }

        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw  new DomainValidationException("El formato del email es inválido");
        }
    }

    private void validarPassword() {
        if (password == null || password.trim().isEmpty()) {
            throw new DomainValidationException("El hash de contraseña es obligatorio");
        }

        if (password.trim().length() < 6) {
            throw  new DomainValidationException("El hash de contraseña es inválido");
        }
    }

    private void validarDocumentoIdentidad() {
        if (documentoIdentidad == null || documentoIdentidad.trim().isEmpty()) {
            throw new DomainValidationException("El documento de identidad es obligatorio");
        }

        if (!documentoIdentidad.trim().matches("^[0-9]{8,15}$")) {
            throw new DomainValidationException("El documento debe contener entre 8 y 15 dígitos");
        }
    }

    private void validarNombres() {
        if (nombres == null || nombres.trim().isEmpty()) {
            throw  new DomainValidationException("Los nombres son obligatorios");
        }

        if (nombres.trim().length() < 2) {
            throw new DomainValidationException("Los nombres deben tener al menos 2 caracteres");
        }
    }

    private void validarApellidos() {
        if (apellidos == null || apellidos.trim().isEmpty()) {
            throw  new DomainValidationException("Los apellidos son obligatorios");
        }

        if (apellidos.trim().length() < 2) {
            throw  new DomainValidationException("Los apellidos deben tener al menos 2 caracteres");
        }
    }

    private void validarFechaNacimiento() {
        if (fechaNacimiento == null) {
            throw new DomainValidationException("La fecha de nacimiento es obligatoria");
        }

        if (fechaNacimiento.isAfter(LocalDate.now().minusYears(18))) {
            throw new DomainValidationException("El usuario debe ser mayor de 18 años");
        }
    }

    private void validarSalarioBase() {
        if (salarioBase <= 0 ){
            throw new DomainValidationException("El salario base debe ser mayor a 0");
        }
        if (salarioBase > 15000000){
            throw new DomainValidationException("El salario base debe ser menor a 15000000");
        }

    }

    private void validarDireccion(){
        if(direccion == null  || direccion.trim().isEmpty()){
            throw new DomainValidationException("La direccion de usuario es obligatoria");
        }
    }

}
