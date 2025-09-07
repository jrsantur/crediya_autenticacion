package pe.crediya.autenticacion.model.usuario.exception;

public class UsuarioNoEncontradoException extends DomainValidationException{
    public UsuarioNoEncontradoException(String message) {
        super(message);
    }

    public UsuarioNoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
}
