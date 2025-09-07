package pe.crediya.autenticacion.model.usuario.exception;

public class AuthException extends DomainValidationException  {
    public AuthException(String message) {
        super(message);
    }
}
