package pe.crediya.autenticacion.model.usuario;

import lombok.*;
import pe.crediya.autenticacion.model.usuario.exception.AuthException;
import reactor.core.publisher.Mono;

@Getter
@Setter
@NoArgsConstructor
@Builder(toBuilder = true)
public class CredencialesLogin {
    private String email;
    private String clave;

    public CredencialesLogin(String email, String clave) {
        this.email = email;
        this.clave = clave;
    }

    public static Mono<CredencialesLogin> validarDatos(CredencialesLogin credenciales){
        if(credenciales.getEmail() == null || credenciales.getEmail().isBlank())
            return Mono.error(new AuthException("email es requerido"));
        if(credenciales.getClave() == null || credenciales.getClave().isBlank())
            return Mono.error(new AuthException("clave es requerida"));
        return Mono.just(credenciales);
    }
}
