package pe.crediya.autenticacion.model.usuario.exception;

public class UsuarioInactivoException extends DomainValidationException {

    public UsuarioInactivoException(String message) {
        super(message);
    }

    public UsuarioInactivoException(String message, Throwable cause) {
        super(message, cause);
    }
}