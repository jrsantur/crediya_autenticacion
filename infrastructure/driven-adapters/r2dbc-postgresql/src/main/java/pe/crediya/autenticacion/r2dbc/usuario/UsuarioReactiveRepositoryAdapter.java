package pe.crediya.autenticacion.r2dbc.usuario;

import pe.crediya.autenticacion.model.usuario.AccionSistema;
import pe.crediya.autenticacion.model.usuario.Usuario;
import pe.crediya.autenticacion.model.usuario.gateways.UsuarioRepository;
import pe.crediya.autenticacion.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import pe.crediya.autenticacion.r2dbc.usuario.entity.UsuarioEntity;
import reactor.core.publisher.Mono;

@Repository
public class UsuarioReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Usuario/* change for domain model */,
        UsuarioEntity/* change for adapter model */,
        Long,
        UsuarioReactiveRepository
> implements UsuarioRepository {
    public UsuarioReactiveRepositoryAdapter(UsuarioReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Usuario.class/* change for domain model */));
    }

    @Override
    public Mono<Usuario> guardarUsuario(Usuario usuario) {
        return Mono.fromCallable( () ->  this.toData(usuario) )
                .flatMap(repository::save)
                .map( u -> this.toEntity(u));
    }

    @Override
    public Mono<Usuario> buscarPorEmail(String email) {
        return repository.findByEmail(email).map(this::toEntity);
    }

    @Override
    public Mono<Boolean> existePorEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Mono<Usuario> buscarPorDocumentoIdentidad(String documento) {
        return repository.findByDocumentoIdentidad(documento).map(this::toEntity);
    }

}
