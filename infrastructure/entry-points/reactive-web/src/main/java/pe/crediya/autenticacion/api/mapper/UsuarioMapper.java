package pe.crediya.autenticacion.api.mapper;

import org.mapstruct.Mapper;
import pe.crediya.autenticacion.api.dto.UsuarioRequestDto;
import pe.crediya.autenticacion.model.usuario.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toDomain(UsuarioRequestDto usuarioRequestDto);

}
