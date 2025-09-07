package pe.crediya.autenticacion.model.usuario.gateways;

import pe.crediya.autenticacion.model.usuario.Rol;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RolRepository {

    Mono<Rol> buscarPorId(int id);

    Mono<Rol> buscarPorCodigo(String codigo);

    Flux<Rol> obtenerRolesActivos();

    Mono<Boolean> existePorId(String id);

    Mono<Boolean> existePorCodigo(String codigo);

}
