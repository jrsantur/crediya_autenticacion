package pe.crediya.autenticacion.usecase.token;

import lombok.RequiredArgsConstructor;
import pe.crediya.autenticacion.model.usuario.TokenAutenticacion;
import pe.crediya.autenticacion.model.usuario.TokenValidation;
import pe.crediya.autenticacion.model.usuario.exception.AuthException;
import pe.crediya.autenticacion.model.usuario.exception.TokenInvalidoException;
import pe.crediya.autenticacion.model.usuario.gateways.TokenRepository;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TokenUseCase {

    private final TokenRepository tokenRepository;

    public Mono<TokenValidation> validarToken(String token){
        return Mono.justOrEmpty(token)
                .switchIfEmpty(Mono.error(new AuthException("El token es requerido")))
                .flatMap(tokenRepository::validarToken);
    }


}
