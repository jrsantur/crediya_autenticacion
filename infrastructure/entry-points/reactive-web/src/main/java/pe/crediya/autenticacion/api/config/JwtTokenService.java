package pe.crediya.autenticacion.api.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pe.crediya.autenticacion.model.usuario.TokenAutenticacion;
import pe.crediya.autenticacion.model.usuario.TokenValidation;
import pe.crediya.autenticacion.model.usuario.Usuario;
import pe.crediya.autenticacion.model.usuario.gateways.TokenRepository;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class JwtTokenService  implements TokenRepository {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.ttlSeconds:3600}")
    private long jwtExpirationSeconds ;
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    String issuerUri;
    @Value("${spring.security.oauth2.resourceserver.jwt.client-id}")
    String clientId;

    @Override
    public Mono<TokenAutenticacion> generarToken(Usuario usuario) {

        return Mono.fromSupplier(() -> {
            Instant now = Instant.now();
            String token = Jwts.builder()
                    .issuer(issuerUri)
                    .claim("azp",clientId)
                    .subject(String.valueOf(usuario.getId()))
                    .claim("email", usuario.getEmail())
                    .claim("rol", usuario.getRol() )
                    .claim("documento_identidad", usuario.getDocumentoIdentidad())
                    .issuedAt(Date.from(now))
                    .expiration(Date.from(now.plusSeconds(jwtExpirationSeconds)))
                    .signWith(jwtKey())
                    .compact();

            return TokenAutenticacion.builder().accessToken(token)
                    .fechaExpiracion(LocalDateTime.now().plus(Duration.ofSeconds(jwtExpirationSeconds)))
                    .fechaCreacion(LocalDateTime.now())
                    .build();

        });
    }

    @Override
    public Mono<TokenValidation> validarToken(String token) {
        return Mono.fromCallable(() -> {
            if (token == null || token.trim().isEmpty()) {
                return TokenValidation.tokenFaltante();
            }

            // Remover prefijo "Bearer " si existe
            String tokenLimpio = token.startsWith("Bearer ")
                    ? token.substring(7)
                    : token;

            try {
                Claims claims = Jwts.parser()
                        .verifyWith(jwtKey())
                        .build()
                        .parseSignedClaims(tokenLimpio)
                        .getPayload();

                // Verificar expiración
                if (claims.getExpiration().before(new Date())) {
                    return TokenValidation.tokenExpirado();
                }

                return TokenValidation.exitoso((String) claims.get("sub"),
                        (String) claims.get("email"),
                        (String) claims.get("rol"),
                        (String) claims.get("documento_identidad"));

            } catch (ExpiredJwtException e) {
                return TokenValidation.tokenExpirado();
            } catch (UnsupportedJwtException e) {
                return TokenValidation.tokenInvalido("formato no soportado");
            } catch (MalformedJwtException e) {
                return TokenValidation.tokenInvalido("formato malformado");
            } catch (SecurityException | IllegalArgumentException e) {
                return TokenValidation.tokenInvalido("firma inválida");
            } catch (Exception e) {
                return TokenValidation.errorInterno();
            }
        });
    }


    private SecretKey jwtKey() {
        byte[] bytes = secret.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        if (bytes.length < 32) {
            throw new IllegalStateException("jwt.secret debe tener al menos 32 bytes");
        }
        return io.jsonwebtoken.security.Keys.hmacShaKeyFor(bytes);
    }
}
