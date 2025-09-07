package pe.crediya.autenticacion.r2dbc.rol;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.crediya.autenticacion.r2dbc.rol.entity.RolEntity;
import reactor.core.publisher.Mono;

// TODO: This file is just an example, you should delete or modify it
public interface RolReactiveRepository extends ReactiveCrudRepository<RolEntity, Integer>, ReactiveQueryByExampleExecutor<RolEntity> {

    Mono<RolEntity> findByCodigo(String codigo);
}
