package pe.crediya.autenticacion.model.usuario.exception;

public class UsuarioYaExisteException extends DomainValidationException {
    public UsuarioYaExisteException(String message) {
        super(message);
    }
}