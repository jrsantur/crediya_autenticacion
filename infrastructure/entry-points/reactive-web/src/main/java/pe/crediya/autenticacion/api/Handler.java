package pe.crediya.autenticacion.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.crediya.autenticacion.api.dto.LoginRequestDto;
import pe.crediya.autenticacion.api.dto.UsuarioRequestDto;
import pe.crediya.autenticacion.api.dto.UsuarioResponseDto;
import pe.crediya.autenticacion.api.mapper.UsuarioMapper;
import pe.crediya.autenticacion.model.usuario.CredencialesLogin;
import pe.crediya.autenticacion.model.usuario.gateways.TokenRepository;
import pe.crediya.autenticacion.usecase.auth.AuthUseCase;
import pe.crediya.autenticacion.usecase.usuario.UsuarioUseCase;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class Handler {

    private final AuthUseCase authUseCase;
    private final TokenRepository tokenRepository;
    private final UsuarioUseCase usuarioUseCase;
    private final UsuarioMapper usuarioMapper;

    public Mono<ServerResponse> login(ServerRequest req){
        return req.bodyToMono(LoginRequestDto.class)
                .flatMap( r -> authUseCase.autenticar(new CredencialesLogin(r.getEmail(),r.getPassword())))
                .flatMap(tr-> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(tr))
                .doOnError(error -> log.error("Error: {}", error.getMessage()));
    }

    public Mono<ServerResponse> crearUsuario(ServerRequest req){
        return req.bodyToMono(UsuarioRequestDto.class)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Request body es requerido")))
                .flatMap(usuarioRequest -> {
                    var usuario =  usuarioMapper.toDomain(usuarioRequest);
                    return usuarioUseCase.registrar(usuario);
                })
                .flatMap(usuario -> {
                    var response = UsuarioResponseDto.builder()
                            .id(usuario.getId())
                            .nombre(usuario.getNombres())
                            .apellidos(usuario.getApellidos())
                            .email(usuario.getEmail())
                            .fechaCreacion(usuario.getFechaCreacion()).build();
                    return ServerResponse.ok().bodyValue(response);
                })
                .doOnError(error -> log.error("Error: {}", error.getMessage()));
    }

    public Mono<ServerResponse> consultarUsuario(ServerRequest serverRequest){
        String documento = serverRequest.queryParam("documento").orElse("");
        return null;
    }

    public Mono<ServerResponse> validaToken(ServerRequest serverRequest){
        return Mono.justOrEmpty(serverRequest.headers().firstHeader(HttpHeaders.AUTHORIZATION))
                .filter(h -> h.startsWith("Bearer "))
                .map(h-> h.substring(7))
                .flatMap(tokenRepository::validarToken)
                .flatMap(response -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(response))
                .switchIfEmpty(ServerResponse.status(HttpStatus.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE, "Bearer error=\"invalid_token\"")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(Map.of("valid", false, "error", "Falta Authorization Bearer")))
                .onErrorResume( e -> ServerResponse.status(HttpStatus.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE, "Bearer error=\"invalid_token\"")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(Map.of("valid", false, "error", "Token inválido" +e)));

    }

}
