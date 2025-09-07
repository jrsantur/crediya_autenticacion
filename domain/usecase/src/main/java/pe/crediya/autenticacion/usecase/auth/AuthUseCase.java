package pe.crediya.autenticacion.usecase.auth;

import lombok.RequiredArgsConstructor;
import pe.crediya.autenticacion.model.usuario.*;
import pe.crediya.autenticacion.model.usuario.exception.AuthException;
import pe.crediya.autenticacion.model.usuario.exception.UsuarioInactivoException;
import pe.crediya.autenticacion.model.usuario.exception.UsuarioNoEncontradoException;
import pe.crediya.autenticacion.model.usuario.gateways.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class AuthUseCase {

    private final UsuarioRepository usuarioRepository;
    private final TokenRepository tokenRepository;
    private final CriptoRepository criptoRepository;

    public Mono<TokenAutenticacion> autenticar(CredencialesLogin credenciales) {

        return Mono.justOrEmpty(credenciales)
                .switchIfEmpty(Mono.error(new AuthException("Las credenciales son requeridas")))
                .flatMap(CredencialesLogin::validarDatos) // método de instancia
                .flatMap(c -> usuarioRepository.buscarPorEmail(c.getEmail())
                        .switchIfEmpty(Mono.error(new AuthException("Usuario o contraseña incorrectos")))
                        .flatMap(usuario -> validarCredenciales(usuario, c))
                        .flatMap(this::validarEstadoUsuario)
                        .flatMap(tokenRepository::generarToken));


    }


    private Mono<Usuario> validarCredenciales(Usuario usuario, CredencialesLogin credenciales) {
        boolean passwordValida =  criptoRepository.matches(credenciales.getClave(), usuario.getPassword());
        if (!passwordValida) {
            return Mono.error(new AuthException("Email o contraseña incorrectos"));
        }

        return Mono.just(usuario);
    }

    private Mono<Usuario> buscarUsuario(String email) {
        return usuarioRepository.buscarPorEmail(email)
                .switchIfEmpty(Mono.error(new UsuarioNoEncontradoException(email)));
    }

    private Mono<Usuario> validarEstadoUsuario(Usuario usuario) {
        if (usuario.getEstado() != EstadoUsuario.ACTIVO) {
            return Mono.error(new UsuarioInactivoException(
                    "El usuario está " + usuario.getEstado().getDescripcion().toLowerCase()
            ));
        }
        return Mono.just(usuario);
    }

    private Mono<CredencialesLogin> validarCredencialesNoNulas(CredencialesLogin credenciales) {
        if (credenciales == null) {
            return Mono.error(new AuthException("Las credenciales son requeridas"));
        }
        return Mono.just(credenciales);
    }



}
