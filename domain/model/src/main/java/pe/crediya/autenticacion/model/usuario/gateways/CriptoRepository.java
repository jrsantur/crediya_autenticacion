package pe.crediya.autenticacion.model.usuario.gateways;

public interface CriptoRepository {

    String encode(String rawPassword);

    boolean matches(String rawPassword, String encodedPassword);
}
