package pe.crediya.autenticacion.usecase.usuario;

import lombok.RequiredArgsConstructor;
import pe.crediya.autenticacion.model.usuario.AccionSistema;
import pe.crediya.autenticacion.model.usuario.EstadoUsuario;
import pe.crediya.autenticacion.model.usuario.Usuario;
import pe.crediya.autenticacion.model.usuario.exception.*;
import pe.crediya.autenticacion.model.usuario.gateways.CriptoRepository;
import pe.crediya.autenticacion.model.usuario.gateways.TokenRepository;
import pe.crediya.autenticacion.model.usuario.gateways.UsuarioRepository;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final TokenRepository tokenRepository;
    private final CriptoRepository criptoRepository;

    public Mono<Usuario> registrar(Usuario usuario){
        return Mono.justOrEmpty(usuario)
                .switchIfEmpty(Mono.error(new DomainValidationException("Usuario es requerido")))
                .flatMap(Usuario::validarDatos)
                .flatMap(u -> validarUsuarioNoExiste(u.getEmail()).thenReturn(u))
                .flatMap(this::encriptarPassword)
                .flatMap(usuarioRepository::guardarUsuario);

    }

    private Mono<Boolean> validarUsuarioNoExiste(String email) {
        return usuarioRepository.existePorEmail(email)
                .flatMap(existe -> {
                    if (existe) {
                        return Mono.error(new UsuarioYaExisteException("Usuario ya se encuentra registrado en el sistema"));
                    }
                    return Mono.just(true);
                });
    }

    private Mono<Usuario> encriptarPassword(Usuario usuario) {
        return Mono.fromCallable(() -> {
            String passwordEncriptada = criptoRepository.encode(usuario.getPassword());
            return usuario.toBuilder()  // O usar Builder pattern
                    .password(passwordEncriptada)
                    .build();
        });
    }
}
