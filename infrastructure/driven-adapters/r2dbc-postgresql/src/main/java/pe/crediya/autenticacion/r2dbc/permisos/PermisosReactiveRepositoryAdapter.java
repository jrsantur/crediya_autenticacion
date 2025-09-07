package pe.crediya.autenticacion.r2dbc.permisos;

import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import pe.crediya.autenticacion.model.usuario.Permiso;
import pe.crediya.autenticacion.r2dbc.helper.ReactiveAdapterOperations;
import pe.crediya.autenticacion.r2dbc.permisos.entity.PermisosEntity;

@Repository
public class PermisosReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Permiso/* change for domain model */,
        PermisosEntity/* change for adapter model */,
    Integer,
        PermisoslReactiveRepository
> {
    public PermisosReactiveRepositoryAdapter(PermisoslReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Permiso.class/* change for domain model */));
    }

}
