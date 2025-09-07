package pe.crediya.autenticacion.api.config;

import io.swagger.v3.oas.models.*; import io.swagger.v3.oas.models.info.Info; import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.*;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI api(){ return new OpenAPI()
            .components(new Components().addSecuritySchemes("bearer-jwt",
                    new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
            .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"))
            .info(new Info().title("Auth API").version("v1")); }
}