package pe.crediya.autenticacion.model.usuario.gateways;

import pe.crediya.autenticacion.model.usuario.AccionSistema;
import pe.crediya.autenticacion.model.usuario.Usuario;
import reactor.core.publisher.Mono;

public interface UsuarioRepository {


    Mono<Usuario> guardarUsuario(Usuario usuario);

    Mono<Usuario> buscarPorEmail(String email);

    Mono<Boolean> existePorEmail(String email);

    Mono<Usuario> buscarPorDocumentoIdentidad(String documento);
}
