package pe.crediya.autenticacion.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta de creacion de usuario")
@Builder(toBuilder = true)
public class UsuarioResponseDto {

    @JsonProperty("id")
    long id;

    @JsonProperty("nombre")
    String nombre;

    @JsonProperty("apellidos")
    String apellidos;

    @JsonProperty("email")
    String email;

    @JsonProperty("fechaCreacion")
    private LocalDateTime fechaCreacion;
}
