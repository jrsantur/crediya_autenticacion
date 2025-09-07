package pe.crediya.autenticacion.r2dbc.rol;

import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import pe.crediya.autenticacion.model.usuario.Permiso;
import pe.crediya.autenticacion.model.usuario.Rol;
import pe.crediya.autenticacion.model.usuario.gateways.RolRepository;
import pe.crediya.autenticacion.r2dbc.helper.ReactiveAdapterOperations;
import pe.crediya.autenticacion.r2dbc.permisos.PermisoslReactiveRepository;
import pe.crediya.autenticacion.r2dbc.permisos.entity.PermisosEntity;
import pe.crediya.autenticacion.r2dbc.rol.entity.RolEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository

public class RolReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Rol/* change for domain model */,
        RolEntity/* change for adapter model */,
        Integer,
        RolReactiveRepository
>  implements RolRepository {

    private final PermisoslReactiveRepository permisoslReactiveRepository;

    public RolReactiveRepositoryAdapter(RolReactiveRepository repository, ObjectMapper mapper, PermisoslReactiveRepository permisoslReactiveRepository) {
        super(repository, mapper, d -> mapper.map(d, Rol.class/* change for domain model */));
        this.permisoslReactiveRepository = permisoslReactiveRepository;
    }

    @Override
    public Mono<Rol> buscarPorId(int rolId) {
        return this.repository.findById(rolId)
                .zipWith(permisoslReactiveRepository.findAllByRolId(rolId)
                .map( p-> {
                    return Permiso.builder()
                            .id(p.getId())
                            .codigo(p.getCodigo())
                            .nombre(p.getNombre())
                            .descripcion(p.getDescripcion())
                            .recurso(p.getRecurso())
                            .accion(p.getAccion())
                            .fechaCreacion(p.getFechaCreacion())
                            .fechaActualizacion(p.getFechaActualizacion())
                            .build();
                })
                .collectList() , (re, perms) -> mapRol(re, new java.util.HashSet<>(perms)));
    }

    @Override
    public Mono<Rol> buscarPorCodigo(String codigo) {
        return this.repository.findByCodigo(codigo).map(this::toEntity);
    }

    @Override
    public Flux<Rol> obtenerRolesActivos() {
        return null;
    }

    @Override
    public Mono<Boolean> existePorId(String id) {
        return null;
    }

    @Override
    public Mono<Boolean> existePorCodigo(String codigo) {
        return null;
    }

    private Rol mapRol(RolEntity re, java.util.Set<Permiso> ps){ return Rol.builder()
            .id(re.getId()).codigo(re.getCodigo()).nombre(re.getNombre())
            .descripcion(re.getDescripcion()).activo(Boolean.TRUE.equals(re.isActivo() ) )
            .permisos(ps).build();
    }
}
