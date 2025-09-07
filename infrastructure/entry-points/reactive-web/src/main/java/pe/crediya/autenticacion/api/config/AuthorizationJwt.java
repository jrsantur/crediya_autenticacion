package pe.crediya.autenticacion.api.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimValidator;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import lombok.extern.log4j.Log4j2;

import javax.crypto.SecretKey;

@Log4j2
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class AuthorizationJwt implements WebFluxConfigurer {

    private final String issuerUri;
    private final String clientId;
    private final String jsonExpRoles;
    private final String secret;
    private final ObjectMapper mapper;
    private static final String ROLE = "ROLE_";
    private static final String AZP = "azp";

    public AuthorizationJwt(@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") String issuerUri,
                         @Value("${spring.security.oauth2.resourceserver.jwt.client-id}") String clientId,
                         @Value("${jwt.json-exp-roles}") String jsonExpRoles,
                         @Value("${jwt.secret}") String secret,
                         ObjectMapper mapper) {
        this.issuerUri = issuerUri;
        this.clientId = clientId;
        this.jsonExpRoles = jsonExpRoles;
        this.secret = secret;
        this.mapper = mapper;
    }

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(ex -> ex
                        .pathMatchers(
                                "/api/v1/usuarios/valida_token",
                                "/api/v1/usuarios",
                                "/api/v1/login",
                                "/v3/api-docs/**",
                                "/swagger-ui/**"
                        ).permitAll()
                        //.pathMatchers(HttpMethod.POST, "/api/v1/usuarios").hasAnyAuthority("ROLE_ADMIN","ROLE_ASESOR","CREAR_USUARIO")
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(j -> j
                                .jwtDecoder(jwtDecoder())
                                .jwtAuthenticationConverter(grantedAuthoritiesExtractor())
                        )
                )
                .build();
    }


    public ReactiveJwtDecoder jwtDecoder() {
        /*
        var defaultValidator = JwtValidators.createDefaultWithIssuer(issuerUri);
        var audienceValidator = new JwtClaimValidator<String>(AZP,
                azp -> azp != null && !azp.isEmpty() && azp.equals(clientId));
        var tokenValidator = new DelegatingOAuth2TokenValidator<>(defaultValidator, audienceValidator);
        var jwtDecoder = NimbusReactiveJwtDecoder
                .withIssuerLocation(issuerUri)
                .build();

        jwtDecoder.setJwtValidator(tokenValidator);
        return jwtDecoder;
        */
        SecretKey key = io.jsonwebtoken.security.Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8));

        NimbusReactiveJwtDecoder dec = NimbusReactiveJwtDecoder
                .withSecretKey(key)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();

        OAuth2TokenValidator<Jwt> iss = JwtValidators.createDefaultWithIssuer(issuerUri);
        OAuth2TokenValidator<Jwt> azp = new JwtClaimValidator<>("azp", v -> clientId.equals(v));
        dec.setJwtValidator(new DelegatingOAuth2TokenValidator<>(iss, azp));
        return dec;
    }

    public Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        var jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwt ->
                getRoles(jwt.getClaims(), jsonExpRoles)
                .stream()
                //.map(ROLE::concat)
                .map(r -> r.startsWith(ROLE) ? r : r)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()));
        return new ReactiveJwtAuthenticationConverterAdapter(jwtConverter);
    }

    private List<String> getRoles(Map<String, Object> claims, String jsonExpClaim){
        /*
        List<String> roles = List.of();
        try {
            var json = mapper.writeValueAsString(claims);
            var chunk = mapper.readTree(json).at(jsonExpClaim);
            return mapper.readerFor(new TypeReference<List<String>>() {})
                    .readValue(chunk);
        } catch (IOException e) {
            log.error(e.getMessage());
            return roles;
        }
        */
        try {
            var node = mapper.readTree(mapper.writeValueAsString(claims)).at(jsonExpClaim);
            if (node.isMissingNode() || node.isNull()) return List.of();
            if (node.isArray()) {
                return StreamSupport.stream(node.spliterator(), false)
                        .filter(JsonNode::isValueNode).map(JsonNode::asText).toList();
            }
            if (node.isTextual()) {
                // soporte "ADMIN,USER" o "CLIENTE"
                return Arrays.stream(node.asText().split("[,;\\s]+"))
                        .filter(s -> !s.isBlank()).toList();
            }
            return List.of(node.asText());
        } catch (Exception e) {
            log.error("No se pudieron extraer roles: {}", e.getMessage());
            return List.of();
        }
    }


}
