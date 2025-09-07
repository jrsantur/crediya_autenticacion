package pe.crediya.autenticacion.usecase.rol;

import lombok.RequiredArgsConstructor;
import pe.crediya.autenticacion.model.usuario.Rol;
import pe.crediya.autenticacion.model.usuario.gateways.RolRepository;
import pe.crediya.autenticacion.model.usuario.gateways.UsuarioRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RolUseCase {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;


    public Mono<Rol> buscarPorId(int id){
         return rolRepository.buscarPorId(id);
    }

    public Mono<Rol> buscarPorCodigo(String codigo){
        return rolRepository.buscarPorCodigo(codigo);
    }

    public Flux<Rol> obtenerRolesActivos(){
        return rolRepository.obtenerRolesActivos();
    }

    public Mono<Boolean> existePorId(String id){
        return rolRepository.existePorId(id);
    }

    public Mono<Boolean> existePorCodigo(String codigo){
        return rolRepository.existePorCodigo(codigo);
    }
}
