package pe.crediya.autenticacion.model.usuario;



import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;


@Builder
@Data
public class TokenValidation {

    private boolean valido;
    private int codigoHttp;
    private String mensaje;
    private Map<String, Object> claims;
    private LocalDateTime fechaExpiracion;

    public TokenValidation(boolean valido, int codigoHttp, String mensaje,
                           Map<String, Object> claims, LocalDateTime fechaExpiracion) {
        this.valido = valido;
        this.codigoHttp = codigoHttp;
        this.mensaje = mensaje;
        this.claims = claims != null ? Map.copyOf(claims) : Map.of();
        this.fechaExpiracion = fechaExpiracion;
    }

    // Factory methods para diferentes casos
    public static TokenValidation exitoso(String sub, String email, String rol, String documento) {
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("sub", sub);
        claimsMap.put("email", email);
        claimsMap.put("rol", rol);
        claimsMap.put("documento_identidad", documento);

        return new TokenValidation(
                true,
                200,
                "Token válido",
                claimsMap,
                LocalDateTime.now()
        );
    }

    public static TokenValidation tokenFaltante() {
        return new TokenValidation(false, 401, "Token es requerido", null, null);
    }

    public static TokenValidation tokenExpirado() {
        return new TokenValidation(false, 401, "Token expirado", null, null);
    }

    public static TokenValidation tokenInvalido(String razon) {
        return new TokenValidation(false, 401, "Token inválido: " + razon, null, null);
    }

    public static TokenValidation errorInterno() {
        return new TokenValidation(false, 500, "Error interno del servidor", null, null);
    }
}
