package pe.crediya.autenticacion.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para registrar un nuevo usuario")
public class UsuarioRequestDto {

    @NotBlank(message = "El documento de identidad es obligatorio")
    @Pattern(regexp = "^[0-9]{8,15}$", message = "El documento debe contener entre 8 y 15 dígitos")
    @Schema(description = "Documento de identidad", example = "12345678901")
    @JsonProperty("documentoIdentidad")
    private String documentoIdentidad;

    @NotBlank(message = "Los nombres son obligatorios")
    @Size(min = 2, message = "Los nombres deben tener al menos 2 caracteres")
    @Schema(description = "Nombres del usuario", example = "Juan Carlos")
    @JsonProperty("nombres")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(min = 2, message = "Los apellidos deben tener al menos 2 caracteres")
    @Schema(description = "Apellidos del usuario", example = "García López")
    @JsonProperty("apellidos")
    private String apellidos;

    @NotBlank(message = "La fecha de nacimiento es obligatoria")
    @Schema(description = "Fecha de nacimiento del usuario en formato AAAA-MM-DD")
    @JsonProperty("fechaNacimiento")
    private LocalDate fechaNacimiento;

    @NotBlank(message =  "La direccion es obligatoria")
    @JsonProperty("direccion")
    private String direccion;

    @JsonProperty("telefono")
    private String telefono;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email es inválido")
    @Schema(description = "Email del usuario", example = "nuevo.usuario@crediya.com")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Salario base es obligatorio")
    @Schema(description = "Salario del usuario", example = "12 000 000")
    @JsonProperty("salarioBase")
    private double salarioBase;

    @NotBlank(message = "El rol es obligatorio")
    @Schema(description = "Rol del usuario",
            example = "CLIENTE",
            allowableValues = {"ADMIN", "ASESOR", "CLIENTE"})
    @JsonProperty("rol")
    private String rol;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @Schema(description = "Contraseña del usuario", example = "password123")
    @JsonProperty("password")
    private String password;












}
