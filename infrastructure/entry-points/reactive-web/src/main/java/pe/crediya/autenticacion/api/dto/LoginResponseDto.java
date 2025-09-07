package pe.crediya.autenticacion.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta de autenticación exitosa")
public class LoginResponseDto {

    @Schema(description = "Token de acceso JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    @JsonProperty("accessToken")
    private String accessToken;

    @Schema(description = "Token para renovación", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    @JsonProperty("refreshToken")
    private String refreshToken;

    @Schema(description = "Tipo de token", example = "Bearer")
    @JsonProperty("tokenType")
    private String tokenType;

    @Schema(description = "Email del usuario", example = "usuario@crediya.com")
    @JsonProperty("email")
    private String email;

    @Schema(description = "Rol del usuario", example = "CLIENTE")
    @JsonProperty("rol")
    private String rol;

    @Schema(description = "Descripción del rol", example = "Cliente")
    @JsonProperty("rolDescripcion")
    private String rolDescripcion;

    @Schema(description = "Permisos del usuario")
    @JsonProperty("permisos")
    private Set<String> permisos;

    @Schema(description = "Fecha de expiración del token")
    @JsonProperty("fechaExpiracion")
    private LocalDateTime fechaExpiracion;

}
