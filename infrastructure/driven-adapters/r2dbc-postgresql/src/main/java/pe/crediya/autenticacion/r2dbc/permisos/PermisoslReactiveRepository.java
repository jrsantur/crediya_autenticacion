package pe.crediya.autenticacion.r2dbc.permisos;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.crediya.autenticacion.r2dbc.permisos.entity.PermisosEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// TODO: This file is just an example, you should delete or modify it
public interface PermisoslReactiveRepository extends ReactiveCrudRepository<PermisosEntity, Integer>, ReactiveQueryByExampleExecutor<PermisosEntity> {
    Mono<PermisosEntity> findByCodigo(String codigo);

    @Query("""
        SELECT p.id, p.codigo, p.nombre, p.descripcion, p.recurso, p.accion, p.activo,
               p.fecha_creacion, p.fecha_actualizacion
        FROM permiso p
        JOIN rol_permiso rp ON rp.permiso_id = p.id
        WHERE rp.rol_id = :rolId
      """)
    Flux<PermisosEntity> findAllByRolId(Integer rolId);

}
