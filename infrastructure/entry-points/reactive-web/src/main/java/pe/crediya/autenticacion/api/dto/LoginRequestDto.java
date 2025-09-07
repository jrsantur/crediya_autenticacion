package pe.crediya.autenticacion.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Credenciales para inicio de sesión")
public class LoginRequestDto {

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email es inválido")
    @Schema(description = "Email del usuario", example = "usuario@crediya.com")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @Schema(description = "Contraseña del usuario", example = "password123")
    @JsonProperty("password")
    private String password;
}
