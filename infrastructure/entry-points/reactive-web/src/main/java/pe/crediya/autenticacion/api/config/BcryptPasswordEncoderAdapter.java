package pe.crediya.autenticacion.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pe.crediya.autenticacion.model.usuario.gateways.CriptoRepository;

@Component
@RequiredArgsConstructor
public class BcryptPasswordEncoderAdapter implements CriptoRepository {

    private final BCryptPasswordEncoder enc = new BCryptPasswordEncoder();

    @Override
    public String encode(String rawPassword) {
        return enc.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return  enc.matches(rawPassword, encodedPassword);
    }
}
