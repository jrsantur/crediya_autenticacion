package pe.crediya.autenticacion.model.usuario;

import lombok.*;
import pe.crediya.autenticacion.model.usuario.exception.DomainValidationException;
import pe.crediya.autenticacion.model.usuario.exception.TokenInvalidoException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TokenAutenticacion {
    private String accessToken;
    private LocalDateTime fechaExpiracion;
    private LocalDateTime fechaCreacion;

    public TokenAutenticacion(String accessToken, LocalDateTime fechaExpiracion) {
        this.accessToken = accessToken;
        this.fechaExpiracion = fechaExpiracion;
        this.fechaCreacion = LocalDateTime.now();
    }

    public Mono<TokenAutenticacion> validarToken(TokenAutenticacion token) {
        if (token == null || token.getAccessToken() == null || token.getAccessToken().isBlank()) {
            return Mono.error(new TokenInvalidoException("El token de acceso es requerido"));
        }
        if (token.getFechaExpiracion() == null) {
            return Mono.error(new TokenInvalidoException("La fecha de expiración es requerida"));
        }
        if (token.haExpirado()) {
            return Mono.error(new TokenInvalidoException("El token ha expirado"));
        }
        return Mono.just(token);
    }

    public boolean haExpirado() {
        return LocalDateTime.now().isAfter(fechaExpiracion);
    }

    public boolean tienePermiso(AccionSistema accion) {
        return !haExpirado();
    }
}
