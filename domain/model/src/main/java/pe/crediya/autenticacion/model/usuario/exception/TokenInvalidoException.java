package pe.crediya.autenticacion.model.usuario.exception;

public class TokenInvalidoException extends DomainValidationException {
    public TokenInvalidoException(String message) {
        super(message);
    }

    public TokenInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }
}
