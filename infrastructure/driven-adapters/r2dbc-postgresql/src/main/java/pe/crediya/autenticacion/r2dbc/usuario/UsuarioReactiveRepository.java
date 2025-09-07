package pe.crediya.autenticacion.r2dbc.usuario;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.crediya.autenticacion.r2dbc.usuario.entity.UsuarioEntity;
import reactor.core.publisher.Mono;

// TODO: This file is just an example, you should delete or modify it
public interface UsuarioReactiveRepository extends ReactiveCrudRepository<UsuarioEntity, Long>, ReactiveQueryByExampleExecutor<UsuarioEntity> {

    Mono<UsuarioEntity> findByEmail(String email);

    Mono<Boolean> existsByEmail(String email);

    Mono<UsuarioEntity> findByDocumentoIdentidad(String documento);
}
