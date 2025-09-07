package pe.crediya.autenticacion.model.usuario.gateways;

import pe.crediya.autenticacion.model.usuario.TokenAutenticacion;
import pe.crediya.autenticacion.model.usuario.TokenValidation;
import pe.crediya.autenticacion.model.usuario.Usuario;
import reactor.core.publisher.Mono;

public interface TokenRepository {

    Mono<TokenAutenticacion> generarToken(Usuario usuario);
    Mono<TokenValidation> validarToken(String token);
}
